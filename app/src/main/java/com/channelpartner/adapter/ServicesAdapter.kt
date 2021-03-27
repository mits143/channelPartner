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
import com.channelpartner.model.response.SeviceProvider.PackageDetail


class ServicesAdapter(
    internal var context: Context,
    val dataList: ArrayList<PackageDetail>,
    val clickListener: (PackageDetail) -> Unit
) :
    RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_services, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ServicesAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: PackageDetail, clickListener: (PackageDetail) -> Unit) {
            val txtServiceName = itemView.findViewById(R.id.txtServiceName) as TextView
            val txtPackageName = itemView.findViewById(R.id.txtPackageName) as TextView

            txtServiceName.text = data.service_name
            if (data.service_id < 4)
                txtPackageName.text = data.package_name
            else
                txtPackageName.text = data.expiry_date

            itemView.setOnClickListener(View.OnClickListener {
//                data.service_check = !data.service_check
                clickListener(data)
            })
        }
    }
}