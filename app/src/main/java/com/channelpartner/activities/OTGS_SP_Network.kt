package com.channelpartner.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.channelpartner.R
import com.channelpartner.adapter.OTGSChannelPartnerAdapter
import com.channelpartner.adapter.SpinLevelAdapter
import com.channelpartner.adapter.SpinServicesAdapter
import com.channelpartner.model.response.AutoHubResponse
import com.channelpartner.model.response.CpDetail
import com.channelpartner.model.response.OTGSChannelPartnerResponse
import com.channelpartner.model.response.ServiceMasterLists
import com.channelpartner.presenter.OTGSSPNetworkPresenter
import com.channelpartner.view.OTGSSPNetworkView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_otgs_channel_partner.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response


class OTGS_SP_Network : AppCompatActivity(), OTGSSPNetworkView.MainView,
    SearchView.OnQueryTextListener {

    var otgsspnetworkpresenter: OTGSSPNetworkPresenter? = null
    var otgschannelpartnerDirectadapter: OTGSChannelPartnerAdapter? = null
    var otgschannelpartnerIndirectadapter: OTGSChannelPartnerAdapter? = null
    var cpdetailDirectList: ArrayList<CpDetail>? = arrayListOf()
    var cpdetailInDirectList: ArrayList<CpDetail>? = arrayListOf()

    var serviceList: ArrayList<ServiceMasterLists>? = arrayListOf()
    var page: Int? = 1
    var generation_id = 0
    var service_master_id = 0
    var level_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otgs_channel_partner)
        setSupportActionBar(toolBar)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        txtTitle.text = getString(R.string.sp_partner)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        otgsspnetworkpresenter = OTGSSPNetworkPresenter(this, this)
        searchView.setOnQueryTextListener(this)

        llFilter.visibility = View.VISIBLE
        flLevel.visibility = View.GONE
        llDirect_Indirect.visibility = View.VISIBLE
        cvDirect.setOnClickListener(View.OnClickListener {
            cvDirect.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtDirect.setTextColor(resources.getColor(R.color.colorWhite))
            cvInDirect.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtInDirect.setTextColor(resources.getColor(R.color.colorBlack))
            rlDirect.visibility = View.VISIBLE
            rlInDirect.visibility = View.GONE
        })
        cvInDirect.setOnClickListener(View.OnClickListener {
            cvDirect.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtDirect.setTextColor(resources.getColor(R.color.colorBlack))
            cvInDirect.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtInDirect.setTextColor(resources.getColor(R.color.colorWhite))
            rlDirect.visibility = View.GONE
            rlInDirect.visibility = View.VISIBLE
        })
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(layoutManager)
        otgschannelpartnerDirectadapter = OTGSChannelPartnerAdapter(this, cpdetailDirectList!!,
            { cpdetailList -> onClick(cpdetailList) })

        recyclerView!!.setAdapter(otgschannelpartnerDirectadapter)

        val layoutManager1 = LinearLayoutManager(this)
        recyclerView1!!.setLayoutManager(layoutManager1)
        otgschannelpartnerIndirectadapter = OTGSChannelPartnerAdapter(this, cpdetailInDirectList!!,
            { cpdetailList -> onClick(cpdetailList) })

        recyclerView1!!.setAdapter(otgschannelpartnerIndirectadapter)


        otgsspnetworkpresenter!!.loadData(
            sessionManager.getAuthToken(this)!!,
            sessionManager.getUserType(this)!!
        )

//        otgsspnetworkpresenter!!.loadDirectData(
//            sessionManager.getAuthToken(this)!!,
//            page!!,
//            sessionManager.getUser_ID(this)!!,
//            sessionManager.getUser_ID(this)!!.toInt(),
//            sessionManager.getUserType(this)!!.toInt(),
//            "",
//            generation_id,
//            level_id
//        )
//        otgsspnetworkpresenter!!.loadIndirectData(
//            sessionManager.getAuthToken(this)!!,
//            page!!,
//            sessionManager.getUser_ID(this)!!,
//            sessionManager.getUser_ID(this)!!.toInt(),
//            sessionManager.getUserType(this)!!.toInt(),
//            "",
//            generation_id,
//            level_id
//        )

        swipeRefresh.setOnRefreshListener {
            searchView.setQuery("", false);
            searchView.clearFocus();
            otgsspnetworkpresenter!!.loadDirectData(
                sessionManager.getAuthToken(this)!!,
                page!!,
                sessionManager.getUser_ID(this)!!,
                sessionManager.getUser_ID(this)!!.toInt(),
                sessionManager.getUserType(this)!!.toInt(),
                "",
                service_master_id,
                level_id
            )
            otgsspnetworkpresenter!!.loadIndirectData(
                sessionManager.getAuthToken(this)!!,
                page!!,
                sessionManager.getUser_ID(this)!!,
                sessionManager.getUser_ID(this)!!.toInt(),
                sessionManager.getUserType(this)!!.toInt(),
                "",
                service_master_id,
                level_id
            )
        }

        spinGeneration?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                service_master_id = serviceList!!.get(position).service_master_id.toInt()

                otgsspnetworkpresenter!!.loadDirectData(
                    sessionManager.getAuthToken(this@OTGS_SP_Network)!!,
                    page!!,
                    sessionManager.getUser_ID(this@OTGS_SP_Network)!!,
                    sessionManager.getUser_ID(this@OTGS_SP_Network)!!.toInt(),
                    sessionManager.getUserType(this@OTGS_SP_Network)!!.toInt(),
                    "",
                    service_master_id,
                    level_id
                )
                otgsspnetworkpresenter!!.loadIndirectData(
                    sessionManager.getAuthToken(this@OTGS_SP_Network)!!,
                    page!!,
                    sessionManager.getUser_ID(this@OTGS_SP_Network)!!,
                    sessionManager.getUser_ID(this@OTGS_SP_Network)!!.toInt(),
                    sessionManager.getUserType(this@OTGS_SP_Network)!!.toInt(),
                    "",
                    service_master_id,
                    level_id
                )
            }

        }

    }

    fun onClick(cpDetail: CpDetail) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("cp_id", cpDetail.id)
                .putExtra("class_name", "OTGS_SP_Network")
                .putExtra("name", cpDetail.fullName)
        )
    }

    override fun showProgressbar() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipeRefresh.isRefreshing = false
    }

    override fun onSuccessServices(responseModel: Response<AutoHubResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.ServiceMasterLists.size > 0) {
            serviceList!!.clear()
            serviceList!!.addAll(responseModel.body()!!.ServiceMasterLists)
            val adapter = SpinServicesAdapter(this, serviceList!!)
            spinGeneration.adapter = adapter
        }
    }

    override fun onSuccess(value: Int, responseModel: Response<OTGSChannelPartnerResponse>) {
        if (value == 1) {
            if (responseModel.body() != null && responseModel.body()!!.size > 0) {
                llDirectNoData.visibility = View.GONE
                cpdetailDirectList!!.clear()
                cpdetailDirectList!!.addAll(responseModel.body()!!)
//                txtDirect.text = "Direct: " + responseModel.body()!!.total_count
            } else {
                cpdetailDirectList!!.clear()
                llDirectNoData.visibility = View.VISIBLE
            }
        } else {
            if (responseModel.body() != null && responseModel.body()!!.size > 0) {
                llIndirectNoData.visibility = View.GONE
                cpdetailInDirectList!!.clear()
                cpdetailInDirectList!!.addAll(responseModel.body()!!)
//                txtInDirect.text = "InDirect: " + responseModel.body()!!.total_count
            } else {
                cpdetailInDirectList!!.clear()
                llIndirectNoData.visibility = View.VISIBLE
            }
        }
        otgschannelpartnerDirectadapter!!.notifyDataSetChanged()
        otgschannelpartnerIndirectadapter!!.notifyDataSetChanged()
    }

    override fun onError(value: Int, errorCode: Int) {
        if (value == 1) {
            cpdetailDirectList!!.clear()
            otgschannelpartnerDirectadapter!!.notifyDataSetChanged()
            llDirectNoData.visibility = View.VISIBLE
        } else {
            cpdetailInDirectList!!.clear()
            otgschannelpartnerIndirectadapter!!.notifyDataSetChanged()
            llIndirectNoData.visibility = View.VISIBLE
        }
        if (errorCode == 500) {
            Toast.makeText(this, getString(R.string.internal_server_error), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(value: Int, throwable: Throwable) {
        if (value == 1) {
            cpdetailDirectList!!.clear()
            otgschannelpartnerDirectadapter!!.notifyDataSetChanged()
            llDirectNoData.visibility = View.VISIBLE
        } else {
            cpdetailInDirectList!!.clear()
            otgschannelpartnerIndirectadapter!!.notifyDataSetChanged()
            llIndirectNoData.visibility = View.VISIBLE
        }
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        otgsspnetworkpresenter!!.onStop()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        otgsspnetworkpresenter!!.loadDirectData(
            sessionManager.getAuthToken(this)!!,
            page!!,
            sessionManager.getUser_ID(this)!!,
            sessionManager.getUser_ID(this)!!.toInt(),
            sessionManager.getUserType(this)!!.toInt(),
            query!!,
            generation_id,
            level_id
        )
        otgsspnetworkpresenter!!.loadIndirectData(
            sessionManager.getAuthToken(this)!!,
            page!!,
            sessionManager.getUser_ID(this)!!,
            sessionManager.getUser_ID(this)!!.toInt(),
            sessionManager.getUserType(this)!!.toInt(),
            query!!,
            generation_id,
            level_id
        )
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}
