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
import com.channelpartner.model.response.AllFaq


class FAQAdapter(
    internal var context: Context,
    val dataList: ArrayList<AllFaq>,
    val clickListener: (AllFaq) -> Unit
) :
    RecyclerView.Adapter<FAQAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_faq, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: FAQAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: AllFaq, clickListener: (AllFaq) -> Unit) {
            val txtQues = itemView.findViewById(R.id.txtQues) as TextView
            val txtAns = itemView.findViewById(R.id.txtAns) as TextView

            txtQues.text = data.question
            txtAns.text = data.answer

//            Glide.with(this).load(URL).into(image)


            itemView.setOnClickListener(View.OnClickListener {
//                data.service_check = !data.service_check
                clickListener(data)
            })
        }
    }
}