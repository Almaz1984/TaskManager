package com.example.taskmanager.fragments.newtask

import com.example.taskmanager.App
import com.example.taskmanager.data.dao.TaskDatabase
import com.example.taskmanager.data.models.TaskData
import com.example.taskmanager.data.repository.TaskRepository
import com.example.taskmanager.utils.TimeUtils
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
const val WRONG_INPUT = "Wrong input parameters. Please check fields!"

internal class NewTaskPresenter(private val view: NewTaskContract.View) :
    NewTaskContract.Presenter {
    private var taskName: String = ""
    private var date: LocalDateTime? = null
    private var timeFrom: LocalTime? = null
    private var timeTo: LocalTime? = null
    private var description: String = ""
    private var db: TaskDatabase = App.instance.getDatabase()
    private val taskDao = db.taskDao()
    private val repository: TaskRepository = TaskRepository(taskDao)
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onDateClicked() {
        view.showDatePicker()
    }

    override fun onEditTextChanged(editTextName: String, changedText: String) {
        when (editTextName) {
            TASK_NAME -> {
                taskName = changedText
                checkFields()
            }
            DESCRIPTION -> {
                description = changedText
            }
        }
    }

    override fun onEditTextClicked(editTextName: String) {
        when (editTextName) {
            DATE -> view.showDatePicker()
            TIME_FROM -> view.showTimeFromPicker()
            TIME_TO -> view.showTimeToPicker()
        }
    }

    override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        val date = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
        val formattedDate = TimeUtils.getFormattedDate(date)
        view.setDate(formattedDate)
        this.date = date
        checkFields()
    }

    override fun onTimeFromSet(hourOfDay: Int, minute: Int) {
        val time = LocalTime.of(hourOfDay, minute, 0, 0)
        val formattedTime = TimeUtils.getFormattedTime(time)
        view.setTimeFrom(formattedTime)
        timeFrom = time
        checkFields()
    }

    override fun onTimeToSet(hourOfDay: Int, minute: Int) {
        val time = LocalTime.of(hourOfDay, minute, 0, 0)
        val formattedTime = TimeUtils.getFormattedTime(time)
        timeTo = time
        view.setTimeTo(formattedTime)
        checkFields()
    }

    override fun getCurrentDate(): LocalDateTime = LocalDateTime.now()

    private fun checkFields() {
        val enabled =
            taskName.isNotBlank() &&
                date != null &&
                timeFrom != null &&
                timeTo != null

        view.setSaveButtonStatus(enabled)
    }

    override fun onSaveButtonClicked() {
        val mDate = date ?: run {
            view.showToast(WRONG_INPUT)
            return
        }

        val mTimeFrom = timeFrom ?: run {
            view.showToast(WRONG_INPUT)
            return
        }
        val mTimeTo = timeTo ?: run {
            view.showToast(WRONG_INPUT)
            return
        }
        val startDate = TimeUtils.getTimestampFromDateTime(mDate.toLocalDate(), mTimeFrom)
        val finishDate = TimeUtils.getTimestampFromDateTime(mDate.toLocalDate(), mTimeTo)

        val newTask = TaskData(0, startDate, finishDate, taskName, description)

        scope.launch(Dispatchers.IO) {
            repository.insertTask(newTask)
        }

        view.backToPreviousFragment()
    }
}
