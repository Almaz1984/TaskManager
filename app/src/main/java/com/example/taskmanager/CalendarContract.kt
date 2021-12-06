package com.example.taskmanager

interface CalendarContract {
    interface View {
        fun showText(message: String){

        }
    }

    interface Presenter {
        fun onButtonClicked(){

        }
    }
}