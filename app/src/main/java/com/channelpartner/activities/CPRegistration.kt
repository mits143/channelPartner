package com.channelpartner.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.channelpartner.R
import com.channelpartner.adapter.CityAdapter
import com.channelpartner.adapter.PincodeAdapter
import com.channelpartner.adapter.StateAdapter
import com.channelpartner.model.request.CPRegRequest
import com.channelpartner.model.response.*
import com.channelpartner.presenter.CPRegistrationPresenter
import com.channelpartner.utils.FileUtils
import com.channelpartner.utils.MyEditTextDatePicker
import com.channelpartner.utils.utility.DateDialog
import com.channelpartner.utils.utility.OPERATION_CAPTURE_PHOTO
import com.channelpartner.utils.utility.OPERATION_CHOOSE_PHOTO
import com.channelpartner.utils.utility.capturePhoto
import com.channelpartner.utils.utility.isValidEmail
import com.channelpartner.utils.utility.isValidIFSC
import com.channelpartner.utils.utility.isValidPan
import com.channelpartner.utils.utility.openGallery
import com.channelpartner.utils.utility.source
import com.channelpartner.view.CPRegistrationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_class_registration.*
import kotlinx.android.synthetic.main.activity_cp_registration.*
import kotlinx.android.synthetic.main.activity_cp_registration.btnSubmit
import kotlinx.android.synthetic.main.activity_cp_registration.imgEdit
import kotlinx.android.synthetic.main.activity_cp_registration.imgProfile
import kotlinx.android.synthetic.main.activity_cp_registration.loader
import kotlinx.android.synthetic.main.activity_cp_registration.progressBar
import kotlinx.android.synthetic.main.activity_cp_registration.rlSubmit
import kotlinx.android.synthetic.main.activity_service_detail.radioGroup
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import java.net.URISyntaxException

class CPRegistration : AppCompatActivity(), CPRegistrationView.MainView {

    var cpregistrationpresenter: CPRegistrationPresenter? = null

    var stateList: ArrayList<AllState>? = arrayListOf()
    var cityList: ArrayList<AllState>? = arrayListOf()
    var pincodeList: ArrayList<AllState>? = arrayListOf()

    var type = 0
    var file0: File? = null
    var file1: File? = null
    var file2: File? = null
    var file3: File? = null
    var file4: File? = null
    var file5: File? = null
    var file6: File? = null

    var paid_amount = 0L;
    var id = 0;
    var gender = 0;
    var state = 0;
    var city = 0;
    var pincode = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cp_registration)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.cp_reg)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        cpregistrationpresenter = CPRegistrationPresenter(this, this)

        edtDOB.setOnClickListener {
            DateDialog(this, edtDOB)
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            // This will get the radiobutton that has changed in its check state
            val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
            gender = if (checkedRadioButton == rbMale) {
                1
            } else {
                2
            }
        })

        cpregistrationpresenter!!.loadStates(sessionManager.getAuthToken(this)!!)
        autoTextState.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as AllState?
            state = selectedPoi?.id!!.toInt()
            autoTextState.setText(selectedPoi?.name)
            cpregistrationpresenter!!.loadCities(
                sessionManager.getAuthToken(this)!!,
                selectedPoi?.id
            )
        }

        autoTextState.setOnTouchListener { v, event ->
            autoTextState.showDropDown()
            false
        }

        autoTextCity.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as AllState?
            city = selectedPoi?.id!!.toInt()
            autoTextCity.setText(selectedPoi?.name)
            cpregistrationpresenter!!.loadPincodes(
                sessionManager.getAuthToken(this)!!,
                selectedPoi?.id
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

        llContact.setOnClickListener {
            if (llContactDetails.visibility == View.VISIBLE) {
                llContactDetails.visibility = View.GONE
                imgContact.rotation = 180f
            } else {
                llContactDetails.visibility = View.VISIBLE
                imgContact.rotation = 0f
            }
        }
        llAccount.setOnClickListener {
            if (llAccountDetails.visibility == View.VISIBLE) {
                llAccountDetails.visibility = View.GONE
                imgAccount.rotation = 0f
            } else {
                llAccountDetails.visibility = View.VISIBLE
                imgAccount.rotation = 180f
            }
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
        btnSubmit.setOnClickListener {
            submit()
        }
    }

    fun submit() {
        if (TextUtils.isEmpty(edtFname.text.toString().trim())) {
            edtFname.error = "First name cannot be empty"
            edtFname.requestFocus()
            return
        }
        if (TextUtils.isEmpty(edtLname.text.toString().trim())) {
            edtLname.error = "Last name cannot be empty"
            edtLname.requestFocus()
            return
        }
        if (!isValidEmail(edtEmail.text.toString().trim())) {
            edtEmail.error = "Enter valid email"
            edtEmail.requestFocus()
            return
        }
        if (TextUtils.isEmpty(edtPhone.text.toString().trim())) {
            edtPhone.error = "Mobile No. cannot be empty"
            edtPhone.requestFocus()
            return
        }
        if (TextUtils.isEmpty(edtDOB.text.toString().trim())) {
            edtDOB.error = "D.O.B cannot be empty"
            edtDOB.requestFocus()
            return
        }
        if (TextUtils.isEmpty(edtAddress.text.toString().trim())) {
            edtAddress.error = "Address cannot be empty"
            edtAddress.requestFocus()
            return
        }
        if (TextUtils.isEmpty(edtLandmark.text.toString().trim())) {
            edtLandmark.error = "Landmark cannot be empty"
            edtLandmark.requestFocus()
            return
        }
        if (TextUtils.isEmpty(autoTextState.text.toString().trim())) {
            autoTextState.error = "Select state"
            autoTextState.requestFocus()
            return
        }
        if (TextUtils.isEmpty(autoTextCity.text.toString().trim())) {
            autoTextCity.error = "Select city"
            autoTextCity.requestFocus()
            return
        }
        if (TextUtils.isEmpty(autoTextPincode.text.toString().trim())) {
            autoTextPincode.error = "Select pincode"
            autoTextPincode.requestFocus()
            return
        }
//        if (TextUtils.isEmpty(edtHolderName.text.toString().trim())) {
//            edtHolderName.error = "Holder name cannot be empty"
//            edtHolderName.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtBankName.text.toString().trim())) {
//            edtBankName.error = "Bank name cannot be empty"
//            edtBankName.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtAccountNo.text.toString().trim())) {
//            edtAccountNo.error = "Account no cannot be empty"
//            edtAccountNo.requestFocus()
//            return
//        }
//        if (!isValidIFSC(edtIFSCCode.text.toString().trim())) {
//            edtIFSCCode.setError("Ivalid IFSC code")
//            edtIFSCCode.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtBranchName.text.toString().trim())) {
//            edtBranchName.error = "Branch name cannot be empty"
//            edtBranchName.requestFocus()
//            return
//        }
//        if (!isValidPan(edtPancard.text.toString().trim())) {
//            edtPancard.setError("Pan card number is invalid")
//            edtPancard.requestFocus()
//            return
//        }

//        if (file1 == null) {
//            Toast.makeText(this, "Please pan card image", Toast.LENGTH_LONG).show()
//            return
//        }

        val jsonObject = CPRegRequest(
            sessionManager.getUser_ID(this)!!,
            edtAddress.text.toString().trim(),
            city.toString(),
            edtEmail.text.toString().trim(),
            edtLandline.text.toString().trim(),
            edtPhone.text.toString().trim(),
            pincode.toString(),
            state.toString(),
            edtDOB.text.toString().trim(),
            edtFname.text.toString().trim(),
            edtLname.text.toString().trim(),
            gender,
            1,
            1
        )

        val json = Gson().toJson(jsonObject)

//        val user_Id: RequestBody =
//            RequestBody.create(MultipartBody.FORM, sessionManager.getUser_ID(this))
//
//        val user_type: RequestBody =
//            RequestBody.create(MultipartBody.FORM, sessionManager.getUserType(this)!!)

        val data: RequestBody =
            RequestBody.create(MultipartBody.FORM, json)

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

//        var body1: MultipartBody.Part? = null
//        if (file1 != null) {
//            val reqFile1: RequestBody =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file1!!)
//            body1 = MultipartBody.Part.createFormData("1", "", reqFile1)
//        } else {
//            val attachmentEmpty =
//                RequestBody.create(MediaType.parse("text/plain"), "")
//            body1 = MultipartBody.Part.createFormData("1", "", attachmentEmpty);
//        }
//
//        var body2: MultipartBody.Part? = null
//        if (file2 != null) {
//            val reqFile2: RequestBody =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file2!!)
//            body2 = MultipartBody.Part.createFormData("2", "", reqFile2)
//        } else {
//            val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
//            body2 = MultipartBody.Part.createFormData("2", "", attachmentEmpty);
//        }
//
//        var body3: MultipartBody.Part? = null
//        if (file3 != null) {
//            val reqFile3: RequestBody =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file3!!)
//            body3 = MultipartBody.Part.createFormData("3", "", reqFile3)
//        } else {
//            val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
//            body3 = MultipartBody.Part.createFormData("3", "", attachmentEmpty);
//        }
//
//        var body4: MultipartBody.Part? = null
//        if (file4 != null) {
//            val reqFile4: RequestBody =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file4!!)
//            body4 = MultipartBody.Part.createFormData("4", "", reqFile4)
//        } else {
//            val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
//            body4 = MultipartBody.Part.createFormData("4", "", attachmentEmpty);
//        }
//
//        var body5: MultipartBody.Part? = null
//        if (file5 != null) {
//            val reqFile5: RequestBody =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file5!!)
//            body5 = MultipartBody.Part.createFormData("5", "", reqFile5)
//        } else {
//            val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
//            body5 = MultipartBody.Part.createFormData("5", "", attachmentEmpty);
//        }
//
//        var body6: MultipartBody.Part? = null
//        if (file6 != null) {
//            val reqFile6: RequestBody =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file6!!)
//            body6 = MultipartBody.Part.createFormData("6", "", reqFile6)
//        } else {
//            val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
//            body6 = MultipartBody.Part.createFormData("6", "", attachmentEmpty);
//        }
        cpregistrationpresenter!!.postCPRegisterData(
            sessionManager.getAuthToken(this)!!,
            body0,
            data
        )
    }

    override fun showProgressbar(type: Int) {
        when (type) {
            4 -> {
                loader.visibility = View.VISIBLE
            }
        }
    }

    override fun hideProgressbar(type: Int) {
        when (type) {
            4 -> {
                loader.visibility = View.GONE
            }
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

    override fun onSuccess(type: Int, responseModel: Response<JsonObject>) {
        if (responseModel.body() != null) {
            if (type == 1) {
//                id = responseModel.body()!!.get("userid").asInt
//
//                cpregistrationpresenter!!.commission(
//                    sessionManager.getAuthToken(this)!!,
//                    edtDOB.text.toString().trim(),
//                    edtFname.text.toString().trim(),
//                    paid_amount.toString(),
//                    sessionManager.getUser_ID(this)!!.toInt(),
//                    "C",
//                    id,
//                    2
//                )
                Toast.makeText(
                    this!!,
                    "Successfully register",
                    Toast.LENGTH_LONG
                ).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            if (type == 2) {
//                Toast.makeText(
//                    this!!,
//                    "Successfully register",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
            }
        }
    }

    override fun onError(errorCode: Int) {
        when (errorCode) {
            409 -> {
                Toast.makeText(this, "Email or Mobile is already exist.", Toast.LENGTH_SHORT).show()
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


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantedResults: IntArray
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
        }
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
            capturePhoto(this)
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

    override fun onDestroy() {
        super.onDestroy()
        cpregistrationpresenter!!.onStop()
    }
}
