package com.channelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.model.response.CpDetail
import com.channelpartner.model.response.ServiceMasterLists


class AutoHubAdapter(
    internal var context: Context,
    val dataList: ArrayList<ServiceMasterLists>,
    val clickListener: (ServiceMasterLists) -> Unit
) :
    RecyclerView.Adapter<AutoHubAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoHubAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_auto_hub, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: AutoHubAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: ServiceMasterLists, clickListener: (ServiceMasterLists) -> Unit) {
            val imgView = itemView.findViewById(R.id.imgView) as ImageView
            val checkBox = itemView.findViewById(R.id.checkBox) as CheckBox
            val txtService = itemView.findViewById(R.id.txtService) as TextView

            txtService.text = data.service_name

//            Glide.with(this).load(URL).into(image)


            itemView.setOnClickListener(View.OnClickListener {
//                data.service_check = !data.service_check
                clickListener(data)
            })
        }
    }
}