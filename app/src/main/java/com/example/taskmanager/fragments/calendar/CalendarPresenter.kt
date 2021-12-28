package com.example.taskmanager.fragments.calendar

import com.example.taskmanager.App
import com.example.taskmanager.data.dao.TaskDatabase
import com.example.taskmanager.data.models.NwTask
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.data.models.TaskData
import com.example.taskmanager.data.repository.TaskRepository
import com.example.taskmanager.utils.TimeUtils
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class CalendarPresenter(private val view: CalendarContract.View) :
    CalendarContract.Presenter {
    private var selectedDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
    private var db: TaskDatabase = App.instance.getDatabase()
    private val taskDao = db.taskDao()
    private val repository: TaskRepository = TaskRepository(taskDao)
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getSelectedDate(): LocalDateTime {
        return selectedDay
    }

    override fun onTaskClicked(id: Long?) {
        var task: List<TaskData>
        scope.launch(Dispatchers.Main) {
            task = withContext(Dispatchers.IO) {
                repository.getTaskById(id)
            }
            view.showDetailTaskFragment(task.first().mapToTask())
        }
    }

    override fun onAddTaskClicked() {
        view.showNewTaskFragment()
    }

    override fun init() {
        val selectedDayTimeStamp = TimeUtils.getTimestampFromDateTime(selectedDay)
        updateTaskList(selectedDayTimeStamp)
    }

    override fun onDateChanged(selectedDay: LocalDateTime) {
        val selectedDayTimeStamp = TimeUtils.getTimestampFromDateTime(selectedDay)
        updateTaskList(selectedDayTimeStamp)
        this.selectedDay = selectedDay
    }

    private fun updateTaskList(selectedDayTimeStamp: Long) {
        scope.launch(Dispatchers.Main) {
            val selectedDayTaskList = withContext(Dispatchers.IO) {
                getSelectedDayTaskList(selectedDayTimeStamp)
            }
            view.setTaskList(selectedDayTaskList)
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
                "'date_start':'147600000'," +
                "'date_finish':'147610000'," +
                "'name':'Дело 1'," +
                "'description':'Описание дела 1'}",
            "{'id':'2'," +
                "'date_start':'147686400'," +
                "'date_finish':'147690000'," +
                "'name':'Дело 2'," +
                "'description':'Описание дела 2'}",
            "{'id':'3'," +
                "'date_start':'147772800'," +
                "'date_finish':'147776400'," +
                "'name':'Дело 3'," +
                "'description':'Описание дела 3'}"
        )
        val taskList = mutableListOf<Task>()
        val builder = GsonBuilder()
        val gson = builder
            .create()
        for (task in sampleJsonTaskList) {
            taskList.add(gson.fromJson(task, NwTask::class.java).mapToTask())
        }
        return taskList
    }

    companion object {
        const val ONE_DAY_IN_MILLIS = 86399999L
    }
}
