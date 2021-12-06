package com.example.taskmanager.calendar

import com.example.taskmanager.NWTask
import com.example.taskmanager.Task
import com.example.taskmanager.TimeService
import com.google.gson.GsonBuilder
import java.time.LocalDateTime

class CalendarPresenter(private val view: CalendarContract.View) : CalendarContract.Presenter {
    override fun onTaskClicked(id: Long) {
        val task = getSampleListFromJson().find { it.id == (id) }
        if (task != null) {
            view.showTaskFragment(task)
        }
    }

    override fun onDateChanged(selectedDay: LocalDateTime) {
        val selectedDayTimeStamp = TimeService.getTimestampFromDateTime(selectedDay)
        view.updateTaskList(getSelectedDayTaskList(selectedDayTimeStamp))
    }

    private fun getSelectedDayTaskList(selectedDay: Long): List<Task> {
        val taskList = getSampleListFromJson()
        return taskList.filter {
            TimeService.getDateFromTimestamp(it.dataStart) == TimeService.getDateFromTimestamp(
                selectedDay
            )
        }
    }

    private fun getSampleListFromJson(): List<Task> {
        val sampleJsonTaskList = listOf(
            "{'id':'1'," +
                    "'date_start':'1637957621641'," +
                    "'date_finish':'1637957621642'," +
                    "'name':'Дело 1'," +
                    "'description':'Описание дела 1'}",
            "{'id':'2'," +
                    "'date_start':'1637611980641'," +
                    "'date_finish':'1637615580642'," +
                    "'name':'Дело 2'," +
                    "'description':'Описание дела 2'}",
            "{'id':'3'," +
                    "'date_start':'1637439180641'," +
                    "'date_finish':'1637442780642'," +
                    "'name':'Дело 3'," +
                    "'description':'Описание дела 3'}"
        )
        val taskList = mutableListOf<Task>()
        val builder = GsonBuilder()
        val gson = builder
            .create()
        for (task in sampleJsonTaskList) {
            taskList.add(gson.fromJson(task, NWTask::class.java).mapToTask())
        }
        return taskList
    }
}