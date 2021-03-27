package com.channelpartner.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.channelpartner.R
import com.channelpartner.adapter.IncomeAdaterAdapter
import com.channelpartner.adapter.SpinnerMonthAdapter
import com.channelpartner.adapter.SpinnerYearAdapter
import com.channelpartner.model.response.*
import com.channelpartner.presenter.MyIncomePresenter
import com.channelpartner.view.MyIncomeView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_my_income.*
import kotlinx.android.synthetic.main.dialog_income_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class MyIncome : AppCompatActivity(), MyIncomeView.MainView {

    var myIncomePresenter: MyIncomePresenter? = null
    var spinneryearadapter: SpinnerYearAdapter? = null
    var spinnerFromMonthAdapter: SpinnerMonthAdapter? = null
    var spinnerTOMonthAdapter: SpinnerMonthAdapter? = null
    var incomeadateradapter: IncomeAdaterAdapter? = null
    var yearList: ArrayList<AllYear>? = arrayListOf()
    var monthList: ArrayList<AllMonth>? = arrayListOf()
    var monthList1: ArrayList<AllMonth>? = arrayListOf()
    var incomeList: ArrayList<CpDetailX>? = arrayListOf()
    var spinYear = 0
    var spinFromMonth = 0
    var spinToMonth = 0
    var isBackAllowed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_income)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.my_income)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        myIncomePresenter = MyIncomePresenter(this, this)
        myIncomePresenter!!.loadYearData(
            sessionManager.getAuthToken(this)!!
        )

        txtTotalIncome.setOnClickListener {
            isBackAllowed = false
            llMonth.visibility = View.GONE
            myIncomePresenter!!.loadNetIncomeData(
                sessionManager.getAuthToken(this)!!,
                sessionManager.getUser_ID(applicationContext)!!.toInt(),
                sessionManager.getUser_ID(applicationContext)!!.toInt(),
                sessionManager.getUserType(applicationContext)!!.toInt(),
                spinYear
            )
        }

        txtIndividualIncome.setOnClickListener {
            startActivity(Intent(this, IndividualActivity::class.java))
        }

        spinnerYear?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinYear = yearList!!.get(position).id.toInt()
            }

        }

        spinnerFromMonth?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinFromMonth = monthList!!.get(position).id.toInt()

                myIncomePresenter!!.getAllToMonths(
                    sessionManager.getAuthToken(this@MyIncome)!!,
                    monthList!!.get(position).id
                )
            }

        }

        spinnerToMonth?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinToMonth = monthList1!!.get(position).id.toInt()
                myIncomePresenter!!.loadIncomeYearData(
                    sessionManager.getAuthToken(this@MyIncome)!!,
                    sessionManager.getUser_ID(applicationContext)!!.toInt(),
                    spinFromMonth,
                    spinToMonth,
                    sessionManager.getUser_ID(applicationContext)!!.toInt(),
                    sessionManager.getUserType(applicationContext)!!.toInt(),
                    spinYear
                )
            }
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(layoutManager)
        incomeadateradapter = IncomeAdaterAdapter(this, incomeList!!,
            { incomeList -> onClick(incomeList) })
        recyclerView!!.setAdapter(incomeadateradapter)
    }

    fun onClick(cpDetail: CpDetailX) {
        myIncomePresenter!!.loadMonthlyIncomeDetailsData(
            sessionManager.getAuthToken(this)!!,
            sessionManager.getUser_ID(this)!!.toInt(),
            cpDetail.month_id,
            sessionManager.getUser_ID(this)!!.toInt(),
            sessionManager.getUserType(this)!!.toInt(),
            spinYear

        )
    }

    override fun showProgressbar() {
    }

    override fun hideProgressbar() {
    }

    override fun onSuccessYear(responseModel: Response<YearResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.isNotEmpty()) {
            yearList!!.clear()
            yearList!!.addAll(responseModel.body()!!)
            spinneryearadapter = SpinnerYearAdapter(this, yearList!!)
            spinnerYear.adapter = spinneryearadapter;

            myIncomePresenter!!.loadMonthData(
                sessionManager.getAuthToken(this)!!
            )
        }
    }

    override fun onSuccessMonth(responseModel: Response<MonthResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.isNotEmpty()) {
            monthList!!.clear()
            monthList!!.addAll(responseModel.body()!!)
            spinnerFromMonthAdapter = SpinnerMonthAdapter(this, monthList!!)
            spinnerFromMonth.adapter = spinnerFromMonthAdapter;
        }
    }

    override fun onSuccessMonth1(responseModel: Response<MonthResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.isNotEmpty()) {
            monthList1!!.clear()
            monthList1!!.addAll(responseModel.body()!!)
            spinnerTOMonthAdapter = SpinnerMonthAdapter(this, monthList1!!)
            spinnerToMonth.adapter = spinnerTOMonthAdapter;
        }
    }

    override fun onSuccessIncomeDetail(responseModel: Response<IncomeDetail>) {
        if (responseModel.body() != null && responseModel.body()!!.CpDetails.isNotEmpty()) {
            incomeList!!.clear()
            incomeList!!.addAll(responseModel.body()!!.CpDetails)
            incomeadateradapter!!.notifyDataSetChanged()
            llDirectNoData.visibility = View.GONE
        } else {
            if (incomeList!!.isNotEmpty())
                incomeList!!.clear()
            llDirectNoData.visibility = View.VISIBLE
        }
    }

    override fun onSuccessMonthlyIncomeDetail(responseModel: Response<MonthlyIncomeDetailResponse>) {
        if (responseModel.body() != null) {
            showDialog(responseModel.body()!!.CpDetails)
        }
    }

    override fun onError(errorCode: Int) {
        if (errorCode == 500) {
            Toast.makeText(this, getString(R.string.internal_server_error), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    private fun showDialog(cpDetail: CpDetails) {
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_income_detail, null)
        val dialog = BottomSheetDialog(this)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogView)
        val txtYear = dialogView.findViewById(R.id.txtMonth) as TextView
        val txtMonth = dialogView.findViewById(R.id.txtMonth) as TextView
        val txtLevelIncome = dialogView.findViewById(R.id.txtLevelIncome) as TextView
        val txtResidualMonth = dialogView.findViewById(R.id.txtResidualMonth) as TextView
        val txtRoyalIncome = dialogView.findViewById(R.id.txtRoyalIncome) as TextView
        val txtTotalIncome1 = dialogView.findViewById(R.id.txtTotalIncome1) as TextView
        val txtTDS = dialogView.findViewById(R.id.txtTDS) as TextView
        val txtNetIncome = dialogView.findViewById(R.id.txtNetIncome) as TextView

        txtYear.text = cpDetail.year
        txtMonth.text = cpDetail.month
        txtLevelIncome.text = cpDetail.level_difference_income.toString()
        txtResidualMonth.text = cpDetail.residual_income.toString()
        txtRoyalIncome.text = cpDetail.royalty_income.toString()
        txtTotalIncome1.text = cpDetail.total_income.toString()
        txtTDS.text = cpDetail.tds.toString()
        txtNetIncome.text = cpDetail.net_income.toString()

        dialog.show()

    }

    override fun onBackPressed() {
        if (isBackAllowed) {
            super.onBackPressed()
        } else {
            isBackAllowed = true
            llMonth.visibility = View.VISIBLE
            myIncomePresenter!!.loadIncomeYearData(
                sessionManager.getAuthToken(this)!!,
                sessionManager.getUser_ID(applicationContext)!!.toInt(),
                spinFromMonth,
                spinToMonth,
                sessionManager.getUser_ID(applicationContext)!!.toInt(),
                sessionManager.getUserType(applicationContext)!!.toInt(),
                spinYear
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myIncomePresenter!!.onStop()
    }
}
