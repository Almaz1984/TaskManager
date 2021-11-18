package com.example.taskmanager

import java.sql.Timestamp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TimeService() {
    fun getDateFromTimestamp(timestamp: Timestamp): String {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
        return LocalDate.parse(timestamp.toString(), dateFormat).toString()
    }
}