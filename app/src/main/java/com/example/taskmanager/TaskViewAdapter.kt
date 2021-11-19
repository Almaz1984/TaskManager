package com.example.taskmanager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskName: TextView? = null
        var taskDate: TextView? = null

        init {
            taskName = itemView.findViewById(R.id.task_name)
            taskDate = itemView.findViewById(R.id.task_date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.taskName?.text = tasks[position].taskName
        val timeStart= TimeService().getTimeFromTimestamp(tasks[position].dataStart)
        val timeFinish= TimeService().getTimeFromTimestamp(tasks[position].dataFinish)
        holder.taskDate?.text = "${timeStart}-${timeFinish}"
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}