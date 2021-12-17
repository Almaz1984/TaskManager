package com.example.taskmanager.fragments.calendar

import com.example.taskmanager.data.models.Task
import java.time.LocalDateTime

interface CalendarContract {
    interface View {
        fun updateTaskList(taskList: List<Task>)
        fun showDetailTaskFragment(task: Task)
    }

    interface Presenter {
        fun onDateChanged(selectedDay: LocalDateTime)
        fun onDateChanged()
        fun onTaskClicked(id: Long)
        fun getSelectedDate(): LocalDateTime
    }
}