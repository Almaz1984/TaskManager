package com.example.taskmanager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(
    private val taskClickListener: (Long?) -> Int,
    private var tasks: List<Task> = listOf()
) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var taskName: TextView? = null
        private var taskDate: TextView? = null
        private var id: String? = null

        init {
            taskName = itemView.findViewById(R.id.task_name)
            taskDate = itemView.findViewById(R.id.task_date)
            itemView.setOnClickListener {
                taskClickListener.invoke(id?.toLong())
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(
            position: Int
        ) {
            taskName?.text = tasks[position].taskName
            val timeStart = TimeService.getTimeFromTimestamp(tasks[position].dataStart ?: 0)
            val timeFinish = TimeService.getTimeFromTimestamp(tasks[position].dataFinish ?: 0)
            id = tasks[position].id.toString()
            taskDate?.text = "${timeStart}-${timeFinish}"
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