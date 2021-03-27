package com.channelpartner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.channelpartner.R
import com.channelpartner.adapter.QueryAdapter
import com.channelpartner.adapter.StateAdapter
import com.channelpartner.model.response.AllPincode
import com.channelpartner.model.response.AllState
import com.channelpartner.model.response.Allcat
import com.channelpartner.model.response.ContactUsResponse
import com.channelpartner.presenter.ContactPresenter
import com.channelpartner.view.ContactView
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.activity_my_income.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class ContactUs : AppCompatActivity(), ContactView.MainView {

    var presenter: ContactPresenter? = null
    var catList: ArrayList<Allcat>? = arrayListOf()

    var category_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.contact_us)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        presenter = ContactPresenter(this, this)
        presenter!!.loadData(
            sessionManager.getAuthToken(this)!!)

        spinQuery?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category_id = catList!!.get(position).category_id
            }

        }
        rlSubmit.setOnClickListener {
            submit()
        }
    }

    fun submit() {
        if (TextUtils.equals(category_id, "0")) {
            Toast.makeText(this, "Select query type", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(txtDesc.text.toString().trim())) {
            txtDesc.setError("Enter description")
            txtDesc.requestFocus()
            return
        }

        presenter!!.submitData(
            sessionManager.getAuthToken(this)!!,
            sessionManager.getUser_ID(this)!!,
            sessionManager.getUserType(this)!!,
            txtDesc.text.toString().trim(),
            category_id
        )
    }

    override fun showProgressbar(type: Int) {
        if (type == 2) {
            rlSubmit.isClickable = false
            txtSubmit.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressbar(type: Int) {
        if (type == 2) {
            rlSubmit.isClickable = true
            txtSubmit.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }

    }

    override fun onSuccess(responseModel: Response<ContactUsResponse>) {
        if (responseModel.body() != null) {
            txtEmail.text = responseModel.body()!!.email
            txtPhone.text = responseModel.body()!!.phone
            txtAddress.text = responseModel.body()!!.address
            txtSite.text = responseModel.body()!!.website

            catList!!.clear()
            catList!!.addAll(responseModel.body()!!.Allcat)
            val adapter = QueryAdapter(this, catList!!)
            spinQuery.setAdapter(adapter)
        }
    }

    override fun onSuccess1(responseModel: Response<JsonObject>) {
        if (responseModel.body() != null) {

            Toast.makeText(
                this!!,
                responseModel.body()!!.get("message").asString,
                Toast.LENGTH_LONG
            ).show()

            txtDesc.setText("")
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
}
