package com.example.finallab1.db

import android.content.Context
import android.util.Log

import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.finallab1.network.NetworkAPI
import com.example.finallab1.PMApplication
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.DelicateCoroutinesApi


//4.10.2024 by Mamita Gurung
object DBSynchronizer {
    fun start() {
        val uploadRequest = PeriodicWorkRequestBuilder<DBWorker>(15, TimeUnit.MINUTES).build()
        val workManager = WorkManager.getInstance(PMApplication.appContext)
        workManager.enqueue(uploadRequest)
    }
}

class DBWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @OptIn(DelicateCoroutinesApi::class)
    override fun doWork(): Result {
        val db = PMDatabase.getInstance(applicationContext)
        val dao = db.ParliamentMemberDao()

        try {
            GlobalScope.launch {
                var parliamentMembers: List<ParliamentMember>? = NetworkAPI.apiService.loadMainData()?.execute()?.body()
                if (parliamentMembers == null) {
                    throw Exception("Failed to fetch main data")
                }

                val extraData = NetworkAPI.apiService.loadExtraData()?.execute()?.body()
                if (extraData == null) {
                    throw Exception("Failed to fetch extra data")
                }

                // Add extra data to parliament members' list
                parliamentMembers = parliamentMembers.map { memberData1: ParliamentMember ->
                    val memberData2 = extraData.find { it.hetekaId == memberData1.hetekaId }
                    if (memberData2 != null) {
                        return@map memberData1.copy(
                            twitter = memberData2.twitter,
                            bornYear = memberData2.bornYear,
                            constituency = memberData2.constituency
                        )
                    } else {
                        return@map memberData1
                    }
                }

                dao.insertAll(parliamentMembers)
                Log.d("DB", "Data synchronized with remote server")
            }

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}