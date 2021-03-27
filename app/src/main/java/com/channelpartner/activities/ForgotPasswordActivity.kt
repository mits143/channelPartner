package com.channelpartner.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.presenter.ForgotPasswordPresenter
import com.channelpartner.utils.NetWorkConection
import com.channelpartner.view.ForgotPasswordView
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.edtEmail
import kotlinx.android.synthetic.main.activity_forgot_password.progressBar
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordView.MainView {

    var forgotPasswordPresenter: ForgotPasswordPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val attrib = window.attributes
            attrib.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        setContentView(R.layout.activity_forgot_password)
        init()
    }

    fun init() {
        forgotPasswordPresenter = ForgotPasswordPresenter(this, this)

        txtReset.setOnClickListener(View.OnClickListener {
            submit()
        })
    }

    fun submit() {
        if (TextUtils.isEmpty(edtEmail.text.toString().trim())) {
            edtEmail.error = getString(R.string.email_empty)
            edtEmail.requestFocus()
            return
        }
        forgotPasswordPresenter!!.loadData(
            sessionManager.getAuthToken(this)!!,
            edtEmail.text.toString().trim()
        )
    }

    override fun showProgressbar() {
        txtReset.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        txtReset.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun onSuccess(responseModel: Response<JsonObject>) {
        if (responseModel.body() != null) {
            Toast.makeText(this, responseModel.body()!!.get("message").asString, Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onError(errorCode: Int) {
        if (errorCode == 401) {
            edtEmail.error = getString(R.string.invalid_email)
            edtEmail.requestFocus()
        } else if (errorCode == 500) {
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
        forgotPasswordPresenter!!.onStop()
    }
}
