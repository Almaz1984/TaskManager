package com.example.taskmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskData(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val dataStart: Long,
    val dataFinish: Long,
    val taskName: String,
    val taskDescription: String
) {
    fun mapToTask() = Task(
        id,
        dataStart,
        dataFinish,
        taskName,
        taskDescription
    )
}
