package com.channelpartner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.activities.AutoHub.Companion.allServiceDetailList
import com.channelpartner.activities.GarageRegistration.Companion.file0
import com.channelpartner.activities.GarageRegistration.Companion.file1
import com.channelpartner.activities.GarageRegistration.Companion.file2
import com.channelpartner.activities.GarageRegistration.Companion.file3
import com.channelpartner.activities.GarageRegistration.Companion.file4
import com.channelpartner.activities.GarageRegistration.Companion.file5
import com.channelpartner.activities.GarageRegistration.Companion.file6
import com.channelpartner.activities.GarageRegistration.Companion.file7
import com.channelpartner.activities.GarageRegistration.Companion.files
import com.channelpartner.activities.GarageRegistration.Companion.garageInformation
import com.channelpartner.activities.GarageRegistration.Companion.paymentModeList
import com.channelpartner.activities.GarageRegistration.Companion.workingHourList
import com.channelpartner.activities.Package.Companion.packageDetailList
import com.channelpartner.presenter.DetailPresenter
import com.channelpartner.utils.utility
import com.channelpartner.view.DetailView
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_detail2.*
import kotlinx.android.synthetic.main.activity_detail2.progressBar
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class Detail : AppCompatActivity(), DetailView.MainView {

    var detailPresenter: DetailPresenter? = null
    var total = 0L

    var paid_amount = 0L;
    var id = 0;
    var parts: ArrayList<MultipartBody.Part> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail2)
        init()
    }

    fun init() {
        detailPresenter = DetailPresenter(this, this)
        txtGarageName.text = garageInformation!!.garage_name
        txtPhone.text = garageInformation!!.mobile_number
        txtEmail.text = garageInformation!!.contact_email_id
        txtholderName.text = garageInformation!!.account_holder_name
        txtBankName.text = garageInformation!!.bank_name
        txtAcNo.text = garageInformation!!.account_number
        txtIfsc.text = garageInformation!!.ifsc_code
        txtBranchName.text = garageInformation!!.branch_name

        for (i in allServiceDetailList.indices) {
            for (j in allServiceDetailList[i].service_details.indices) {

                if (TextUtils.equals(allServiceDetailList[i].service_details[j].service_id, "1")) {

                    llTwoWheeler.visibility = View.VISIBLE
                    val sb = StringBuffer()
                    for (k in allServiceDetailList[i].service_details[j].vehicle_types.indices) {
                        sb.append(allServiceDetailList[i].service_details[j].vehicle_types[k].type)
                        sb.append(", ")
                    }
                    txtvehicletype.text = sb.toString()
                    txtLabourcharge.text = allServiceDetailList[i].service_details[j].labour_cost

                    if (TextUtils.equals(
                            allServiceDetailList[i].service_details[j].pick_drop_flag,
                            "1"
                        )
                    )
                        txtPickDrop.text = "Yes"
                    else
                        txtPickDrop.text = "no"
                }

                if (TextUtils.equals(allServiceDetailList[i].service_details[j].service_id, "2")) {

                    llFourWheeler.visibility = View.VISIBLE
                    val sb1 = StringBuffer()
                    for (k in allServiceDetailList[i].service_details[j].vehicle_types.indices) {
                        sb1.append(allServiceDetailList[i].service_details[j].vehicle_types[k].type)
                        sb1.append(", ")
                    }
                    txtvehicletype1.text = sb1.toString()
                    txtLabourcharge1.text = allServiceDetailList[i].service_details[j].labour_cost

                    if (TextUtils.equals(
                            allServiceDetailList[i].service_details[j].pick_drop_flag,
                            "1"
                        )
                    )
                        txtPickDrop1.text = "Yes"
                    else
                        txtPickDrop1.text = "no"
                }

                if (TextUtils.equals(allServiceDetailList[i].service_details[j].service_id, "3")) {
                    llDoorStep.visibility = View.VISIBLE
                }
            }
        }

        for (i in packageDetailList.indices) {
            if (TextUtils.equals(packageDetailList[i].service_id, "1")) {
                txtPackage.text = packageDetailList[i].package_name
                total = (total + packageDetailList[i].amount.toLong())
            }
            if (TextUtils.equals(packageDetailList[i].service_id, "2")) {
                txtPackage1.text = packageDetailList[i].package_name
                total = (total + packageDetailList[i].amount.toLong())
            }
            if (TextUtils.equals(packageDetailList[i].service_id, "3")) {
                if (packageDetailList[i].service_type == 1) {
                    txtPackage2.text = packageDetailList[i].package_name
                    total = (total + packageDetailList[i].amount.toLong())
                }
                if (packageDetailList[i].service_type == 2) {
                    txtPackage3.text = packageDetailList[i].package_name
                    total = (total + packageDetailList[i].amount.toLong())
                }
            }
            if (!TextUtils.equals(packageDetailList[i].service_id, "1") &&
                !TextUtils.equals(packageDetailList[i].service_id, "2") &&
                !TextUtils.equals(packageDetailList[i].service_id, "3")
            ) {
                total = (total + packageDetailList[i].amount.toLong())
            }
        }
        paid_amount = total
        txtAmount.text = paid_amount.toString()
        val gst = (total * 18) / 100
        total += gst
        txtgst.text = gst.toString()

        val ihc = (total * 2) / 100
        total += ihc
        txtInternet.text = ihc.toString()

        txtTotal.text = total.toString()

        rlPay.setOnClickListener {
            detailPresenter!!.loadData(
                sessionManager.getAuthToken(this)!!,
                allServiceDetailList,
                "CP10001",
                sessionManager.getUser_ID(this)!!,
                garageInformation!!,
                txtgst.text.toString().trim(),
                txtInternet.text.toString().trim(),
                packageDetailList,
                paymentModeList,
                total.toString(),
                workingHourList
            )
        }
    }

    override fun showProgressbar() {
        rlPay.isClickable = false
        txtPay.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        rlPay.isClickable = true
        txtPay.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun onSuccess(type: Int, responseModel: Response<JsonObject>) {
        if (responseModel.body() != null) {
            if (type == 1) {
                id = responseModel.body()!!.get("spid").asInt

                detailPresenter!!.commission(
                    sessionManager.getAuthToken(this)!!,
                    garageInformation!!.date_of_establish,
                    garageInformation!!.owner_first_name,
                    paid_amount.toString(),
                    sessionManager.getUser_ID(this)!!.toInt(),
                    "T",
                    id,
                    3
                )
            }

            if (type == 2) {
                val user_Id: RequestBody =
                    RequestBody.create(
                        MultipartBody.FORM,
                        id.toString()
                    )
                val user_type: RequestBody =
                    RequestBody.create(MultipartBody.FORM, "3")

                var body0: MultipartBody.Part? = null
                if (file0 != null) {
                    val reqFile0: RequestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file0!!)
                    body0 = MultipartBody.Part.createFormData("profile", file0!!.name, reqFile0)
                } else {
                    val attachmentEmpty =
                        RequestBody.create(MediaType.parse("text/plain"), "")
                    body0 = MultipartBody.Part.createFormData("profile", "", attachmentEmpty);
                }

                var body1: MultipartBody.Part? = null
                if (file1 != null) {
                    val reqFile1: RequestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file1!!)
                    body1 = MultipartBody.Part.createFormData("1", file1!!.name, reqFile1)
                } else {
                    val attachmentEmpty =
                        RequestBody.create(MediaType.parse("text/plain"), "")
                    body1 = MultipartBody.Part.createFormData("1", "", attachmentEmpty);
                }

                var body2: MultipartBody.Part? = null
                if (file2 != null) {
                    val reqFile2: RequestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file2!!)
                    body2 = MultipartBody.Part.createFormData("2", file2!!.name, reqFile2)
                } else {
                    val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
                    body2 = MultipartBody.Part.createFormData("2", "", attachmentEmpty);
                }

                var body3: MultipartBody.Part? = null
                if (file3 != null) {
                    val reqFile3: RequestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file3!!)
                    body3 = MultipartBody.Part.createFormData("3", file3!!.name, reqFile3)
                } else {
                    val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
                    body3 = MultipartBody.Part.createFormData("3", "", attachmentEmpty);
                }

                var body4: MultipartBody.Part? = null
                if (file4 != null) {
                    val reqFile4: RequestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file4!!)
                    body4 = MultipartBody.Part.createFormData("4", file4!!.name, reqFile4)
                } else {
                    val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
                    body4 = MultipartBody.Part.createFormData("4", "", attachmentEmpty);
                }

                var body5: MultipartBody.Part? = null
                if (file5 != null) {
                    val reqFile5: RequestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file5!!)
                    body5 = MultipartBody.Part.createFormData("5", file5!!.name, reqFile5)
                } else {
                    val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
                    body5 = MultipartBody.Part.createFormData("5", "", attachmentEmpty);
                }

                var body6: MultipartBody.Part? = null
                if (file6 != null) {
                    val reqFile6: RequestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file6!!)
                    body6 = MultipartBody.Part.createFormData("6", file6!!.name, reqFile6)
                } else {
                    val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
                    body6 = MultipartBody.Part.createFormData("6", "", attachmentEmpty);
                }

                var body7: MultipartBody.Part? = null
                if (file7 != null) {
                    val reqFile7: RequestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file7!!)
                    body7 = MultipartBody.Part.createFormData("7", file7!!.name, reqFile7)
                } else {
                    val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
                    body7 = MultipartBody.Part.createFormData("7", "", attachmentEmpty);
                }

                if (files != null) {
                    // create part for file (photo, video, ...)
                    for (i in files.indices) {
                        parts.add(utility.prepareFilePart(this, "gallary", files.get(i))!!)
                    }
                }
                detailPresenter!!.SPImageUploads(
                    sessionManager.getAuthToken(this)!!,
                    body0,
                    user_Id,
                    user_type,
                    body1,
                    body2,
                    body3,
                    body4,
                    body5,
                    body6,
                    body7,
                    parts
                )
            }

            if (type == 3) {
                Toast.makeText(
                    this!!,
                    "Successfully register",
                    Toast.LENGTH_LONG
                ).show()
                finish()
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
}