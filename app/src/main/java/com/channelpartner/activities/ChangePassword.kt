package com.channelpartner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.channelpartner.R
import com.channelpartner.presenter.ChangePwdPresenter
import com.channelpartner.view.ChangePasswordView
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class ChangePassword : AppCompatActivity(), ChangePasswordView.MainView {

    var changePwdPresenter: ChangePwdPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        setSupportActionBar(toolBar)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        txtTitle.text = getString(R.string.change_pwd)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        changePwdPresenter = ChangePwdPresenter(this, this)
        txtSubmit.setOnClickListener(View.OnClickListener {
            submit()
        })
    }

    fun submit() {
        if (TextUtils.isEmpty(edtOldPwd.text.toString().trim())) {
            edtOldPwd.setError("Old password cannot be empty")
            edtOldPwd.requestFocus()
            return
        }
        if (TextUtils.isEmpty(edtNewPwd.text.toString().trim())) {
            edtNewPwd.setError("New password cannot be empty")
            edtNewPwd.requestFocus()
            return
        }
        if (TextUtils.isEmpty(edtConfirmPwd.text.toString().trim())) {
            edtConfirmPwd.setError("Confirm password cannot be empty")
            edtConfirmPwd.requestFocus()
            return
        }
        if (!TextUtils.equals(
                edtNewPwd.text.toString().trim(),
                edtConfirmPwd.text.toString().trim()
            )
        ) {
            edtConfirmPwd.setError("Confirm password mismatch")
            edtConfirmPwd.requestFocus()
            return
        }
        changePwdPresenter!!.loadData(
            sessionManager.getAuthToken(this)!!,
            edtNewPwd.text.toString().trim(),
            edtOldPwd.text.toString().trim()
        )
    }

    override fun showProgressbar() {
        txtSubmit.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        txtSubmit.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun onSuccess(responseModel: Response<JsonObject>) {
        if (responseModel.body() != null) {
//            Toast.makeText(
//                this,
//                responseModel.body()!!.get("message").asString,
//                Toast.LENGTH_SHORT
//            ).show()
//            sessionManager.setChangePassword(this, true)
            sessionManager.clearData(this)
            finish()
        }
    }

    override fun onError(errorCode: Int) {
        if (errorCode == 401) {
            edtOldPwd.setError(getString(R.string.invalid_old_pwd))
            edtOldPwd.requestFocus()
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
        changePwdPresenter!!.onStop()
    }
}
