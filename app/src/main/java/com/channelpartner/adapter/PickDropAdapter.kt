package com.channelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.model.response.Pickdrp


class PickDropAdapter(
    internal var context: Context,
    val dataList: ArrayList<Pickdrp>,
    val clickListener: (Pickdrp) -> Unit
) :
    RecyclerView.Adapter<PickDropAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PickDropAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_pick_drop, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: PickDropAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: Pickdrp, clickListener: (Pickdrp) -> Unit) {
            val checkBox = itemView.findViewById(R.id.checkBox) as CheckBox
            val txtBrands = itemView.findViewById(R.id.txtBrands) as TextView
            val spinSlot = itemView.findViewById(R.id.spinSlot) as Spinner

            txtBrands.text = data.pickdropTime
            checkBox.isChecked = data.isChecked
            spinSlot.isEnabled = data.isChecked

            spinSlot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    data.pickdropSlot = parentView!!.getItemAtPosition(position).toString();
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // your code here
                }
            }

            itemView.setOnClickListener {
                clickListener(data)
                modifyItem(adapterPosition, data)
            }
        }
    }

    fun modifyItem(position: Int, model: Pickdrp?) {
        dataList.set(position, model!!)
        notifyItemChanged(position)
    }
}