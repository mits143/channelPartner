package com.channelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.channelpartner.R
import com.channelpartner.model.response.AllState
import kotlinx.android.synthetic.main.cell_spinner.view.*

class SpinStateAdapter(
    ctx: Context,
    moods: List<AllState>
) :
    ArrayAdapter<AllState>(ctx, 0, moods) {
    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val mood = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.cell_spinner,
            parent,
            false
        )
        view.txtSpinner.text = mood!!.name
        return view
    }


}