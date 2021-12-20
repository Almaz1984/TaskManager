package com.example.taskmanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmanager.data.models.TaskData

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table WHERE dataStart >= :dateFrom and dataStart<:dateTo")
    suspend fun getAllDayTasks(dateFrom: Long, dateTo: Long): List<TaskData>

    @Query("SELECT * FROM task_table WHERE id = :id")
    suspend fun getTaskById(id: Long): List<TaskData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(taskData: TaskData)
}
