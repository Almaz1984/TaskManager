package com.example.taskmanager.fragments.newtask

import java.time.LocalDateTime

interface NewTaskContract {
    interface View {
        fun showDatePicker()
        fun showTimeFromPicker()
        fun showTimeToPicker()
        fun setDate(formattedDate: String)
        fun setTimeFrom(formattedTime: String)
        fun setTimeTo(formattedTime: String)
        fun setSaveButtonStatus(enabled: Boolean)
        fun getTaskName(): String
        fun getDescription(): String
    }

    interface Presenter {
        fun onDateClicked()
        fun onFieldClicked(field: String)
        fun onSaveButtonClicked()
        fun onDateSet(year: Int, month: Int, dayOfMonth: Int)
        fun onTimeFromSet(hourOfDay: Int, minute: Int)
        fun onTimeToSet(hourOfDay: Int, minute: Int)
        fun getCurrentDate(): LocalDateTime
    }
}
