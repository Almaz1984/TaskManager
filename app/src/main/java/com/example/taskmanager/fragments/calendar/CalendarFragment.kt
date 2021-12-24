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
import com.example.taskmanager.utils.TimeUtils
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.fragments.calendar.adapter.TaskAdapter
import com.example.taskmanager.fragments.detailtask.DetailTaskFragment
import com.example.taskmanager.fragments.newtask.NewTaskFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime

class CalendarFragment : Fragment(), CalendarContract.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskListAdapter: TaskAdapter
    private var presenter: CalendarPresenter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = CalendarPresenter(view = this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskClickListener =
            { id: Long? -> presenter?.onTaskClicked(id) ?: throw IllegalStateException() }
        taskListAdapter = TaskAdapter(taskClickListener)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = taskListAdapter
        }

        val calendarView: CalendarView = view.findViewById(R.id.calendar_view)
        calendarView.date = TimeUtils.getTimestampFromDateTime(presenter!!.getSelectedDate())
        presenter?.getSelectedDate()?.let { presenter?.onDateChanged(it) }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            presenter?.onDateChanged(LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0))
        }

        val addTaskButton = view.findViewById<FloatingActionButton>(R.id.add_task_button)
        addTaskButton.setOnClickListener { presenter?.onAddTaskClicked() }
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }

    override fun updateTaskList(taskList: List<Task>) {
        taskListAdapter.updateTasks(taskList)
    }

    override fun showDetailTaskFragment(task: Task) {
        val taskFragment = DetailTaskFragment.newInstance(task)
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, taskFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun showNewTaskFragment() {
        val taskFragment = NewTaskFragment.newInstance()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, taskFragment)
            .addToBackStack(null)
            .commit()
    }
}
