package com.channelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.model.response.CpDetail
import com.channelpartner.model.response.Notification


class NotificationAdapter(internal var context: Context, val dataList: ArrayList<Notification>, val clickListener: (Notification) -> Unit) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_notification, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position],  clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: Notification, clickListener: (Notification) -> Unit) {
            val txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
            val txtMessage = itemView.findViewById(R.id.txtMessage) as TextView

            txtTitle.text = "Title: " + data.title
            txtMessage.text = "Message: " + data.msg

            itemView.setOnClickListener { clickListener(data)}
        }
    }
}