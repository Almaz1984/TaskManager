package com.example.taskmanager

import android.app.Application
import androidx.room.Room
import com.example.taskmanager.data.dao.TaskDatabase

const val DATABASE_NAME = "database"

class App : Application() {
    private lateinit var database: TaskDatabase
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, TaskDatabase::class.java, DATABASE_NAME)
            .build()
    }

    fun getDatabase(): TaskDatabase {
        return database
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
