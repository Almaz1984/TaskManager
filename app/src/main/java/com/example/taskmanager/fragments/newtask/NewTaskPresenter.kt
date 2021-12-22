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
const val TIME_FROM = "Time from"
const val TIME_TO = "Time to"
const val DESCRIPTION = "Description"

internal class NewTaskPresenter(private val view: NewTaskContract.View) :
    NewTaskContract.Presenter {
    private var taskName: String = ""
    private var date: LocalDateTime? = null
    private var timeFrom: LocalTime? = null
    private var timeTo: LocalTime? = null
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
            TIME_FROM -> view.showTimeFromPicker()
            TIME_TO -> view.showTimeToPicker()
            DESCRIPTION -> {
                description = view.getDescription()
            }
        }
    }

    override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        val date: LocalDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
        val formattedDate = TimeService.getFormattedDate(date)
        view.setDate(formattedDate)
        this.date = date
        checkFields()
    }

    override fun onTimeFromSet(hourOfDay: Int, minute: Int) {
        val time: LocalTime = LocalTime.of(hourOfDay, minute, 0, 0)
        val formattedTime = TimeService.getFormattedTime(time)
        view.setTimeFrom(formattedTime)
        timeFrom = time
        checkFields()
    }

    override fun onTimeToSet(hourOfDay: Int, minute: Int) {
        val time: LocalTime = LocalTime.of(hourOfDay, minute, 0, 0)
        val formattedTime = TimeService.getFormattedTime(time)
        timeTo = time
        view.setTimeTo(formattedTime)
        checkFields()
    }

    override fun getCurrentDate(): LocalDateTime = LocalDateTime.now()

    private fun checkFields() {
        val enabled = !(
            taskName.isEmpty() ||
                date == null ||
                timeFrom == null ||
                timeTo == null
            )
        view.setSaveButtonStatus(enabled)
    }

    override fun onSaveButtonClicked() {

        val startDate = TimeService.getTimestampFromDateTime(date!!.toLocalDate(), timeFrom!!)
        val finishDate = TimeService.getTimestampFromDateTime(date!!.toLocalDate(), timeTo!!)
        val newTask = TaskData(0, startDate, finishDate, taskName, description)
        scope.launch(Dispatchers.IO) {
            repository.insertTask(newTask)
        }
    }
}
