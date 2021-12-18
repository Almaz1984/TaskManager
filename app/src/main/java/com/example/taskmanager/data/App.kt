package com.example.taskmanager.data

import android.app.Application
import androidx.room.Room

const val DATABASE_NAME = "database"

class App : Application() {
    private var database: TaskDatabase? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, TaskDatabase::class.java, DATABASE_NAME)
            .build()
    }

    fun getDatabase(): TaskDatabase? {
        return database
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
