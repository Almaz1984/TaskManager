package com.example.taskmanager.fragments.newtask

import java.time.LocalDateTime

interface NewTaskContract {
    interface View {
        fun showDatePicker()
        fun showStartTimePicker()
        fun showEndTimePicker()
        fun setDate(formattedDate: String)
        fun setStartTime(formattedTime: String)
        fun setEndTime(formattedTime: String)
        fun setSaveButtonStatus(enabled: Boolean)
        fun getTaskName(): String
        fun getDescription(): String
    }

    interface Presenter {
        fun onDateClicked()
        fun onFieldClicked(field: String)
        fun onSaveButtonClicked()
        fun onDateSet(year: Int, month: Int, dayOfMonth: Int)
        fun onStartTimeSet(hourOfDay: Int, minute: Int)
        fun onEndTimeSet(hourOfDay: Int, minute: Int)
        fun getCurrentDate(): LocalDateTime
    }
}
