package com.example.taskmanager.fragments.newtask

internal class NewTaskPresenter(private val view: NewTaskContract.View) : NewTaskContract.Presenter {
    override fun onDateClicked() {
        view.showDatePicker()
    }
}
