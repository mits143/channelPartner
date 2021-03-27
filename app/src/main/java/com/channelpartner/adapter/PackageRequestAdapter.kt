package com.channelpartner.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.activities.RenewalDetail
import com.channelpartner.model.response.Cp


class PackageRequestAdapter(
    internal var context: Context,
    val dataList: ArrayList<Cp>,
    val clickListener: (Cp) -> Unit
) :
    RecyclerView.Adapter<PackageRequestAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PackageRequestAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_package_request, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: PackageRequestAdapter.ViewHolder, position: Int) {
        holder.bindItems(context, dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(context: Context, data: Cp, clickListener: (Cp) -> Unit) {
            val imgView = itemView.findViewById(R.id.imgView) as ImageView
            val txtName = itemView.findViewById(R.id.txtName) as TextView
            val txtCity = itemView.findViewById(R.id.txtCity) as TextView
            val txtPhone = itemView.findViewById(R.id.txtPhone) as TextView
            val txtDate = itemView.findViewById(R.id.txtDate) as TextView
            val txtStatus = itemView.findViewById(R.id.txtStatus) as TextView

            txtName.text = data.gagare_name
            txtCity.text = data.gagare_address
            txtPhone.text = data.gagare_mobile
            txtDate.text = data.request_date

            if (data.communication_status == 0)
                txtStatus.text = "Pending"
            else if (data.communication_status == 1)
                txtStatus.text = "delivered"
            else if (data.communication_status == 2)
                txtStatus.text = "cancelled"
            else if (data.communication_status == 3)
                txtStatus.text = "reject"
//            Glide.with(this).load(URL).into(image)

            itemView.setOnClickListener {
                clickListener(data)
            }
        }
    }
}