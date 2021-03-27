package com.channelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.otgs.customerapp.model.response.VehicleSubTypeData

class SubTypeVehicleAdapter(
    internal var context: Context,
    val dataList: ArrayList<VehicleSubTypeData>,
    val clickListener: (VehicleSubTypeData) -> Unit
) :
    RecyclerView.Adapter<SubTypeVehicleAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubTypeVehicleAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_brands, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: SubTypeVehicleAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: VehicleSubTypeData, clickListener: (VehicleSubTypeData) -> Unit) {
            val checkBox = itemView.findViewById(R.id.checkBox) as CheckBox
            val txtBrands = itemView.findViewById(R.id.txtBrands) as TextView

            txtBrands.text = data.vehicle_subtype_name
            checkBox.isChecked = data.isChecked

            itemView.setOnClickListener { clickListener(data) }
        }
    }
}