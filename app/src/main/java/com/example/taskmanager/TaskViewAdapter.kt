package com.example.taskmanager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(private val names: List<Task>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskItem: TextView? = null
        var taskDateStart: TextView? = null

        init {
            taskItem = itemView.findViewById(R.id.task_name)
            taskDateStart = itemView.findViewById(R.id.task_date_start)
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
        holder.taskItem?.text = names[position].taskName
        val timeStart= TimeService().getTimeFromTimestamp(names[position].dataStart)
        val timeFinish= TimeService().getTimeFromTimestamp(names[position].dataFinish)
        holder.taskDateStart?.text = "${timeStart}-${timeFinish}"
    }

    override fun getItemCount(): Int {
        return names.size
    }
}