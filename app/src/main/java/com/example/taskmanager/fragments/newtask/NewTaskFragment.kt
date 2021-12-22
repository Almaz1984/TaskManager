package com.example.taskmanager.fragments.newtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.taskmanager.R

const val IS_24_TIME_FORMAT = false

class NewTaskFragment : Fragment(), NewTaskContract.View {
    private var presenter: NewTaskPresenter? = null
    private lateinit var taskName: EditText
    private lateinit var dateEdit: EditText
    private lateinit var timeFromEdit: EditText
    private lateinit var timeToEdit: EditText
    private lateinit var description: EditText
    private lateinit var saveButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = NewTaskPresenter(view = this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskName = view.findViewById(R.id.task_name_edit)
        taskName.setOnClickListener { presenter!!.onFieldClicked(TASK_NAME) }

        dateEdit = view.findViewById(R.id.date_edit)
        dateEdit.setOnClickListener { presenter!!.onFieldClicked(DATE) }

        timeFromEdit = view.findViewById(R.id.time_from_edit)
        timeFromEdit.setOnClickListener { presenter!!.onFieldClicked(TIME_FROM) }

        timeToEdit = view.findViewById(R.id.time_to_edit)
        timeToEdit.setOnClickListener { presenter!!.onFieldClicked(TIME_TO) }

        description = view.findViewById(R.id.description_edit)
        description.setOnClickListener { presenter!!.onFieldClicked(DESCRIPTION) }

        saveButton = view.findViewById(R.id.save_button)
        saveButton.isEnabled = false
        saveButton.setOnClickListener { presenter!!.onSaveButtonClicked() }
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }

    override fun showDatePicker() {
        val currentDate = presenter!!.getCurrentDate()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                presenter!!.onDateSet(year, month, dayOfMonth)
            },
            currentDate.year, currentDate.month.value - 1, currentDate.dayOfMonth
        )
        datePicker.show()
    }

    override fun showTimeFromPicker() {
        val currentTime = presenter!!.getCurrentDate().toLocalTime()
        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                presenter!!.onTimeFromSet(hourOfDay, minute)
            },
            currentTime.hour, currentTime.minute, IS_24_TIME_FORMAT
        )
        timePicker.show()
    }

    override fun showTimeToPicker() {
        val currentTime = presenter!!.getCurrentDate().toLocalTime()
        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                presenter!!.onTimeToSet(hourOfDay, minute)
            },
            currentTime.hour, currentTime.minute, IS_24_TIME_FORMAT
        )
        timePicker.show()
    }

    override fun setDate(formattedDate: String) {
        dateEdit.setText(formattedDate)
    }

    override fun setTimeFrom(formattedTime: String) {
        timeFromEdit.setText(formattedTime)
    }

    override fun setTimeTo(formattedTime: String) {
        timeToEdit.setText(formattedTime)
    }

    override fun setSaveButtonStatus(enabled: Boolean) {
        saveButton.isEnabled = enabled
    }

    override fun getTaskName() = taskName.text.toString()

    override fun getDescription() = description.text.toString()

    companion object {

        @JvmStatic
        fun newInstance(): NewTaskFragment {
            return NewTaskFragment()
        }
    }
}
