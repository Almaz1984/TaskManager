package com.example.taskmanager.fragments.calendar

import com.example.taskmanager.TimeService
import com.example.taskmanager.data.App
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.models.NWTask
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.data.repository.TaskRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

const val ONE_DAY_IN_MILLIS = 86399999L

class CalendarPresenter(private val view: CalendarContract.View) :
    CalendarContract.Presenter {
    private var selectedDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
    private var db: TaskDatabase = App.instance.getDatabase()!!
    private val taskDao = db.taskDao()
    private val repository: TaskRepository = TaskRepository(taskDao)
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getSelectedDate(): LocalDateTime {
        return selectedDay
    }

    override fun onTaskClicked(id: Long) {

        val task = getSampleListFromJson().find { it.id == (id) }
        if (task != null) {
            view.showDetailTaskFragment(task)
        }
    }

    override fun onAddTaskClicked() {
        view.showNewTaskFragment()
    }

    override fun onDateChanged(selectedDay: LocalDateTime) {
        val selectedDayTimeStamp = TimeService.getTimestampFromDateTime(selectedDay)
        scope.launch(Dispatchers.Main) {
            val selectedDayTaskList = withContext(Dispatchers.IO) {
                getSelectedDayTaskList(selectedDayTimeStamp)
            }
            view.updateTaskList(selectedDayTaskList)
        }
        this.selectedDay = selectedDay
    }

    override fun onDateChanged() {
        val selectedDayTimeStamp = TimeService.getTimestampFromDateTime(selectedDay)
        scope.launch(Dispatchers.Main) {
            val selectedDayTaskList = withContext(Dispatchers.IO) {
                getSelectedDayTaskList(selectedDayTimeStamp)
            }
            view.updateTaskList(selectedDayTaskList)
        }
    }

    private suspend fun getSelectedDayTaskList(selectedDay: Long): List<Task> {
        val taskList: List<Task>
        val dateFrom = selectedDay
        val dateTo = selectedDay + ONE_DAY_IN_MILLIS
        taskList = repository.getAllDayTasks(dateFrom, dateTo).map { it.mapToTask() }
        return taskList
    }

    private fun getSampleListFromJson(): List<Task> {
        val sampleJsonTaskList = listOf(
            "{'id':'1'," +
                "'date_start':'1637611980641'," +
                "'date_finish':'1637615580642'," +
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
