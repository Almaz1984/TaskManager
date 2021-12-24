package com.example.taskmanager.utils

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

const val TIMESTAMP_PARSE_PATTERN = "yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]"
const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm"
const val DATE_FORMATTER = "dd MMM, yyyy"
const val TIME_FORMATTER = "hh:mm a"

object TimeUtils {

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

    fun getTimestampFromDateTime(date: LocalDate, time: LocalTime): Long {
        return getTimestampFromDateTime(LocalDateTime.of(date, time))
    }

    fun getTimestampFromDateTime(dateTime: LocalDateTime): Long {
        return dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    fun getFormattedDate(dateTime: LocalDateTime): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMATTER)
        return dateTime.format(formatter)
    }

    fun getFormattedTime(time: LocalTime): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMATTER)
        return time.format(formatter)
    }
}
