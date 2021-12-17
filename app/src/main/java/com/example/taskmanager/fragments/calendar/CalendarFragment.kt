package com.example.taskmanager.fragments.calendar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.TimeService
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.fragments.calendar.adapter.TaskAdapter
import com.example.taskmanager.fragments.detailtask.DetailTaskFragment
import java.time.LocalDateTime

class CalendarFragment : Fragment(), CalendarContract.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskListAdapter: TaskAdapter
    private var presenter: CalendarPresenter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = CalendarPresenter(view = this)
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }

    override fun updateTaskList(taskList: List<Task>) {
        taskListAdapter.updateTasks(taskList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskClickListener = { id: Long -> presenter!!.onTaskClicked(id) }
        taskListAdapter = TaskAdapter(taskClickListener)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = taskListAdapter
        }

        val calendarView: CalendarView = view.findViewById(R.id.calendar_view)
        calendarView.date = TimeService.getTimestampFromDateTime(presenter!!.getSelectedDate())
        presenter!!.onDateChanged(presenter!!.getSelectedDate())

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            presenter!!.onDateChanged(LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0))
        }
    }

    override fun showDetailTaskFragment(task: Task) {
        val taskFragment = DetailTaskFragment.newInstance(task)
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, taskFragment)
            .addToBackStack("Calendar")
            .commit()
    }
}