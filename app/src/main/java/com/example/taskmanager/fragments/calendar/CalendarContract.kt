package com.example.taskmanager.fragments.calendar

import com.example.taskmanager.data.models.Task
import java.time.LocalDateTime

interface CalendarContract {
    interface View {
        fun setTaskList(taskList: List<Task>)
        fun showDetailTaskFragment(task: Task)
        fun showNewTaskFragment()
    }

    interface Presenter {
        fun onDateChanged(selectedDay: LocalDateTime)
        fun init()
        fun onTaskClicked(id: Long?)
        fun onAddTaskClicked()
        fun getSelectedDate(): LocalDateTime
    }
}
