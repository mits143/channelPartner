package com.channelpartner.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.channelpartner.R
import com.channelpartner.activities.RenewalDetail
import com.channelpartner.adapter.PackageRequestAdapter
import com.channelpartner.model.response.Cp
import com.channelpartner.model.response.PackageRerquestResponse
import com.channelpartner.presenter.HistoryPresenter
import com.channelpartner.view.RenewalView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.fragment_renewal.*
import retrofit2.Response
import java.util.*


class FragmentHistory : Fragment(), RenewalView.MainView {

    var historyPresenter: HistoryPresenter? = null
    var packagerequestadapter: PackageRequestAdapter? = null
    var cpList: ArrayList<Cp>? = arrayListOf()
    var page: Int? = 1
    var status = 0
    var fromdate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_renewal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llDate.visibility = View.VISIBLE
        flSpinner.visibility = View.VISIBLE

        historyPresenter = HistoryPresenter(context, this)


        val c = Calendar.getInstance()
        edtFromDate.setOnClickListener {
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                context!!,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    fromdate = "" + (month) + "-" + dayOfMonth + "-" + year
                    edtFromDate.setText(" " + (month + 1) + "-" + dayOfMonth + "-" + year)
                    edtToDate.setText("")
                },
                year,
                month,
                day
            )
            dpd.datePicker.maxDate = c.timeInMillis;
            dpd.show()
        }

        edtToDate.setOnClickListener {
            if (!TextUtils.isEmpty(fromdate)) {
                val getfrom = fromdate.split("-".toRegex()).toTypedArray()
                val year1: Int
                val month1: Int
                val day1: Int
                year1 = getfrom[2].toInt()
                month1 = getfrom[0].toInt()
                day1 = getfrom[1].toInt()
                val c1 = Calendar.getInstance()
                c1[year1, month1] = day1
                val dpd = DatePickerDialog(
                    context!!,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        // Display Selected date in TextView
                        edtToDate.setText(" " + (month1 + 1) + "-" + dayOfMonth + "-" + year)
                    },
                    year1,
                    month1,
                    day1
                )
                dpd.datePicker.maxDate = c.timeInMillis;
                dpd.datePicker.minDate = c1.timeInMillis;
                dpd.show()
            } else {
                Toast.makeText(context!!, "First select from date", Toast.LENGTH_SHORT).show()
            }
        }

        spinType?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                status = position
            }

        }

        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.setLayoutManager(layoutManager)
        packagerequestadapter = PackageRequestAdapter(context!!, cpList!!
        ) { cpList -> onClick(cpList) }

        recyclerView!!.adapter = packagerequestadapter

//        historyPresenter!!.loadData(
//            sessionManager.getAuthToken(context)!!,
//            edtToDate.text.toString().trim(),
//            edtFromDate.text.toString().trim(),
//            status,
//            sessionManager.getUser_ID(context)!!.toInt(),
//            sessionManager.getUserType(context)!!.toInt()
//        )

        swipeRefresh.setOnRefreshListener {
//            historyPresenter!!.loadData(
//                sessionManager.getAuthToken(context)!!,
//                edtToDate.text.toString().trim(),
//                edtFromDate.text.toString().trim(),
//                status,
//                sessionManager.getUser_ID(context)!!.toInt(),
//                sessionManager.getUserType(context)!!.toInt()
//            )
        }

        txtGetData.setOnClickListener {
//            historyPresenter!!.loadData(
//                sessionManager.getAuthToken(context)!!,
//                edtToDate.text.toString().trim(),
//                edtFromDate.text.toString().trim(),
//                status,
//                sessionManager.getUser_ID(context)!!.toInt(),
//                sessionManager.getUserType(context)!!.toInt()
//            )
        }
    }

    fun onClick(cpList: Cp) {
        startActivity(
            Intent(context, RenewalDetail::class.java)
                .putExtra("id", cpList.history_id)
                .putExtra("type", 3)
        )
    }

    override fun showProgressbar() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipeRefresh.isRefreshing = false
    }

    override fun onSuccess(responseModel: Response<PackageRerquestResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.cp.size > 0) {
            cpList!!.clear()
            llDirectNoData.visibility = View.GONE
            cpList!!.addAll(responseModel.body()!!.cp)
            packagerequestadapter!!.notifyDataSetChanged()
        } else {
            cpList!!.clear()
            llDirectNoData.visibility = View.VISIBLE
        }
    }

    override fun onError(errorCode: Int) {
        llDirectNoData.visibility = View.VISIBLE
        if (errorCode == 500) {
            Toast.makeText(
                context,
                getString(R.string.internal_server_error),
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(throwable: Throwable) {
        llDirectNoData.visibility = View.VISIBLE
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        historyPresenter!!.onStop()
    }
}
