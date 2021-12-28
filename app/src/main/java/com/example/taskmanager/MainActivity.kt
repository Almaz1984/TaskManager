package com.example.taskmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager.fragments.calendar.CalendarFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showCalendarFragment(savedInstanceState)
    }

    private fun showCalendarFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val calendarFragment = CalendarFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, calendarFragment)
                .commit()
        }
    }
}
