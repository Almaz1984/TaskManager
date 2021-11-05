package com.example.taskmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(private val names: List<String>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskItem: TextView? = null
        var viewHolderNumber: TextView? = null

        init {
            taskItem = itemView.findViewById(R.id.task_item)
            viewHolderNumber = itemView.findViewById(R.id.view_holder_number)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.taskItem?.text = names[position]
        holder.viewHolderNumber?.text = position.toString()
    }

    override fun getItemCount(): Int {
        return names.size
    }
}