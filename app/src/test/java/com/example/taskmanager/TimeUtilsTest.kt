package com.example.taskmanager

import com.example.taskmanager.utils.TimeUtils.getDateTimeFromTimestamp
import com.example.taskmanager.utils.TimeUtils.getFormattedDate
import com.example.taskmanager.utils.TimeUtils.getFormattedTime
import com.example.taskmanager.utils.TimeUtils.getTimeFromTimestamp
import com.example.taskmanager.utils.TimeUtils.getTimestampFromDateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TimeUtilsTest {
    @Test
    fun testUtils() {
        val dateTime = LocalDateTime.of(2021, 12, 12, 12, 59)
        val date = LocalDate.of(2021, 12, 12)
        val time = LocalTime.of(12, 59)

        assertEquals(
            "TEST getTimeFromTimestamp",
            "22:00", getTimeFromTimestamp(147600000)
        )
        assertEquals(
            "TEST getDateTimeFromTimestamp",
            "1970-01-02 22:00",
            getDateTimeFromTimestamp(147600000)
        )
        assertEquals(
            "TEST getTimestampFromDateTime",
            1639313940000,
            getTimestampFromDateTime(dateTime)
        )
        assertEquals(
            "TEST getTimeFromTimestamp",
            1639313940000,
            getTimestampFromDateTime(date, time)
        )
        assertEquals(
            "TEST getTimeFromTimestamp",
            "12 дек., 2021", getFormattedDate(dateTime)
        )
        assertEquals(
            "TEST getTimeFromTimestamp",
            "12:59 PM", getFormattedTime(time)
        )
    }
}
