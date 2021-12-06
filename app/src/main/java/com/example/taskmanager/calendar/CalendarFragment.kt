package com.example.taskmanager.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.*
import com.example.taskmanager.calendar.adapter.TaskAdapter
import java.time.LocalDateTime

class CalendarFragment : Fragment(), CalendarContract.View {
    private var selectedDay = LocalDateTime.now()
    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: CalendarPresenter
    private lateinit var taskListAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun updateTaskList(taskList: List<Task>) {
        taskListAdapter.updateTasks(taskList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CalendarPresenter(view = this)

        val taskClickListener = { id: Long -> presenter.onTaskClicked(id) }
        taskListAdapter = TaskAdapter(taskClickListener)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = taskListAdapter
        }

        val calendarView: CalendarView = view.findViewById(R.id.calendar_view)
        calendarView.date = TimeService.getTimestampFromDateTime(selectedDay)
        presenter.onDateChanged(selectedDay)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDay = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
            presenter.onDateChanged(selectedDay)

        }
    }

    override fun showTaskFragment(task: Task) {
        val taskFragment = TaskFragment.newInstance(task)
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, taskFragment)
            .addToBackStack("Calendar")
            .commit()
    }

}

