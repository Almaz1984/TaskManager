package com.example.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import java.util.*


class CalendarFragment : Fragment() {
    private var selectedDay: Calendar = Calendar.getInstance()
    private var recyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskClickListener = { id: Long? ->
            val task = getSampleListFromJson().find { it.id == (id) }
            showTaskFragment(task)
        }
        val taskListAdapter = CustomRecyclerAdapter(taskClickListener)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = taskListAdapter
        }

        val calendarView: CalendarView = view.findViewById(R.id.calendar_view)
        calendarView.date = selectedDay.timeInMillis

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDay.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            taskListAdapter.updateTasks(getSelectedDayTaskList(selectedDay.timeInMillis))
        }
    }

    private fun showTaskFragment(task: Task?): Int {
        val taskFragment = TaskFragment.newInstance(task)
        return parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, taskFragment)
            .addToBackStack("Calendar")
            .commit()
    }

    private fun getSelectedDayTaskList(selectedDay: Long): List<Task> {
        val taskList = getSampleListFromJson()
        return taskList.filter {
            TimeService.getDateFromTimestamp(it.dataStart) == TimeService.getDateFromTimestamp(
                selectedDay
            )
        }
    }

    private fun getSampleListFromJson(): List<Task> {
        val sampleJsonTaskList = listOf(
            "{'id':'1'," +
                    "'date_start':'1637957621641'," +
                    "'date_finish':'1637957621642'," +
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
            taskList.add(gson.fromJson(task, TaskJson::class.java).mapToTask())
        }
        return taskList
    }

}

