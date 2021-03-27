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
import com.channelpartner.model.response.BoardData


class NewBoardAdapter(
    internal var context: Context,
    val dataList: ArrayList<BoardData>,
    val clickListener: (BoardData) -> Unit
) :
    RecyclerView.Adapter<NewBoardAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewBoardAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_autocomplete, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: NewBoardAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: BoardData, clickListener: (BoardData) -> Unit) {
            val chkBox = itemView.findViewById(R.id.chkBox) as CheckBox
            val txtName = itemView.findViewById(R.id.txtSpinner) as TextView

            chkBox.isChecked = data.isChecked
            txtName.text = data.board

            itemView.setOnClickListener { clickListener(data) }
        }
    }
}