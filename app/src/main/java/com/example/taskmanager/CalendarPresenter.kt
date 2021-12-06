package com.example.taskmanager
const val MESSAGE="Hello"
class CalendarPresenter(private val view: CalendarContract.View) : CalendarContract.Presenter {
    override fun onButtonClicked() {
        super.onButtonClicked()
        view.showText(MESSAGE)
    }
}