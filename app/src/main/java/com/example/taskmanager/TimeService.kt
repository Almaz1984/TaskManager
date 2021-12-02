package com.example.taskmanager

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

const val TIMESTAMP_PARSE_PATTERN = "yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]"
const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm"

object TimeService {

    fun getDateFromTimestamp(timestamp: Long): String {
        val dateFormat = DateTimeFormatter.ofPattern(TIMESTAMP_PARSE_PATTERN)
        return LocalDate.parse(Timestamp(timestamp).toString(), dateFormat).toString()
    }

    fun getTimeFromTimestamp(timestamp: Long): String {
        val dateFormat = DateTimeFormatter.ofPattern(TIMESTAMP_PARSE_PATTERN)
        return LocalTime.parse(Timestamp(timestamp).toString(), dateFormat)
            .truncatedTo(ChronoUnit.MINUTES).toString()
    }

    fun getDateTimeFromTimestamp(timestamp: Long): String {
        val dateFormat = DateTimeFormatter.ofPattern(TIMESTAMP_PARSE_PATTERN)
        val dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
        return LocalDateTime.parse(Timestamp(timestamp).toString(), dateFormat)
            .format(dateTimeFormat).toString()
    }

    fun getTimestampFromDateTime(dateTime: LocalDateTime): Long {
        return dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
    }
}