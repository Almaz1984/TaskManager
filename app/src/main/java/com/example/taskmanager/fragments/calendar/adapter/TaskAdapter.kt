package com.example.taskmanager.fragments.calendar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.TimeService
import com.example.taskmanager.data.models.Task

class TaskAdapter(
    private val taskClickListener: (Long) -> Unit,
    private var tasks: List<Task> = listOf()
) :
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var taskName: TextView? = null
        private var taskDate: TextView? = null
        private var id: String? = null

        init {
            taskName = itemView.findViewById(R.id.task_name)
            taskDate = itemView.findViewById(R.id.task_date)
            itemView.setOnClickListener {
                taskClickListener.invoke(id?.toLong() ?: 0)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(
            position: Int
        ) {
            taskName?.text = tasks[position].taskName
            val timeStart = TimeService.getTimeFromTimestamp(tasks[position].dataStart)
            val timeFinish = TimeService.getTimeFromTimestamp(tasks[position].dataFinish)
            id = tasks[position].id.toString()
            taskDate?.text = "$timeStart-$timeFinish"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}
