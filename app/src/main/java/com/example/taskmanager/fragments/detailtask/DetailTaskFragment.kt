package com.example.taskmanager.fragments.detailtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.taskmanager.R
import com.example.taskmanager.data.models.Task
import com.example.taskmanager.utils.TimeUtils

class DetailTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        val task = arguments?.getSerializable(TASK_TAG) as Task

        view.findViewById<TextView>(R.id.task_name).apply {
            text = task.taskName
        }
        view.findViewById<TextView>(R.id.task_date_start).apply {
            text = TimeUtils.getDateTimeFromTimestamp(task.dataStart)
        }
        view.findViewById<TextView>(R.id.task_date_finish).apply {
            text = TimeUtils.getDateTimeFromTimestamp(task.dataFinish)
        }
        view.findViewById<TextView>(R.id.task_description).apply {
            text = task.taskDescription
        }
        return view
    }

    companion object {
        private const val TASK_TAG = "Task_tag"

        @JvmStatic
        fun newInstance(task: Task): DetailTaskFragment {
            return DetailTaskFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TASK_TAG, task)
                }
            }
        }
    }
}
