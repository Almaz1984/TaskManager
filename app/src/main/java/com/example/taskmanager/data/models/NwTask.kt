package com.example.taskmanager.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NwTask(
    @SerializedName("id") val id: Long?,
    @SerializedName("date_start") val dataStart: Long?,
    @SerializedName("date_finish") val dataFinish: Long?,
    @SerializedName("name") val taskName: String?,
    @SerializedName("description") val taskDescription: String?
) : Serializable {
    fun mapToTask() = Task(
        id ?: 0,
        dataStart ?: 0,
        dataFinish ?: 0,
        taskName ?: "",
        taskDescription ?: ""
    )
}
