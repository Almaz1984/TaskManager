package com.example.taskmanager.data.repository

import com.example.taskmanager.data.TaskDao
import com.example.taskmanager.data.models.TaskData

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun getAllDayTasks(dateFrom: Long, dateTo: Long) = taskDao.getAllDayTasks(dateFrom, dateTo)
    suspend fun insertTask(taskData: TaskData) {
        taskDao.insertTask(taskData)
    }
}
