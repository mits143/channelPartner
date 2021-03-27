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
import com.channelpartner.model.response.LevelResponse
import com.channelpartner.model.response.All


class LevelAdapter(
    internal var context: Context,
    val dataList: ArrayList<All>,
    val clickListener: (All) -> Unit
) :
    RecyclerView.Adapter<LevelAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_level, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: LevelAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: All, clickListener: (All) -> Unit) {
            val txtCurrentLevel = itemView.findViewById(R.id.txtCurrentLevel) as TextView
            val txtPromotion = itemView.findViewById(R.id.txtPromotion) as TextView
            val txtTarget = itemView.findViewById(R.id.txtTarget) as TextView
            val txtIncome = itemView.findViewById(R.id.txtIncome) as TextView

            txtCurrentLevel.text = data.currentLevel
            txtPromotion.text = data.promotionLevel
            txtTarget.text = data.target
            txtIncome.text = data.incomePercentage

//            Glide.with(this).load(URL).into(image)


            itemView.setOnClickListener(View.OnClickListener {
//                data.service_check = !data.service_check
                clickListener(data)
            })
        }
    }
}