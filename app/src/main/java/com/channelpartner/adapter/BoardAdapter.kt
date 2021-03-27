package com.channelpartner.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.activities.ClassRegistration
import com.channelpartner.model.request.PackageDetail
import com.channelpartner.model.request.StandradDetail
import com.channelpartner.model.response.AllBoard
import com.channelpartner.model.response.StandardData
import com.channelpartner.utils.EditTextDrawableSize
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_class_registration.*


class BoardAdapter(
    internal var context: Context,
    val dataList: ArrayList<AllBoard>,
    val clickListener: (AllBoard) -> Unit
) :
    RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    var newStandardAdapter: NewStandardAdapter? = null

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BoardAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_board, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: BoardAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: AllBoard, clickListener: (AllBoard) -> Unit) {
            val txtBoard = itemView.findViewById(R.id.txtBoard) as TextView
            val autoTextStandard =
                itemView.findViewById(R.id.autoTextStandard) as EditTextDrawableSize

            txtBoard.text = data.board
            autoTextStandard.setOnClickListener {
                showStandardDialog(itemView, autoTextStandard, data)
            }

            itemView.setOnClickListener { clickListener(data) }
        }
    }

    fun showStandardDialog(view: View, autoTextStandard: EditTextDrawableSize, data: AllBoard) {
        val dialogView =
            LayoutInflater.from(view.context).inflate(R.layout.dialog_recyclerview, null)
        val dialog = BottomSheetDialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogView)

        val rvDialog = dialogView.findViewById(R.id.rvDialog) as RecyclerView
        val btnDone = dialogView.findViewById(R.id.btnDone) as Button

        val layoutManager = LinearLayoutManager(context)
        rvDialog!!.setLayoutManager(layoutManager)
        newStandardAdapter = NewStandardAdapter(
            context,
            data.standards!!
        ) { standardList1 -> onClick(standardList1) }
        rvDialog!!.setAdapter(newStandardAdapter)

        btnDone.setOnClickListener {
            val sb = StringBuffer()
            for (i in data.standards.indices) {
                if (data.standards[i].isChecked) {
                    sb.append(data.standards[i].standard)
                    sb.append(", ")
                }
            }
            autoTextStandard.setText(sb.toString())
            dialog.dismiss()
        }
        dialog.show()

    }

    fun onClick(data: StandardData) {
        data.isChecked = !data.isChecked
        newStandardAdapter!!.notifyDataSetChanged()
    }
}