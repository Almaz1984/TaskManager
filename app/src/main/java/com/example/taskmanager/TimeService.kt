package com.example.taskmanager

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

const val TIMESTAMP_PARSE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS"
const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm"

object TimeService {

    fun getDateFromTimestamp(timestamp: Long?): String {
        val dateFormat = DateTimeFormatter.ofPattern(TIMESTAMP_PARSE_PATTERN)
        return LocalDate.parse(Timestamp(timestamp ?: 0).toString(), dateFormat).toString()
    }

    fun getTimeFromTimestamp(timestamp: Long?): String {
        val dateFormat = DateTimeFormatter.ofPattern(TIMESTAMP_PARSE_PATTERN)
        return LocalTime.parse(Timestamp(timestamp ?: 0).toString(), dateFormat)
            .truncatedTo(ChronoUnit.MINUTES).toString()
    }

    fun getDateTimeFromTimestamp(timestamp: Long?): String {
        val dateFormat = DateTimeFormatter.ofPattern(TIMESTAMP_PARSE_PATTERN)
        val dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
        return LocalDateTime.parse(Timestamp(timestamp ?: 0).toString(), dateFormat)
            .format(dateTimeFormat).toString()
    }
}