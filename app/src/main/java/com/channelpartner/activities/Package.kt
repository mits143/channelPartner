package com.channelpartner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.model.request.AutoHub.PackageDetail
import com.channelpartner.model.request.AutoHub.ServiceDetail
import kotlinx.android.synthetic.main.activity_package.*

class Package : AppCompatActivity() {

    companion object {
        var packageDetailList: ArrayList<PackageDetail> = arrayListOf()
    }

    var service_ID = ""
    var chkTwo = false
    var chkFour = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)
        init()
    }

    fun init() {

        if (intent.extras!! != null) {
            service_ID = intent.extras!!.get("type").toString()
            if (TextUtils.equals(service_ID, "3")) {
                chkTwo = intent.extras!!.getBoolean("chkTwo")
                chkFour = intent.extras!!.getBoolean("chkFour")
            }
        }

        if (TextUtils.equals(service_ID, "1")) {
            llTwoWheeler.visibility = View.VISIBLE
            llFourWheeler.visibility = View.GONE
        }

        if (TextUtils.equals(service_ID, "2")) {
            llTwoWheeler.visibility = View.VISIBLE
            llFourWheeler.visibility = View.GONE
        }

        if (TextUtils.equals(service_ID, "3")) {
            if (chkTwo) {
                llTwoWheeler.visibility = View.VISIBLE
            } else {
                llTwoWheeler.visibility = View.GONE
            }
            if (chkFour) {
                llFourWheeler.visibility = View.VISIBLE
            } else {
                llFourWheeler.visibility = View.GONE
            }
        }

        rbSilver.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbGold.isChecked = false
                rbPlatium.isChecked = false
            }
        }
        rbGold.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbSilver.isChecked = false
                rbPlatium.isChecked = false
            }
        }
        rbPlatium.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbSilver.isChecked = false
                rbGold.isChecked = false
            }
        }

        rbSilver1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbGold1.isChecked = false
                rbPlatium1.isChecked = false
            }
        }
        rbGold1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbSilver1.isChecked = false
                rbPlatium1.isChecked = false
            }
        }
        rbPlatium1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbSilver1.isChecked = false
                rbGold1.isChecked = false
            }
        }
        btnDone.setOnClickListener {
            submit()
        }
    }

    fun submit() {
        if (!TextUtils.equals(service_ID, "3")) {
            if (!rbSilver.isChecked && !rbGold.isChecked && !rbPlatium.isChecked) {
                Toast.makeText(this, "Select any one package", Toast.LENGTH_LONG).show()
                return
            }

            if (rbSilver.isChecked) {
                var packageDetail = PackageDetail(
                    txtSilverAmount.text.toString().trim(),
                    rbSilver.text.toString().trim(),
                    service_ID,
                    0
                )
                packageDetailList.add(packageDetail!!)
            }

            if (rbGold.isChecked) {
                var packageDetail = PackageDetail(
                    txtGoldAmount.text.toString().trim(),
                    rbGold.text.toString().trim(),
                    service_ID,
                    0
                )
                packageDetailList.add(packageDetail!!)
            }

            if (rbPlatium.isChecked) {
                var packageDetail = PackageDetail(
                    txtPlatiumAmount.text.toString().trim(),
                    rbPlatium.text.toString().trim(),
                    service_ID,
                    0
                )
                packageDetailList.add(packageDetail!!)
            }
            finish()
        } else {
            if (chkTwo) {
                if (!rbSilver.isChecked && !rbGold.isChecked && !rbPlatium.isChecked) {
                    Toast.makeText(
                        this,
                        "Select any one package from two wheeler",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
            }

            if (chkFour) {
                if (!rbSilver1.isChecked && !rbGold1.isChecked && !rbPlatium1.isChecked) {
                    Toast.makeText(
                        this,
                        "Select any one package from four wheeler",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
            }

            if (rbSilver.isChecked) {
                var packageDetail = PackageDetail(
                    txtSilverAmount.text.toString().trim(),
                    rbSilver.text.toString().trim(),
                    service_ID,
                    1
                )
                packageDetailList.add(packageDetail!!)
            }

            if (rbGold.isChecked) {
                var packageDetail = PackageDetail(
                    txtGoldAmount.text.toString().trim(),
                    rbGold.text.toString().trim(),
                    service_ID,
                    1
                )
                packageDetailList.add(packageDetail!!)
            }

            if (rbPlatium.isChecked) {
                var packageDetail = PackageDetail(
                    txtPlatiumAmount.text.toString().trim(),
                    rbPlatium.text.toString().trim(),
                    service_ID,
                    1
                )
                packageDetailList.add(packageDetail!!)
            }

            if (rbSilver1.isChecked) {
                var packageDetail = PackageDetail(
                    txtSilverAmount1.text.toString().trim(),
                    rbSilver1.text.toString().trim(),
                    service_ID,
                    2
                )
                packageDetailList.add(packageDetail!!)
            }
            if (rbGold1.isChecked) {
                var packageDetail = PackageDetail(
                    txtGoldAmount1.text.toString().trim(),
                    rbGold1.text.toString().trim(),
                    service_ID,
                    2
                )
                packageDetailList.add(packageDetail!!)
            }
            if (rbPlatium1.isChecked) {
                var packageDetail = PackageDetail(
                    txtPlatiumAmount1.text.toString().trim(),
                    rbPlatium1.text.toString().trim(),
                    service_ID,
                    2
                )
                packageDetailList.add(packageDetail!!)
            }
            finish()
        }
    }
}