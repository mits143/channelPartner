package com.channelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.model.response.Course

class SubscriptionCoursestAdapter(
    internal var context: Context,
    val dataList: ArrayList<Course>,
    val clickListener: (Course) -> Unit
) :
    RecyclerView.Adapter<SubscriptionCoursestAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionCoursestAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_subscription, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: SubscriptionCoursestAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: Course, clickListener: (Course) -> Unit) {
            val txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
            val txtMedium = itemView.findViewById(R.id.txtMedium) as TextView
            val txtDate = itemView.findViewById(R.id.txtDate) as TextView
            val txtType = itemView.findViewById(R.id.txtType) as TextView
            val txtStatus = itemView.findViewById(R.id.txtStatus) as TextView
            val txtAmount = itemView.findViewById(R.id.txtAmount) as TextView
            val txtExpiry = itemView.findViewById(R.id.txtExpiry) as TextView

            txtTitle.text = data.Name
            txtMedium.text = data.medium
            txtDate.text = data.subscription_date
            txtType.text = data.subscription_type
            txtStatus.text = data.status
            txtAmount.text = data.paid_amount
            txtExpiry.text = data.exp_date

//            Glide.with(this).load(URL).into(image)


            itemView.setOnClickListener(View.OnClickListener {
//                data.service_check = !data.service_check
                clickListener(data)
            })
        }
    }
}