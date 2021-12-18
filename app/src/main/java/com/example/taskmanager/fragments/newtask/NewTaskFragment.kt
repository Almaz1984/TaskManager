package com.example.taskmanager.fragments.newtask

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.taskmanager.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewTaskFragment : Fragment(), NewTaskContract.View {
    private var presenter: NewTaskPresenter? = null
    private var dayId: Long? = null
    private lateinit var dateEdit: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateEdit = view.findViewById(R.id.date_edit)
        dateEdit.setOnClickListener { presenter!!.onDateClicked() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = NewTaskPresenter(view = this)
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }

    companion object {

        @JvmStatic
        fun newInstance(): NewTaskFragment {
            return NewTaskFragment()
        }
    }

    override fun showDatePicker() {
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDate.set(Calendar.HOUR_OF_DAY, 0)
                selectedDate.set(Calendar.MINUTE, 0)
                selectedDate.set(Calendar.SECOND, 0)
                selectedDate.set(Calendar.MILLISECOND, 0)

                val date =
                    SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH).format(selectedDate.time)

                dayId = selectedDate.time.time
                dateEdit.setText(date)
            },
            now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }
}
