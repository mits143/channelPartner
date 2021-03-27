package com.channelpartner.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.model.response.CpDetail
import com.channelpartner.utils.utility
import com.channelpartner.utils.utility.loadImage


class OTGSChannelPartnerAdapter(
    internal var context: Context,
    val dataList: ArrayList<CpDetail>,
    val clickListener: (CpDetail) -> Unit
) :
    RecyclerView.Adapter<OTGSChannelPartnerAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OTGSChannelPartnerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_otgs, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: OTGSChannelPartnerAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: CpDetail, clickListener: (CpDetail) -> Unit) {
            val imgView = itemView.findViewById(R.id.imgView) as ImageView
            val txtName = itemView.findViewById(R.id.txtName) as TextView
            val txtSRNO = itemView.findViewById(R.id.txtSRNO) as TextView
            val txtCity = itemView.findViewById(R.id.txtCity) as TextView
            val txtDate = itemView.findViewById(R.id.txtDate) as TextView
            val txtIntroducerName = itemView.findViewById(R.id.txtIntroducerName) as TextView

            txtName.text = data.fullName
            txtSRNO.text = data.uniqueNo
            txtCity.text = data.cityName
            txtDate.text = data.registrationDate
            txtIntroducerName.text = data.introducerName
//            loadImage(
//                imgView.context as Activity, true,
//                2,
//                data.profilePictureURL,
//                imgView
//            )

            itemView.setOnClickListener { clickListener(data) }
        }
    }
}