package com.example.taskmanager.data.models

import java.io.Serializable

data class Task(
    val id: Long,
    val dataStart: Long,
    val dataFinish: Long,
    val taskName: String,
    val taskDescription: String
) : Serializable
