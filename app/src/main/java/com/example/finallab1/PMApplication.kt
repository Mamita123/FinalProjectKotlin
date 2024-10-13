package com.example.finallab1

import android.app.Application
import android.content.Context
import com.example.finallab1.db.DBSynchronizer

// 06.10.2024 by Mamita Gurung 2115081
//This is a helper class which can easily access application context and launches DatabaseSynchronizer on application start

class PMApplication : Application(){
    companion object{
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        DBSynchronizer.start()
    }
}