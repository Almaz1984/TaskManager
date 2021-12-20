package com.example.taskmanager.fragments.newtask

import com.example.taskmanager.TimeService
import com.example.taskmanager.data.App
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.models.TaskData
import com.example.taskmanager.data.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

const val TASK_NAME = "Task name"
const val DATE = "Date"
const val START_TIME = "Start time"
const val END_TIME = "End time"
const val DESCRIPTION = "Description"

internal class NewTaskPresenter(private val view: NewTaskContract.View) :
    NewTaskContract.Presenter {
    private var taskName: String = ""
    private var date: LocalDateTime? = null
    private var startTime: LocalTime? = null
    private var endTime: LocalTime? = null
    private var description: String = ""
    private var db: TaskDatabase = App.instance.getDatabase()!!
    private val taskDao = db.taskDao()
    private val repository: TaskRepository = TaskRepository(taskDao)
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onDateClicked() {
        view.showDatePicker()
    }

    override fun onFieldClicked(field: String) {
        when (field) {
            TASK_NAME -> {
                taskName = view.getTaskName()
                checkFields()
            }
            DATE -> view.showDatePicker()
            START_TIME -> view.showStartTimePicker()
            END_TIME -> view.showEndTimePicker()
            DESCRIPTION -> {
                description = view.getDescription()
            }
        }
    }

    override fun onSaveButtonClicked() {

        val startDate = TimeService.getTimestampFromTime(date!!.toLocalDate(), startTime!!)
        val finishDate = TimeService.getTimestampFromTime(date!!.toLocalDate(), endTime!!)
        val newTask = TaskData(0, startDate, finishDate, taskName, description)
        scope.launch(Dispatchers.IO) {
            repository.insertTask(newTask)
        }
    }

    override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        val date: LocalDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
        val formattedDate = TimeService.getFormattedDate(date)
        view.setDate(formattedDate)
        this.date = date
        checkFields()
    }

    override fun onStartTimeSet(hourOfDay: Int, minute: Int) {
        val time: LocalTime = LocalTime.of(hourOfDay, minute, 0, 0)
        val formattedTime = TimeService.getFormattedTime(time)
        view.setStartTime(formattedTime)
        startTime = time
        checkFields()
    }

    override fun onEndTimeSet(hourOfDay: Int, minute: Int) {
        val time: LocalTime = LocalTime.of(hourOfDay, minute, 0, 0)
        val formattedTime = TimeService.getFormattedTime(time)
        endTime = time
        view.setEndTime(formattedTime)
        checkFields()
    }

    override fun getCurrentDate(): LocalDateTime = LocalDateTime.now()

    private fun checkFields() {
        val enabled = !(
            taskName.isEmpty() ||
                date == null ||
                startTime == null ||
                endTime == null
            )
        view.setSaveButtonStatus(enabled)
    }
}
