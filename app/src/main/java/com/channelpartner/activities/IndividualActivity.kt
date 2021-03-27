package com.channelpartner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.channelpartner.R
import com.channelpartner.adapter.IncomeAdaterAdapter
import com.channelpartner.adapter.SpinnerMonthAdapter
import com.channelpartner.adapter.SpinnerYearAdapter
import com.channelpartner.adapter.StaticAdapter
import com.channelpartner.model.response.*
import com.channelpartner.presenter.IndividualIncomePresenter
import com.channelpartner.presenter.PackageValuePresenter
import com.channelpartner.view.IndividualInccomeView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_individual.*
import kotlinx.android.synthetic.main.activity_individual.cvAutoHub
import kotlinx.android.synthetic.main.activity_individual.cvClassbook
import kotlinx.android.synthetic.main.activity_individual.llClassBook
import kotlinx.android.synthetic.main.activity_individual.txtAutoHub
import kotlinx.android.synthetic.main.activity_individual.txtClassbook
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class IndividualActivity : AppCompatActivity(), IndividualInccomeView.MainView {

    var presenter: IndividualIncomePresenter? = null
    var spinneryearadapter: SpinnerYearAdapter? = null
    var spinnerFromMonthAdapter: SpinnerMonthAdapter? = null
    var adapter: StaticAdapter? = null
    var yearList: ArrayList<AllYear>? = arrayListOf()
    var monthList: ArrayList<AllMonth>? = arrayListOf()
    var staticList: ArrayList<StaticTable>? = arrayListOf()

    var year = 0
    var month = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual)
        txtTitle.text = getString(R.string.individual)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        init()
    }

    fun init() {
        presenter = IndividualIncomePresenter(this, this)
        presenter!!.loadYearData(
            sessionManager.getAuthToken(this)!!
        )
        cvAutoHub.setOnClickListener(View.OnClickListener {
            cvAutoHub.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtAutoHub.setTextColor(resources.getColor(R.color.colorWhite))
            cvClassbook.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtClassbook.setTextColor(resources.getColor(R.color.colorBlack))
            llAutoHub.visibility = View.VISIBLE
            llClassBook.visibility = View.GONE
        })
        cvClassbook.setOnClickListener(View.OnClickListener {
            cvAutoHub.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtAutoHub.setTextColor(resources.getColor(R.color.colorBlack))
            cvClassbook.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtClassbook.setTextColor(resources.getColor(R.color.colorWhite))
            llAutoHub.visibility = View.GONE
            llClassBook.visibility = View.VISIBLE
        })

        spinYear?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                year = yearList!!.get(position).id.toInt()
            }

        }

        spinMonth?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                month = monthList!!.get(position).id.toInt()

            }
        }

        txtSubmit.setOnClickListener {
            presenter!!.loadData(
                sessionManager.getAuthToken(this)!!,
                month,
                year,
                1
            )
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(layoutManager)
        adapter = StaticAdapter(this, staticList!!,
            { staticList -> onClick(staticList) })
        recyclerView!!.setAdapter(adapter)

    }

    private fun onClick(staticList: StaticTable) {

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
            spinYear.adapter = spinneryearadapter;

            presenter!!.loadMonthData(
                sessionManager.getAuthToken(this)!!
            )
        }
    }

    override fun onSuccessMonth(responseModel: Response<MonthResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.isNotEmpty()) {
            monthList!!.clear()
            monthList!!.addAll(responseModel.body()!!)
            spinnerFromMonthAdapter = SpinnerMonthAdapter(this, monthList!!)
            spinMonth.adapter = spinnerFromMonthAdapter;
        }
    }

    override fun onSuccess(responseModel: Response<IndividualResponse>) {
        if (responseModel.body() != null) {
            llMain.visibility = View.VISIBLE
            txtAutoHubTurnOver.text = responseModel.body()!!.total_turnover
            txtAutoHubIncome.text = responseModel.body()!!.total_income
            txtAutoHubGarage.text = responseModel.body()!!.total_garage_registered.toString()
            txtClassBookTurnOver.text = responseModel.body()!!.total_turnover
            txtClassBookIncome.text = responseModel.body()!!.total_income
            txtClassBookStudent.text = responseModel.body()!!.total_student_registered.toString()
            txtClassBookClass.text = responseModel.body()!!.total_class_registered.toString()
            txtClassBookTeacher.text = responseModel.body()!!.total_teacher_registered.toString()
            txtClassBookExperts.text = responseModel.body()!!.total_ce_registered.toString()

            if (responseModel.body()!!.static_table.isNotEmpty()) {
                staticList!!.clear()
                staticList!!.addAll(responseModel.body()!!.static_table)
                adapter!!.notifyDataSetChanged()
            }
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


    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onStop()
    }
}