package com.example.taskmanager

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

const val MYTAG="My TAG"

class DataLayer() {
     fun getSelectedDayTaskList(selectedDay:Long): List<Task> {
         val taskList=getSampleListFromJson()
         Log.d(MYTAG, "Time1=${TimeService().getDateFromTimestamp(taskList[0].dataStart)}")
         Log.d(MYTAG, "Time2=${Timestamp(selectedDay).toString()}")
         return taskList.filter{ TimeService().getDateFromTimestamp(it.dataStart)
             .equals(TimeService().getDateFromTimestamp(Timestamp(selectedDay)))}
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
            .registerTypeAdapter(Timestamp::class.java, deser)
            .create()
        for (task in sampleJsonTaskList) {
            taskList.add(gson.fromJson(task, Task::class.java))
        }
        return taskList
    }
}