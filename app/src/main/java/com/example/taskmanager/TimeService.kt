package com.example.taskmanager

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

const val MS_MULIPLIER=1000L

class TimeService() {

    fun getDateFromTimestamp(timestamp: Long): String {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
        return LocalDate.parse(Timestamp(timestamp*MS_MULIPLIER).toString(), dateFormat).toString()
    }
    fun getTimeFromTimestamp(timestamp: Long): String {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
        return LocalTime.parse(Timestamp(timestamp*MS_MULIPLIER).toString(), dateFormat)
            .truncatedTo(ChronoUnit.MINUTES).toString()
    }
}