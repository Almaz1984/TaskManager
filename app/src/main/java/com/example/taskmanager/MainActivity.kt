package com.example.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        )

        val calendarView:CalendarView=findViewById(R.id.calendar_view)
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