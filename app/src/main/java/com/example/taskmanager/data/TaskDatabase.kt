package com.example.taskmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskmanager.data.models.TaskData

@Database(entities = [TaskData::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
