package com.channelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.model.response.CpDetailX


class IncomeAdaterAdapter(
    internal var context: Context,
    val dataList: ArrayList<CpDetailX>,
    val clickListener: (CpDetailX) -> Unit
) :
    RecyclerView.Adapter<IncomeAdaterAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IncomeAdaterAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_income, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: IncomeAdaterAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: CpDetailX, clickListener: (CpDetailX) -> Unit) {
            val txtMonth = itemView.findViewById(R.id.txtMonth) as TextView
            val txtIncome = itemView.findViewById(R.id.txtIncome) as TextView

            txtMonth.text = data.month
            txtIncome.text = data.amount.toString()

            itemView.setOnClickListener { clickListener(data) }
        }
    }
}