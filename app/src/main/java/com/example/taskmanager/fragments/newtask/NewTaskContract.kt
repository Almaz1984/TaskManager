package com.example.taskmanager.fragments.newtask

interface NewTaskContract {
    interface View {
        fun showDatePicker()
    }

    interface Presenter {
        fun onDateClicked()
    }
}
