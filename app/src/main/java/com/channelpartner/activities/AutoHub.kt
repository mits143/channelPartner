package com.channelpartner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.channelpartner.R
import com.channelpartner.adapter.AutoHubAdapter
import com.channelpartner.model.request.AutoHub.AllServiceDetail
import com.channelpartner.model.request.AutoHub.ServiceDetail
import com.channelpartner.model.response.AutoHubResponse
import com.channelpartner.model.response.ServiceMasterLists
import com.channelpartner.presenter.AutohubPresenter
import com.channelpartner.view.AutoHubView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_auto_hub.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class AutoHub : AppCompatActivity(), AutoHubView.MainView {

    companion object {
        var allServiceDetailList: ArrayList<AllServiceDetail> = arrayListOf()
    }

    var autohubPresenter: AutohubPresenter? = null
    var autoHubAdapterMain: AutoHubAdapter? = null
    var autoHubAdapterOther: AutoHubAdapter? = null
    var autoHubAdapterinformative: AutoHubAdapter? = null
    var serviceMainList: ArrayList<ServiceMasterLists>? = arrayListOf()
    var serviceOtherList: ArrayList<ServiceMasterLists>? = arrayListOf()
    var serviceInformativeList: ArrayList<ServiceMasterLists>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_hub)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.auto_hub)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        autohubPresenter = AutohubPresenter(this, this)
        autohubPresenter!!.loadData(
            sessionManager.getAuthToken(this)!!,
            sessionManager.getUserType(this)!!
        )

        val layoutManager = GridLayoutManager(this, 3)
        rvMainServices!!.setLayoutManager(layoutManager)
        autoHubAdapterMain =
            AutoHubAdapter(this, serviceMainList!!, { serviceMainList -> onClick(serviceMainList) })

        rvMainServices!!.setAdapter(autoHubAdapterMain)

        val layoutManager1 = GridLayoutManager(this, 3)
        rvOtherServices!!.setLayoutManager(layoutManager1)
        autoHubAdapterOther = AutoHubAdapter(
            this,
            serviceOtherList!!,
            { serviceMainList -> onClick(serviceMainList) })

        rvOtherServices!!.setAdapter(autoHubAdapterOther)

        val layoutManager2 = GridLayoutManager(this, 3)
        rvInformativeServices!!.setLayoutManager(layoutManager2)
        autoHubAdapterinformative = AutoHubAdapter(
            this,
            serviceInformativeList!!,
            { serviceMainList -> onClick(serviceMainList) })

        rvInformativeServices!!.setAdapter(autoHubAdapterinformative)

        btnNext.setOnClickListener {
            submit()
        }

    }

    fun submit() {
        if (allServiceDetailList.isEmpty()) {
            Toast.makeText(this, "Select services", Toast.LENGTH_LONG).show()
            return
        }
        startActivity(Intent(this, Detail::class.java))
        finish()
    }

    fun onClick(data: ServiceMasterLists) {
        when {
            TextUtils.equals(data.service_master_id, "1") -> startActivity(
                Intent(this, ServiceDetails::class.java).putExtra(
                    "type",
                    data.service_master_id
                )
            )
            TextUtils.equals(data.service_master_id, "2") -> startActivity(
                Intent(this, ServiceDetails::class.java).putExtra(
                    "type",
                    data.service_master_id
                )
            )
            TextUtils.equals(data.service_master_id, "3") -> startActivity(
                Intent(this, ServiceDetails::class.java).putExtra(
                    "type",
                    data.service_master_id
                )
            )
            else -> startActivity(
                Intent(this, OtherServiceDetail::class.java).putExtra(
                    "type",
                    data.service_master_id
                )
            )
        }
    }

    override fun showProgressbar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        progressBar.visibility = View.GONE
    }

    override fun onSuccess(responseModel: Response<AutoHubResponse>) {
        if (responseModel.body() != null) {
            for (i in responseModel.body()!!.ServiceMasterLists.indices) {
                if (TextUtils.equals(
                        responseModel.body()!!.ServiceMasterLists.get(i).service_type,
                        "0"
                    )
                ) {
                    serviceMainList!!.add(responseModel.body()!!.ServiceMasterLists.get(i))
                    autoHubAdapterMain!!.notifyDataSetChanged()
                }
                if (TextUtils.equals(
                        responseModel.body()!!.ServiceMasterLists.get(i).service_type,
                        "1"
                    )
                ) {
                    serviceOtherList!!.add(responseModel.body()!!.ServiceMasterLists.get(i))
                    autoHubAdapterOther!!.notifyDataSetChanged()
                }
                if (TextUtils.equals(
                        responseModel.body()!!.ServiceMasterLists.get(i).service_type,
                        "2"
                    )
                ) {
                    serviceInformativeList!!.add(responseModel.body()!!.ServiceMasterLists.get(i))
                    autoHubAdapterinformative!!.notifyDataSetChanged()
                }
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
        autohubPresenter!!.onStop()
    }

}
