package com.channelpartner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.channelpartner.R
import com.channelpartner.model.response.NetworkServiceCountResponse
import com.channelpartner.model.response.PromotionDetailResponse
import com.channelpartner.presenter.StatusPresenter
import com.channelpartner.view.StatusView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_status.*
import kotlinx.android.synthetic.main.activity_status.cvDirect
import kotlinx.android.synthetic.main.activity_status.cvInDirect
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class Status : AppCompatActivity(), StatusView.MainView {

    var statusPresenter: StatusPresenter? = null
    var generation_id = 0
    var level_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.my_status)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        statusPresenter = StatusPresenter(this, this)
//        statusPresenter!!.loadPromotionDetails(
//            sessionManager.getAuthToken(this)!!,
//            0,
//            sessionManager.getUser_ID(this)!!,
//            sessionManager.getUser_ID(this)!!.toInt(),
//            sessionManager.getUserType(this)!!.toInt(),
//            "",
//            generation_id,
//            level_id
//        )

        txtViewDetail.setOnClickListener {
            if (llDetails.visibility == View.VISIBLE) {
                txtViewDetail.text = "Show Detail"
                llDetails.visibility = View.GONE
            } else {
                txtViewDetail.text = "Hide Detail"
                llDetails.visibility = View.VISIBLE
            }
        }

        cvDirect.setOnClickListener(View.OnClickListener {
            cvDirect.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtAutoHub.setTextColor(resources.getColor(R.color.colorWhite))
            cvInDirect.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtClassBook.setTextColor(resources.getColor(R.color.colorBlack))
            llAutoHub.visibility = View.VISIBLE
            llClassBook.visibility = View.GONE
        })
        cvInDirect.setOnClickListener(View.OnClickListener {
            cvDirect.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtAutoHub.setTextColor(resources.getColor(R.color.colorBlack))
            cvInDirect.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtClassBook.setTextColor(resources.getColor(R.color.colorWhite))
            llAutoHub.visibility = View.GONE
            llClassBook.visibility = View.VISIBLE
        })
    }

    override fun showProgressbar() {

    }

    override fun hideProgressbar() {
    }

    override fun onSuccess(responseModel: Response<PromotionDetailResponse>) {
        if (responseModel.body() != null) {
            txtCurrentLevel.text =
                "Current Level: " + responseModel.body()!!.CpDetails.currentLevel.toString()
            txtTotal.text = responseModel.body()!!.CpDetails.target.toString()
            txtPending.text = responseModel.body()!!.CpDetails.pending.toString()

            statusPresenter!!.loadCpNetworkServiceCount(
                sessionManager.getAuthToken(this)!!,
                0,
                sessionManager.getUser_ID(this)!!,
                sessionManager.getUser_ID(this)!!.toInt(),
                sessionManager.getUserType(this)!!.toInt(),
                "",
                generation_id,
                level_id
            )
        }
    }

    override fun onSuccess1(responseModel: Response<NetworkServiceCountResponse>) {
        if (responseModel.body() != null) {
            txtTwoWheeler.text = responseModel.body()!!.CpDetails.tw.toString()
            txtFourWheeler.text = responseModel.body()!!.CpDetails.fw.toString()
            txtDoorSteps.text = responseModel.body()!!.CpDetails.door_step.toString()
            txtSpareParts.text = responseModel.body()!!.CpDetails.spare_spart.toString()
            txtTyre.text = responseModel.body()!!.CpDetails.tyre.toString()
            txtBattery.text = responseModel.body()!!.CpDetails.Battery.toString()
            txtDecor.text = responseModel.body()!!.CpDetails.decor.toString()
            txtOilDealer.text = responseModel.body()!!.CpDetails.oil_dealer.toString()
            txtWashing.text = responseModel.body()!!.CpDetails.washing.toString()
            txtTowing.text = responseModel.body()!!.CpDetails.towing.toString()
            txtDenting.text = responseModel.body()!!.CpDetails.denting.toString()
            txtPuncture.text = responseModel.body()!!.CpDetails.puncture.toString()
            txtCNGLPG.text = responseModel.body()!!.CpDetails.cng.toString()
            txtPetrolPump.text = responseModel.body()!!.CpDetails.petrol.toString()
            txtStudent.text = responseModel.body()!!.CpDetails.student.toString()
            txtTeacher.text = responseModel.body()!!.CpDetails.teachers.toString()
            txtClass.text = responseModel.body()!!.CpDetails.classes.toString()
            txtCareerExpert.text = responseModel.body()!!.CpDetails.career_experts.toString()
        }
    }

    override fun onError(errorCode: Int) {
    }

    override fun onError(throwable: Throwable) {
    }
}