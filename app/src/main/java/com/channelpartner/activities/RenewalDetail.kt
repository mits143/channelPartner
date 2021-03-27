package com.channelpartner.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.channelpartner.R
import com.channelpartner.adapter.RenewelDetailAdapter
import com.channelpartner.model.response.NewService
import com.channelpartner.model.response.RenewalDetailResponse
import com.channelpartner.presenter.RenewalDetailPresenter
import com.channelpartner.view.RenewalDetailView
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_renewal_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response


class RenewalDetail : AppCompatActivity(), RenewalDetailView.MainView {

    var renewalDetailPresenter: RenewalDetailPresenter? = null
    var renewelDetailAdapter: RenewelDetailAdapter? = null
    var serviceList: ArrayList<NewService>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renewal_detail)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.renewal_detail)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        renewalDetailPresenter = RenewalDetailPresenter(this, this)
        renewalDetailPresenter!!.loadData(
            sessionManager.getAuthToken(this)!!,
            sessionManager.getUser_ID(this)!!.toInt(),
            sessionManager.getUserType(this)!!.toInt(),
            intent.extras!!.getInt("id")
        )

        if (intent.extras!!.getInt("type") != 3) {
            llApproveReject.visibility = View.VISIBLE
        } else {
            llApproveReject.visibility = View.GONE
        }

        rlApprove.setOnClickListener {
            renewalDetailPresenter!!.loadDataApprove(
                sessionManager.getAuthToken(this)!!,
                sessionManager.getUser_ID(this)!!.toInt(),
                sessionManager.getUserType(this)!!.toInt(),
                intent.extras!!.getInt("id")
            )
        }
        rlReject.setOnClickListener {
            showAlert()
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(layoutManager)
        renewelDetailAdapter =
            RenewelDetailAdapter(this, serviceList!!, { serviceList -> onClick(serviceList) })
        recyclerView!!.setAdapter(renewelDetailAdapter)
    }

    fun onClick(data: NewService) {

    }

    override fun showProgressbar(int: Int) {
        if (int == 2) {
            rlApprove.isClickable = false
            rlReject.isClickable = false
            txtApprove.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
        if (int == 3) {
            rlApprove.isClickable = false
            rlReject.isClickable = false
            txtReject.visibility = View.GONE
            progressBar1.visibility = View.VISIBLE
        }
    }

    override fun hideProgressbar(int: Int) {
        if (int == 2) {
            rlApprove.isClickable = true
            rlReject.isClickable = true
            txtApprove.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
        if (int == 3) {
            rlApprove.isClickable = true
            rlReject.isClickable = true
            txtReject.visibility = View.VISIBLE
            progressBar1.visibility = View.GONE
        }
    }

    override fun onSuccess(responseModel: Response<RenewalDetailResponse>) {
        if (responseModel.body() != null) {
            txtGarageName.text = responseModel.body()!!.cp.garage_name.toString()
            txtGarageOwnerName.text = responseModel.body()!!.cp.owner_name.toString()
            txtAddress.text = responseModel.body()!!.cp.garage_address.toString()
            txtPhone.text = responseModel.body()!!.cp.garage_mobile_no.toString()
            txtRenewalDate.text = responseModel.body()!!.cp.renewal_date.toString()
            txtStatus.text = responseModel.body()!!.cp.status.toString()

            serviceList!!.clear()
            serviceList!!.addAll(responseModel.body()!!.cp.new_services)
            renewelDetailAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onSuccess1(responseModel: Response<JsonObject>) {
        if (responseModel.body() != null) {

            Toast.makeText(
                this!!,
                responseModel.body()!!.get("message").asString,
                Toast.LENGTH_LONG
            ).show()

            finish()
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

    fun showAlert() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Do you really want to reject ?")
        builder.setTitle("Reject !")
        builder.setCancelable(false)
        builder
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, which -> // When the user click yes button
                    // then app will close

                    renewalDetailPresenter!!.loadDataReject(
                        sessionManager.getAuthToken(this)!!,
                        sessionManager.getUser_ID(this)!!.toInt(),
                        sessionManager.getUserType(this)!!.toInt(),
                        intent.extras!!.getInt("id")
                    )
                })
        builder
            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, which -> // If user click no
                    // then dialog box is canceled.
                    dialog.cancel()
                })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}