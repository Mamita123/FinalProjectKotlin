package com.example.finallab1.db


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context




@Database(entities = [ParliamentMember::class], version = 1)
abstract class PMDatabase : RoomDatabase() {

    abstract fun ParliamentMemberDao(): ParliamentMemberDao

    companion object {
        @Volatile
        private var INSTANCE: PMDatabase? = null

        fun getInstance(context: Context): PMDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PMDatabase::class.java,
                    "pm_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


