package com.example.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanager.calendar.CalendarFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendarFragment = CalendarFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, calendarFragment)
            .commit()
    }


}