package com.example.taskmanager.calendar

import com.example.taskmanager.Task
import java.time.LocalDateTime

interface CalendarContract {
    interface View {
        fun updateTaskList(taskList: List<Task>)
        fun showTaskFragment(task: Task)
    }

    interface Presenter {
        fun onDateChanged(selectedDay: LocalDateTime)
        fun onTaskClicked(id: Long)
    }

}