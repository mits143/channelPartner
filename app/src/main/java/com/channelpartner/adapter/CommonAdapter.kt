package com.channelpartner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.model.response.AllState
import kotlinx.android.synthetic.main.cell_spinner.view.*

class CommonAdapter(private val dataList: List<AllState>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: AllState, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_spinner, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val data = dataList[position]

            viewHolder.txtSpinner.text = data.name

            viewHolder.itemView.setOnClickListener { v: View ->
                itemClickListener.onItemClick(
                    v,
                    dataList[position],
                    position
                )
            }

        }
    }

    override fun getItemCount(): Int {

        return dataList?.size ?: 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var txtSpinner: TextView = view.txtSpinner

    }
}
