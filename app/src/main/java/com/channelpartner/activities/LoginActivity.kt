package com.channelpartner.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.channelpartner.R
import com.channelpartner.model.response.LoginResponse
import com.channelpartner.presenter.LoginPresenter
import com.channelpartner.utils.utility
import com.channelpartner.utils.utility.isValidEmail
import com.channelpartner.view.LoginView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Response

class LoginActivity : AppCompatActivity(), LoginView.MainView {

    var loginpresenter: LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    fun init() {
        loginpresenter = LoginPresenter(this, this)
        txtLogin.setOnClickListener(View.OnClickListener {
            submit()
        })
        txtForgotPwd.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        })
        if (!TextUtils.isEmpty(sessionManager.getUser_ID(this))) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
    }

    fun submit() {
        if (TextUtils.isEmpty(edtEmail.text.toString().trim())) {
            edtEmail.setError(getString(R.string.email_empty))
            edtEmail.requestFocus()
            return
        }
        if (!isValidEmail(edtEmail.text.toString().trim())) {
            edtEmail.setError(getString(R.string.invalid_email))
            edtEmail.requestFocus()
            return
        }
        if (TextUtils.isEmpty(edtPwd.text.toString().trim())) {
            edtPwd.setError(getString(R.string.pwd_empty))
            edtPwd.requestFocus()
            return
        }
        loginpresenter!!.loadData(
            sessionManager.getAuthToken(this)!!,
            edtEmail.text.toString().trim(),
            edtPwd.text.toString().trim(),
            utility.deviceId(this),
            sessionManager.getAuthToken(this)!!
        )
    }

    override fun showProgressbar() {
        txtLogin.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        txtLogin.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun onSuccess(responseModel: Response<LoginResponse>) {
        if (responseModel.body() != null) {
            sessionManager.setUser_ID(this, responseModel.body()!!.data.userId)
            sessionManager.setUserType(this, responseModel.body()!!.data.usertype)
            sessionManager.setEmail(this, responseModel.body()!!.data.email)
            sessionManager.setName(
                this,
                responseModel.body()!!.data.firstname + " " + responseModel.body()!!.data.lastname
            )
            sessionManager.setUnique_No(this, responseModel.body()!!.data.uniqueNo)
            sessionManager.setis_first_atempt(this, responseModel.body()!!.data.is_first_atempt)
            sessionManager.setMobile_No(this, responseModel.body()!!.data.mobile_no)
            sessionManager.setPofile_photo(this, responseModel.body()!!.data.profile_photo)
            sessionManager.setToken(this, responseModel.body()!!.data.token)
            sessionManager.setAuthToken(this, responseModel.body()!!.data.authorizeTokenKey)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onError(errorCode: Int) {
        when (errorCode) {
            401 -> {
                edtEmail.error = getString(R.string.invalid_username_password)
                edtEmail.requestFocus()
            }
            500 -> {
                Toast.makeText(this, getString(R.string.internal_server_error), Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        loginpresenter!!.onStop()
    }
}
