package com.example.taskmanager.fragments.detailtask

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.taskmanager.R
import com.example.taskmanager.TimeService
import com.example.taskmanager.data.models.Task

private const val TASK_TAG = "Task_tag"

class DetailTaskFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        val taskName: TextView = view.findViewById(R.id.task_name)
        val dateStart: TextView = view.findViewById(R.id.task_date_start)
        val dateFinish: TextView = view.findViewById(R.id.task_date_finish)
        val description: TextView = view.findViewById(R.id.task_description)
        val task = arguments?.getSerializable(TASK_TAG) as Task
        taskName.text = task.taskName
        dateStart.text =
            TimeService.getDateTimeFromTimestamp(task.dataStart)
        dateFinish.text =
            TimeService.getDateTimeFromTimestamp(task.dataFinish)
        description.text = task.taskDescription
        return view
    }

    companion object {

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
