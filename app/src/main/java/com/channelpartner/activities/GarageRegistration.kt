package com.channelpartner.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.channelpartner.R
import com.channelpartner.adapter.CityAdapter
import com.channelpartner.adapter.ImagesAdapter
import com.channelpartner.adapter.PincodeAdapter
import com.channelpartner.adapter.StateAdapter
import com.channelpartner.model.request.AutoHub.GarageInformation
import com.channelpartner.model.request.AutoHub.PaymentMode
import com.channelpartner.model.request.AutoHub.WorkingHour
import com.channelpartner.model.response.*
import com.channelpartner.presenter.GarageRegistrationPresenter
import com.channelpartner.utils.FileUtils
import com.channelpartner.utils.MyEditTextDatePicker
import com.channelpartner.utils.utility
import com.channelpartner.utils.utility.OPERATION_CAPTURE_PHOTO
import com.channelpartner.utils.utility.OPERATION_CHOOSE_MULTIPLE_PHOTO
import com.channelpartner.utils.utility.OPERATION_CHOOSE_PHOTO
import com.channelpartner.utils.utility.isValidEmail
import com.channelpartner.utils.utility.isValidIFSC
import com.channelpartner.utils.utility.isValidPan
import com.channelpartner.utils.utility.openGallery
import com.channelpartner.utils.utility.openGallery1
import com.channelpartner.utils.utility.source
import com.channelpartner.view.GarageRegView
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_garage_registration.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response
import java.io.File
import java.net.URISyntaxException
import java.util.*


class GarageRegistration : AppCompatActivity(), GarageRegView.MainView {

    companion object {
        var garageInformation: GarageInformation? = null
        var paymentModeList: ArrayList<PaymentMode> = arrayListOf()
        var workingHourList: ArrayList<WorkingHour> = arrayListOf()

        var file0: File? = null
        var file1: File? = null
        var file2: File? = null
        var file3: File? = null
        var file4: File? = null
        var file5: File? = null
        var file6: File? = null
        var file7: File? = null
        var files: ArrayList<Uri> = arrayListOf()
    }

    var imagesAdapter: ImagesAdapter? = null
    var garageRegistrationPresenter: GarageRegistrationPresenter? = null

    var otp = ""
    var state: Int = 0;
    var city: Int = 0;
    var pincode: Int = 0;

    var stateList: ArrayList<AllState>? = arrayListOf()
    var cityList: ArrayList<AllState>? = arrayListOf()
    var pincodeList: ArrayList<AllState>? = arrayListOf()

    var myedittextdatepicker: MyEditTextDatePicker? = null

    var type: Int = 0;

    val PERMISSION_ID = 100
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garage_registration)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.garage_registration)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {

        garageRegistrationPresenter = GarageRegistrationPresenter(this, this)
        garageRegistrationPresenter!!.loadStates(
            sessionManager.getAuthToken(this)!!
        )

        edtAddedBy.setText(sessionManager.getName(this)!!)
        edtAddedBy.clearFocus()

        myedittextdatepicker = MyEditTextDatePicker(this, R.id.edtDOB)

        autoTextState.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as AllState?
            autoTextState.setText(selectedPoi?.name)
            state = selectedPoi?.id!!.toInt()
            garageRegistrationPresenter!!.loadCities(
                sessionManager.getAuthToken(this)!!, selectedPoi?.id
            )
        }

        autoTextState.setOnTouchListener { v, event ->
            autoTextState.showDropDown()
            false
        }

        autoTextCity.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as AllState?
            autoTextCity.setText(selectedPoi?.name)
            city = selectedPoi?.id!!.toInt()
            garageRegistrationPresenter!!.loadPincodes(
                sessionManager.getAuthToken(this)!!, selectedPoi?.id
            )
        }

        autoTextCity.setOnTouchListener { v, event ->
            autoTextCity.showDropDown()
            false
        }

        autoTextPincode.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as AllState?
            pincode = selectedPoi?.id!!.toInt()
            autoTextPincode.setText(selectedPoi?.name)
        }

        autoTextPincode.setOnTouchListener { v, event ->
            autoTextPincode.showDropDown()
            false
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvGarageImages!!.setLayoutManager(layoutManager)
        imagesAdapter = ImagesAdapter(this, files!!)
        rvGarageImages!!.setAdapter(imagesAdapter)

        getLastLocation()

        llPersonalDetail.setOnClickListener {
            if (llPersonal.visibility == View.VISIBLE) {
                llPersonal.visibility = View.GONE
                imgPersonal.rotation = 180f
            } else {
                llPersonal.visibility = View.VISIBLE
                imgPersonal.rotation = 0f
            }
        }
        llContactDetails.setOnClickListener {
            if (llContact.visibility == View.VISIBLE) {
                llContact.visibility = View.GONE
                imgContact.rotation = 0f
            } else {
                llContact.visibility = View.VISIBLE
                imgContact.rotation = 180f
            }
        }
        llAccountDetails.setOnClickListener {
            if (llAccount.visibility == View.VISIBLE) {
                llAccount.visibility = View.GONE
                imgAccount.rotation = 0f
            } else {
                llAccount.visibility = View.VISIBLE
                imgAccount.rotation = 180f
            }
        }
        llWorkingHours.setOnClickListener {
            if (llWorking.visibility == View.VISIBLE) {
                llWorking.visibility = View.GONE
                imgWorkingHours.rotation = 0f
            } else {
                llWorking.visibility = View.VISIBLE
                imgWorkingHours.rotation = 180f
            }
        }
        llPaymentMode.setOnClickListener {
            if (llPayment.visibility == View.VISIBLE) {
                llPayment.visibility = View.GONE
                imgPaymentMode.rotation = 0f
            } else {
                llPayment.visibility = View.VISIBLE
                imgPaymentMode.rotation = 180f
            }
        }

        chkMonday.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                txtOpenTimeMon.isEnabled = true
                txtCloseTimeMon.isEnabled = true
            } else {
                txtOpenTimeMon.isEnabled = false
                txtCloseTimeMon.isEnabled = false
            }
        }

        chkTuesday.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                txtOpenTimeTue.isEnabled = true
                txtCloseTimeTue.isEnabled = true
            } else {
                txtOpenTimeTue.isEnabled = false
                txtCloseTimeTue.isEnabled = false
            }
        }

        chkWed.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                txtOpenTimeWed.isEnabled = true
                txtCloseTimeWed.isEnabled = true
            } else {
                txtOpenTimeWed.isEnabled = false
                txtCloseTimeWed.isEnabled = false
            }
        }

        chkThu.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                txtOpenTimeThu.isEnabled = true
                txtCloseTimeThu.isEnabled = true
            } else {
                txtOpenTimeThu.isEnabled = false
                txtCloseTimeThu.isEnabled = false
            }
        }

        chkFri.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                txtOpenTimeFri.isEnabled = true
                txtCloseTimeFri.isEnabled = true
            } else {
                txtOpenTimeFri.isEnabled = false
                txtCloseTimeFri.isEnabled = false
            }
        }

        chkSat.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                txtOpenTimeSat.isEnabled = true
                txtCloseTimeSat.isEnabled = true
            } else {
                txtOpenTimeSat.isEnabled = false
                txtCloseTimeSat.isEnabled = false
            }
        }

        chkSun.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                txtOpenTimeSun.isEnabled = true
                txtCloseTimeSun.isEnabled = true
            } else {
                txtOpenTimeSun.isEnabled = false
                txtCloseTimeSun.isEnabled = false
            }
        }

        txtOpenTimeMon.setOnClickListener {
            showTimer(txtOpenTimeMon)
        }

        txtCloseTimeMon.setOnClickListener {
            showTimer(txtCloseTimeMon)
        }

        txtOpenTimeTue.setOnClickListener {
            showTimer(txtOpenTimeTue)
        }

        txtCloseTimeTue.setOnClickListener {
            showTimer(txtCloseTimeTue)
        }

        txtOpenTimeWed.setOnClickListener {
            showTimer(txtOpenTimeWed)
        }

        txtCloseTimeWed.setOnClickListener {
            showTimer(txtCloseTimeWed)
        }

        txtOpenTimeThu.setOnClickListener {
            showTimer(txtOpenTimeThu)
        }

        txtCloseTimeThu.setOnClickListener {
            showTimer(txtCloseTimeThu)
        }

        txtOpenTimeFri.setOnClickListener {
            showTimer(txtOpenTimeFri)
        }

        txtCloseTimeFri.setOnClickListener {
            showTimer(txtCloseTimeFri)
        }

        txtOpenTimeSat.setOnClickListener {
            showTimer(txtOpenTimeSat)
        }

        txtCloseTimeSat.setOnClickListener {
            showTimer(txtCloseTimeSat)
        }

        txtOpenTimeSun.setOnClickListener {
            showTimer(txtOpenTimeSun)
        }

        txtCloseTimeSun.setOnClickListener {
            showTimer(txtCloseTimeSun)
        }

        rlOTP.setOnClickListener {
            if (TextUtils.isEmpty(edtPhone.text.toString().trim())) {
                edtPhone.setError("Phone cannot be empty")
                edtPhone.requestFocus()
                return@setOnClickListener
            }
            garageRegistrationPresenter!!.loadData(
                sessionManager.getAuthToken(this)!!, edtPhone.text.toString().trim()
            )
        }
        imgEdit.setOnClickListener {
            type = 0
            showDialog()
        }
        imgPan.setOnClickListener {
            type = 1
            showDialog()
        }
        imgAadhar.setOnClickListener {
            type = 2
            showDialog()
        }
        imgDriving.setOnClickListener {
            type = 3
            showDialog()
        }
        imgPassprt.setOnClickListener {
            type = 4
            showDialog()
        }
        imgVoter.setOnClickListener {
            type = 5
            showDialog()
        }
        imgElectric.setOnClickListener {
            type = 6
            showDialog()
        }
        imgShopLicense.setOnClickListener {
            type = 7
            showDialog()
        }

        txtSelectImages.setOnClickListener {
            openGallery1(this)
        }

        btnNext.setOnClickListener {
            submit()
        }
    }

    fun submit() {
//        if (TextUtils.isEmpty(edtAddedBy.text.toString().trim())) {
//            edtAddedBy.setError("Added By cannot be empty")
//            edtAddedBy.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtGarageName.text.toString().trim())) {
//            edtGarageName.setError("Garage Name cannot be empty")
//            edtGarageName.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtFname.text.toString().trim())) {
//            edtFname.setError("First name cannot be empty")
//            edtFname.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtLname.text.toString().trim())) {
//            edtLname.setError("Last name cannot be empty")
//            edtLname.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtContactPersonName.text.toString().trim())) {
//            edtContactPersonName.setError("Contact person name cannot be empty")
//            edtContactPersonName.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtPhone.text.toString().trim())) {
//            edtPhone.setError("Phone cannot be empty")
//            edtPhone.requestFocus()
//            return
//        }
//        if (TextUtils.equals(edtOTP.text.toString().trim(), otp)) {
//            edtOTP.setError("Enter valid OTP")
//            edtOTP.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtDOB.text.toString().trim())) {
//            edtDOB.setError("DOB cannot be empty")
//            edtDOB.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtAddress.text.toString().trim())) {
//            edtAddress.setError("Address cannot be empty")
//            edtAddress.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtLandmark.text.toString().trim())) {
//            edtLandmark.setError("Landmark cannot be empty")
//            edtLandmark.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(autoTextState.text.toString().trim())) {
//            autoTextState.setError("Select state")
//            autoTextState.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(autoTextCity.text.toString().trim())) {
//            autoTextCity.setError("Select city")
//            autoTextCity.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtLandline.text.toString().trim())) {
//            edtLandline.setError("Landline cannot be empty")
//            edtLandline.requestFocus()
//            return
//        }
//        if (!isValidEmail(edtEmail.text.toString().trim())) {
//            edtEmail.setError("Invalid email id")
//            edtEmail.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtHolderName.text.toString().trim())) {
//            edtHolderName.setError("Holder name cannot be empty")
//            edtHolderName.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtBankName.text.toString().trim())) {
//            edtBankName.setError("Bank name cannot be empty")
//            edtBankName.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtAccountNo.text.toString().trim())) {
//            edtAccountNo.setError("Account no cannot be empty")
//            edtAccountNo.requestFocus()
//            return
//        }
//        if (!isValidIFSC(edtIFSCCode.text.toString().trim())) {
//            edtIFSCCode.setError("Ivalid IFSC code")
//            edtIFSCCode.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtBranchName.text.toString().trim())) {
//            edtBranchName.setError("Branch name cannot be empty")
//            edtBranchName.requestFocus()
//            return
//        }
//        if (!isValidPan(edtPancard.text.toString().trim())) {
//            edtPancard.setError("Pan card number is invalid")
//            edtPancard.requestFocus()
//            return
//        }
//
//        if (workingHourList.isEmpty()) {
//            Toast.makeText(this, "Select working days", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        if (paymentModeList.isEmpty()) {
//            Toast.makeText(this, "Select payment type", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        workingHourList.clear()
//        paymentModeList.clear()
//
//        if (chkMonday.isChecked) {
//            var workingHour = WorkingHour(
//                "0",
//                txtOpenTimeMon.text.toString().trim(),
//                txtCloseTimeMon.text.toString().trim()
//            )
//            workingHourList.add(workingHour)
//        }
//        if (chkTuesday.isChecked) {
//            var workingHour = WorkingHour(
//                "1",
//                txtOpenTimeTue.text.toString().trim(),
//                txtCloseTimeTue.text.toString().trim()
//            )
//            workingHourList.add(workingHour)
//        }
//
//        if (chkWed.isChecked) {
//            var workingHour = WorkingHour(
//                "2",
//                txtOpenTimeWed.text.toString().trim(),
//                txtCloseTimeWed.text.toString().trim()
//            )
//            workingHourList.add(workingHour)
//        }
//
//
//        if (chkThu.isChecked) {
//            var workingHour = WorkingHour(
//                "3",
//                txtOpenTimeThu.text.toString().trim(),
//                txtCloseTimeThu.text.toString().trim()
//            )
//            workingHourList.add(workingHour)
//        }
//
//        if (chkFri.isChecked) {
//            var workingHour = WorkingHour(
//                "4",
//                txtOpenTimeFri.text.toString().trim(),
//                txtCloseTimeFri.text.toString().trim()
//            )
//            workingHourList.add(workingHour)
//        }
//
//        if (chkSat.isChecked) {
//            var workingHour = WorkingHour(
//                "5",
//                txtOpenTimeSat.text.toString().trim(),
//                txtCloseTimeSat.text.toString().trim()
//            )
//            workingHourList.add(workingHour)
//        }
//
//        if (chkSun.isChecked) {
//            var workingHour = WorkingHour(
//                "6",
//                txtOpenTimeSun.text.toString().trim(),
//                txtCloseTimeSun.text.toString().trim()
//            )
//            workingHourList.add(workingHour)
//        }
//
//        if (chkCash.isChecked) {
//            var paymentMode = PaymentMode("1")
//            paymentModeList.add(paymentMode)
//        }
//
//        if (chkCredit.isChecked) {
//            var paymentMode = PaymentMode("2")
//            paymentModeList.add(paymentMode)
//        }
//
//        if (chkCheque.isChecked) {
//            var paymentMode = PaymentMode("3")
//            paymentModeList.add(paymentMode)
//        }
//
//        if (chkDebit.isChecked) {
//            var paymentMode = PaymentMode("4")
//            paymentModeList.add(paymentMode)
//        }
//
//        if (chkBanking.isChecked) {
//            var paymentMode = PaymentMode("5")
//            paymentModeList.add(paymentMode)
//        }
//
//        if (chkPaytm.isChecked) {
//            var paymentMode = PaymentMode("6")
//            paymentModeList.add(paymentMode)
//        }
//
//
//        garageInformation = GarageInformation(
//            edtHolderName.text.toString(),
//            edtAccountNo.text.toString(),
//            edtBankName.text.toString(),
//            edtBranchName.text.toString(),
//            edtAddress.text.toString(),
//            city.toString(),
//            edtEmail.text.toString(),
//            edtLandline.text.toString(),
//            edtLandmark.text.toString(),
//            txtLatitude.text.toString().trim(),
//            "",
//            txtLongitude.text.toString().trim(),
//            edtPhone.text.toString(),
//            edtContactPersonName.text.toString(),
//            pincode.toString(),
//            state.toString(),
//            edtDOB.text.toString(),
//            edtGarageName.text.toString(),
//            edtIFSCCode.text.toString(),
//            edtPhone.text.toString(),
//            edtFname.text.toString(),
//            edtLname.text.toString(),
//            edtPancard.text.toString()
//        )

        startActivity(Intent(this, AutoHub::class.java))

    }

    private fun showDialog() {
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_image, null)
        val dialog = BottomSheetDialog(this)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogView)

        val txtTakePhoto = dialogView.findViewById(R.id.txtTakePhoto) as TextView
        val txtChoosePhoto = dialogView.findViewById(R.id.txtChoosePhoto) as TextView

        txtTakePhoto.setOnClickListener {
            dialog.dismiss()
            utility.capturePhoto(this)
        }

        txtChoosePhoto.setOnClickListener {
            dialog.dismiss()
            val checkSelfPermission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) + ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                //Requests permissions to be granted to this application at runtime
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ), 1
                )
            } else {
                openGallery(this)
            }
        }
        dialog.show()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>
        , grantedResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        when (requestCode) {
            1 ->
                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery(this)
                } else {
                    //show("Unfortunately You are Denied Permission to Perform this Operataion.")
                }
            PERMISSION_ID -> {
                if ((grantedResults.isNotEmpty() && grantedResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLastLocation()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPERATION_CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.format(DecodeFormat.PREFER_ARGB_8888)
                    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)

                    if (source != null) {
                        if (source!!.exists()) {
                            if (type == 0) {
                                file0 = source
                                Glide.with(this).load(source!!.getAbsolutePath())
                                    .apply(requestOptions).into(imgProfile)
                            }
                            if (type == 1) {
                                file1 = source
                                Glide.with(this).load(source!!.getAbsolutePath())
                                    .apply(requestOptions).into(imgPan)
                            }
                            if (type == 2) {
                                file2 = source
                                Glide.with(this).load(source!!.getAbsolutePath())
                                    .apply(requestOptions).into(imgAadhar)
                            }
                            if (type == 3) {
                                file3 = source
                                Glide.with(this).load(source!!.getAbsolutePath())
                                    .apply(requestOptions).into(imgDriving)
                            }
                            if (type == 4) {
                                file4 = source
                                Glide.with(this).load(source!!.getAbsolutePath())
                                    .apply(requestOptions).into(imgPassprt)
                            }
                            if (type == 5) {
                                file5 = source
                                Glide.with(this).load(source!!.getAbsolutePath())
                                    .apply(requestOptions).into(imgVoter)
                            }
                            if (type == 6) {
                                file6 = source
                                Glide.with(this).load(source!!.getAbsolutePath())
                                    .apply(requestOptions).into(imgElectric)
                            }
                            if (type == 7) {
                                file7 = source
                                Glide.with(this).load(source!!.getAbsolutePath())
                                    .apply(requestOptions).into(imgShopLicense)
                            }

                        } else {
                            Toast.makeText(
                                this,
                                "file not found",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
                }
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.format(DecodeFormat.PREFER_ARGB_8888)
                    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                    if (Build.VERSION.SDK_INT >= 19) {
                        try {
                            val contentURI: Uri? = data!!.data
                            var path = FileUtils.getPath(this, contentURI!!)!!
                            val file = File(path)
                            if (file != null) {
                                if (file!!.exists()) {
                                    if (type == 0) {
                                        file0 = file
                                        Glide.with(this).load(contentURI)
                                            .apply(requestOptions).into(imgProfile)
                                    }
                                    if (type == 1) {
                                        file1 = file
                                        Glide.with(this).load(contentURI)
                                            .apply(requestOptions).into(imgPan)
                                    }
                                    if (type == 2) {
                                        file2 = file
                                        Glide.with(this).load(contentURI)
                                            .apply(requestOptions).into(imgAadhar)
                                    }
                                    if (type == 3) {
                                        file3 = file
                                        Glide.with(this).load(contentURI)
                                            .apply(requestOptions).into(imgDriving)
                                    }
                                    if (type == 4) {
                                        file4 = file
                                        Glide.with(this).load(contentURI)
                                            .apply(requestOptions).into(imgPassprt)
                                    }
                                    if (type == 5) {
                                        file5 = file
                                        Glide.with(this).load(contentURI)
                                            .apply(requestOptions).into(imgVoter)
                                    }
                                    if (type == 6) {
                                        file6 = file
                                        Glide.with(this).load(contentURI)
                                            .apply(requestOptions).into(imgElectric)
                                    }
                                    if (type == 7) {
                                        file7 = file
                                        Glide.with(this).load(contentURI)
                                            .apply(requestOptions).into(imgShopLicense)
                                    }

                                } else {
                                    Toast.makeText(
                                        this,
                                        "file not found",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }
                            }
                        } catch (e: URISyntaxException) {
                            e.printStackTrace()
                        }
                    }
                }
            OPERATION_CHOOSE_MULTIPLE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    files.clear()
                    if (data!!.clipData != null) {
                        val count: Int = data!!.clipData!!.itemCount
                        var currentItem = 0
                        while (currentItem < count) {
                            val imageUri: Uri =
                                data!!.clipData!!.getItemAt(currentItem).uri
                            currentItem = currentItem + 1
                            Log.d("Uri Selected", imageUri.toString())
                            try {
                                files.add(imageUri)
                                imagesAdapter!!.notifyDataSetChanged()
                            } catch (e: Exception) {
                                Log.e("TAG", "File select error", e)
                            }
                        }
                    } else if (data.getData() != null) {
                        val uri: Uri = data.data!!
                        try {
                            files.add(uri)
                            imagesAdapter!!.notifyDataSetChanged()
                        } catch (e: java.lang.Exception) {
                            Log.e("TAG", "File select error", e)
                        }
                    }
                }
            PERMISSION_ID ->
                if (checkPermissions()) {
                    if (isLocationEnabled()) {
                        mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                            var location: Location? = task.result
                            if (location == null) {
                                requestNewLocationData()
                            } else {
                                txtLatitude.setText("Lat: " + location.latitude.toString())
                                txtLongitude.setText("Lng: " + location.longitude.toString())
                                getAddress(location.latitude, location.longitude)
                            }
                        }
                    }
                }
        }
    }

    override fun showProgressbar(type: Int) {
        if (type == 4) {
            rlOTP.isClickable = false
            txtSendOTP.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressbar(type: Int) {
        if (type == 4) {
            rlOTP.isClickable = true
            txtSendOTP.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    override fun onSuccess(responseModel: Response<JsonObject>) {
        if (responseModel.body() != null) {
            Toast.makeText(
                this,
                responseModel.body()!!.get("message").asString,
                Toast.LENGTH_LONG
            )
                .show()

            otp = responseModel.body()!!.get("otp").asString

        }
    }

    override fun onSuccessgetStates(responseModel: Response<StateResponse>) {
        if (responseModel.body() != null) {
            stateList!!.clear()
            stateList!!.addAll(responseModel.body()!!)
            val adapter = StateAdapter(this, android.R.layout.simple_list_item_1, stateList!!)
            autoTextState.setAdapter(adapter)
        }
    }

    override fun onSuccessgetCities(responseModel: Response<StateResponse>) {
        if (responseModel.body() != null) {
            cityList!!.clear()
            cityList!!.addAll(responseModel.body()!!)

            val adapter = CityAdapter(this, android.R.layout.simple_list_item_1, cityList!!)
            autoTextCity.setAdapter(adapter)
        }
    }

    override fun onSuccessgetPincodes(responseModel: Response<StateResponse>) {
        if (responseModel.body() != null) {
            pincodeList!!.clear()
            pincodeList!!.addAll(responseModel.body()!!)

            val adapter = PincodeAdapter(this, android.R.layout.simple_list_item_1, pincodeList!!)
            autoTextPincode.setAdapter(adapter)
        }
    }

    override fun onError(errorCode: Int) {
    }

    override fun onError(throwable: Throwable) {
    }

    fun showTimer(editText: EditText) {
        val mcurrentTime: Calendar = Calendar.getInstance()
        val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(
                timePicker: TimePicker?,
                selectedHour: Int,
                selectedMinute: Int
            ) {
//                editText.setText("$selectedHour:$selectedMinute")
                editText.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
            }
        }, hour, minute, true) //Yes 24 hour time

        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        txtLatitude.setText("Lat: " + location.latitude.toString())
                        txtLongitude.setText("Lng: " + location.longitude.toString())
                        getAddress(location.latitude, location.longitude)
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(intent, PERMISSION_ID)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation

            txtLatitude.setText("Lat: " + mLastLocation.latitude.toString())
            txtLongitude.setText("Lng: " + mLastLocation.longitude.toString())
            getAddress(mLastLocation.latitude, mLastLocation.longitude)
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    fun getAddress(latitude: Double, longitude: Double) {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        edtAddress.setText(addresses[0].getAddressLine(0))
        autoTextState1.setText(addresses[0].getAdminArea())
        autoTextCity1.setText(addresses[0].getLocality())
        autoTextPincode1.setText(addresses[0].getPostalCode())

//        val city: String = addresses[0].getLocality()
//        val state: String = addresses[0].getAdminArea()
//        val country: String = addresses[0].getCountryName()
//        val postalCode: String = addresses[0].getPostalCode()
//        val knownName: String = addresses[0].getFeatureName()

    }

}
