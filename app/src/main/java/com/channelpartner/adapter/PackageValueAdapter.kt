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
import com.channelpartner.model.response.AllX


class PackageValueAdapter(
    internal var context: Context,
    val dataList: ArrayList<AllX>,
    val clickListener: (AllX) -> Unit
) :
    RecyclerView.Adapter<PackageValueAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PackageValueAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_package_value, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: PackageValueAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: AllX, clickListener: (AllX) -> Unit) {
            val imgView = itemView.findViewById(R.id.imgView) as ImageView
            val txt_type = itemView.findViewById(R.id.txt_type) as TextView
            val txtvtype = itemView.findViewById(R.id.txtvtype) as TextView
            val txt_amt = itemView.findViewById(R.id.txt_amt) as TextView

            txt_type.text = data.package_name
            txtvtype.text = data.vehicles
            txt_amt.text = data.amount

//            Glide.with(this).load(URL).into(image)


            itemView.setOnClickListener(View.OnClickListener {
//                data.service_check = !data.service_check
                clickListener(data)
            })
        }
    }
}