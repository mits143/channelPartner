package com.channelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.otgs.customerapp.model.response.VehicleBrandData

class BrandsAdapter(
    internal var context: Context,
    val dataList: ArrayList<VehicleBrandData>,
    val clickListener: (VehicleBrandData) -> Unit
) :
    RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrandsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_brands, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: BrandsAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: VehicleBrandData, clickListener: (VehicleBrandData) -> Unit) {
            val checkBox = itemView.findViewById(R.id.checkBox) as CheckBox
            val txtBrands = itemView.findViewById(R.id.txtBrands) as TextView

            txtBrands.text = data.vehicle_brand_name
            checkBox.isChecked = data.isChecked

            itemView.setOnClickListener { clickListener(data) }
        }
    }
}