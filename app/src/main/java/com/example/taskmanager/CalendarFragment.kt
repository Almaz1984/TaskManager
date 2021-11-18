package com.example.taskmanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class CalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_calendar, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView : RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL
            )
        )

        val calendarView: CalendarView =view.findViewById(R.id.calendar_view)
        calendarView.setOnDateChangeListener{view, year, month, dayOfMonth ->
            val selectedDay = Calendar.getInstance()
            selectedDay.set(Calendar.YEAR, year)
            selectedDay.set(Calendar.MONTH, month)
            selectedDay.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedDay.set(Calendar.HOUR_OF_DAY, 0)
            selectedDay.set(Calendar.MINUTE, 0)
            selectedDay.set(Calendar.SECOND, 0)
            selectedDay.set(Calendar.MILLISECOND, 0)
            updateTasks(recyclerView, selectedDay.timeInMillis)
        }

    }

    private fun updateTasks(recyclerView: RecyclerView, selectedDay:Long) {
        val selectedDayTaskList = DataLayer().getSelectedDayTaskList(selectedDay)
        recyclerView.adapter = CustomRecyclerAdapter(selectedDayTaskList)
        Log.d(MYTAG, "selectedDayTaskList=${selectedDayTaskList.toString()}")
    }

}

