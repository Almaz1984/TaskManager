package com.example.taskmanager

import com.google.gson.GsonBuilder


class DataLayer() {
     fun getSelectedDayTaskList(selectedDay:Long): List<Task> {
         val taskList=getSampleListFromJson()
         return taskList.filter{ TimeService().getDateFromTimestamp(it.dataStart)
             .equals(TimeService().getDateFromTimestamp(selectedDay/MULTIPLIER))}
    }
    private fun getSampleListFromJson():List<Task>{
        val sampleJsonTaskList=listOf(
            "{'id':'1'," +
                    "'date_start':'1637177404'," +
                    "'date_finish':'1637187404'," +
                    "'name':'Дело 1'," +
                    "'description':'Описание дела 1'}",
            "{'id':'2'," +
                    "'date_start':'1637213016'," +
                    "'date_finish':'1637214016'," +
                    "'name':'Дело 2'," +
                    "'description':'Описание дела 2'}",
            "{'id':'3'," +
                    "'date_start':'1637914016'," +
                    "'date_finish':'1649214016'," +
                    "'name':'Дело 3'," +
                    "'description':'Описание дела 3'}"
        )
        val taskList = mutableListOf<Task>()
        val builder = GsonBuilder()
        val gson = builder
            .create()
        for (task in sampleJsonTaskList) {
            taskList.add(gson.fromJson(task, Task::class.java))
        }
        return taskList
    }
}