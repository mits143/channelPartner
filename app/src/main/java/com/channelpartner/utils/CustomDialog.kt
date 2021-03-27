package com.channelpartner.utils

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.channelpartner.R
import com.channelpartner.presenter.MainActivityPresnter
import com.channelpartner.view.MainView
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.dialog_mobile.*
import retrofit2.Response

class CustomDialog(var activity: Activity, internal var listner: listener) : Dialog(activity),
    View.OnClickListener, MainView.MainView {
    var txtTitle: TextViewDrawableSize? = null
    var txtSubTitle: EditTextDrawableSize? = null
    var rlSubmit: RelativeLayout? = null
    var txtButton: TextViewDrawableSize? = null
    var progressBar: ProgressBar? = null
    var presenter: MainActivityPresnter? = null
    var otp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_mobile)
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        setCancelable(false)
        presenter = MainActivityPresnter(activity, this)
        txtTitle = findViewById<View>(R.id.txtTitle) as TextViewDrawableSize
        txtTitle!!.setText("Verify Number\n\n" + sessionManager.getMobile_No(activity))
        txtSubTitle = findViewById<View>(R.id.edtOTP) as EditTextDrawableSize
        rlSubmit = findViewById<View>(R.id.rlSubmit) as RelativeLayout
        rlSubmit!!.setOnClickListener(this)
        txtButton = findViewById<View>(R.id.txtButton) as TextViewDrawableSize
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rlSubmit -> {
                if (TextUtils.equals(
                        txtButton!!.text.toString().trim(),
                        "Send OTP"
                    ) || TextUtils.equals(
                        txtButton!!.text.toString().trim(),
                        "Resend OTP"
                    )
                ) {
                    presenter!!.loadData(
                        sessionManager.getAuthToken(context)!!, sessionManager.getMobile_No(activity)!!
                    )
                } else {
                    if (TextUtils.isEmpty(txtSubTitle!!.text.toString().trim())) {
                        txtSubTitle!!.error = "OTP cannot be empty"
                        txtSubTitle!!.requestFocus()
                        return
                    }
                    if (!TextUtils.equals(txtSubTitle!!.text.toString().trim(), otp)) {
                        txtSubTitle!!.error = "Invalid OTP"
                        txtSubTitle!!.requestFocus()
                        return
                    }

                    listner.success()
                }
            }
            else -> {
            }
        }
    }

    override fun showProgressbar() {
        rlSubmit!!.isClickable = false
        txtButton!!.visibility = View.GONE
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        rlSubmit!!.isClickable = true
        txtButton!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.GONE
    }

    override fun onSuccess(responseModel: Response<JsonObject>) {
        if (responseModel.body() != null) {
            txtButton!!.setText("Verify")
            edtOTP!!.visibility = View.VISIBLE
            otp = responseModel.body()!!.get("otp").asString
        }

    }

    override fun onError(errorCode: Int) {
        if (errorCode == 500) {
            Toast.makeText(
                activity,
                activity.getString(R.string.internal_server_error),
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            Toast.makeText(activity, activity.getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
        txtButton!!.setText("Resend OTP")
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(activity, activity.getString(R.string.error), Toast.LENGTH_SHORT).show()
        txtButton!!.text = "Resend OTP"
    }

    interface listener {
        fun success()
    }

}