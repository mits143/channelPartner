package com.channelpartner.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.channelpartner.R
import com.channelpartner.adapter.*
import com.channelpartner.model.response.*
import com.channelpartner.presenter.ProfileDetailPresenter
import com.channelpartner.utils.utility
import com.channelpartner.utils.utility.DateDialog
import com.channelpartner.utils.utility.OPERATION_CAPTURE_PHOTO
import com.channelpartner.utils.utility.OPERATION_CHOOSE_PHOTO
import com.channelpartner.utils.utility.capturePhoto
import com.channelpartner.utils.utility.getPath
import com.channelpartner.utils.utility.isValidEmail
import com.channelpartner.utils.utility.loadImage
import com.channelpartner.utils.utility.openGallery
import com.channelpartner.utils.utility.source
import com.channelpartner.view.ProfileDetailView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_my_income.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_my_profile.autoTextCity
import kotlinx.android.synthetic.main.activity_my_profile.autoTextPincode
import kotlinx.android.synthetic.main.activity_my_profile.autoTextState
import kotlinx.android.synthetic.main.activity_my_profile.edtAddress
import kotlinx.android.synthetic.main.activity_my_profile.edtDOB
import kotlinx.android.synthetic.main.activity_my_profile.edtEmail
import kotlinx.android.synthetic.main.activity_my_profile.edtFname
import kotlinx.android.synthetic.main.activity_my_profile.edtLandline
import kotlinx.android.synthetic.main.activity_my_profile.edtLandmark
import kotlinx.android.synthetic.main.activity_my_profile.edtLname
import kotlinx.android.synthetic.main.activity_my_profile.edtPhone
import kotlinx.android.synthetic.main.activity_my_profile.imgProfile
import kotlinx.android.synthetic.main.activity_my_profile.progressBar
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import java.net.URISyntaxException


class MyProfile : AppCompatActivity(), ProfileDetailView.MainView {

    var profiledetailpresenter: ProfileDetailPresenter? = null
    var stateAdapter: SpinStateAdapter? = null
    var cityAdapter: SpinCityAdapter? = null
    var pincodeAdapter: SpinPincodeAdapter? = null
    var isBackAllowed: Boolean? = false

    var stateList: ArrayList<AllState>? = arrayListOf()
    var cityList: ArrayList<AllState>? = arrayListOf()
    var pincodeList: ArrayList<AllState>? = arrayListOf()

    var file0: File? = null

    var menuItem: MenuItem? = null

    var state = ""
    var city = ""
    var pincode = ""
    var generation_id = 0
    var level_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        setSupportActionBar(toolBar)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        txtTitle.text = getString(R.string.my_profile)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        profiledetailpresenter = ProfileDetailPresenter(this, this)
//        profiledetailpresenter!!.loadData(
//            sessionManager.getAuthToken(this)!!,
//            0,
//            "",
//            sessionManager.getUser_ID(this)!!.toInt(),
//            sessionManager.getUserType(this)!!.toInt(),
//            "",
//            generation_id,
//            level_id
//        )
        profiledetailpresenter!!.loadData(
            sessionManager.getAuthToken(this)!!
        )

        stateAdapter = SpinStateAdapter(this, stateList!!)
        spinState.adapter = stateAdapter


        cityAdapter = SpinCityAdapter(this, cityList!!)
        spinCity.adapter = cityAdapter


        pincodeAdapter = SpinPincodeAdapter(this, pincodeList!!)
        spinPincode.adapter = pincodeAdapter

        edtDOB.setOnClickListener {
            DateDialog(this, edtDOB)
        }

        autoTextState.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as AllState?
            state = selectedPoi?.id!!.toString()
            autoTextState.setText(selectedPoi?.name)
            profiledetailpresenter!!.loadCities(
                sessionManager.getAuthToken(this)!!, selectedPoi?.id
            )
        }

        autoTextState.setOnTouchListener { v, event ->
            autoTextState.showDropDown()
            false
        }

        autoTextCity.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as AllState?
            city = selectedPoi?.id!!.toString()
            autoTextCity.setText(selectedPoi?.name)
            profiledetailpresenter!!.loadPincodes(
                sessionManager.getAuthToken(this)!!, selectedPoi?.id
            )
        }

        autoTextCity.setOnTouchListener { v, event ->
            autoTextCity.showDropDown()
            false
        }

        autoTextPincode.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as AllState?
            pincode = selectedPoi?.id!!.toString()
            autoTextPincode.setText(selectedPoi?.name)
        }

        autoTextPincode.setOnTouchListener { v, event ->
            autoTextPincode.showDropDown()
            false
        }

        spinState?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                state = stateList!!.get(position).id.toString()
                profiledetailpresenter!!.loadCities(
                    sessionManager.getAuthToken(this@MyProfile)!!, state.toInt()
                )
            }

        }

        spinCity?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                city = cityList!!.get(position).id.toString()
                profiledetailpresenter!!.loadPincodes(
                    sessionManager.getAuthToken(this@MyProfile)!!, city.toInt()
                )
            }

        }

        spinPincode?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pincode = pincodeList!!.get(position).id.toString()
            }

        }

        llPersonalDetail.setOnClickListener(View.OnClickListener {
            if (llPersonal.visibility == View.VISIBLE) {
                llPersonal.visibility = View.GONE
                imgPersonal.rotation = 180f
            } else {
                llPersonal.visibility = View.VISIBLE
                imgPersonal.rotation = 0f
            }
        })

        llNomineeDetail.setOnClickListener(View.OnClickListener {
            if (llNominee.visibility == View.VISIBLE) {
                llNominee.visibility = View.GONE
                imgNominee.rotation = 0f
            } else {
                llNominee.visibility = View.VISIBLE
                imgNominee.rotation = 180f
            }
        })

        btnViewKYC.setOnClickListener(View.OnClickListener {
            if (TextUtils.equals(btnViewKYC.text, getString(R.string.view_kyc))) {
                startActivity(Intent(this, KYC1::class.java))
            } else {
                submit()
            }
        })

        imgEdit.setOnClickListener {
            showDialog()
        }

        editProfile(false)
    }

    fun submit() {
        isBackAllowed = false

        if (TextUtils.isEmpty(edtNomineeFname.text.toString().trim())) {
            edtNomineeFname.setError("Nominee first name cannot be empty")
            edtNomineeFname.requestFocus()
            return
        }

        if (TextUtils.isEmpty(edtNomineeLName.text.toString().trim())) {
            edtNomineeLName.setError("Nominee last name cannot be empty")
            edtNomineeLName.requestFocus()
            return
        }

        if (TextUtils.isEmpty(edtNomineePhone.text.toString().trim())) {
            edtNomineePhone.setError("Invalid mobile number")
            edtNomineePhone.requestFocus()
            return
        }

        if (!isValidEmail(edtNomineeEmail.text.toString().trim())) {
            edtNomineeEmail.setError("Invalid Email Id")
            edtNomineeEmail.requestFocus()
            return
        }

        profiledetailpresenter!!.editProfile(
            sessionManager.getAuthToken(this)!!,
            edtAddress.text.toString().trim(),
            city,
            edtEmail.text.toString().trim(),
            edtLandline.text.toString().trim(),
            edtLandmark.text.toString().trim(),
            edtPhone.text.toString().trim(),
            pincode,
            state,
            edtDOB.text.toString().trim(),
            edtFname.text.toString().trim(),
            edtLname.text.toString().trim(),
            edtNomineeEmail.text.toString().trim(),
            edtNomineeFname.text.toString().trim(),
            edtNomineeLName.text.toString().trim(),
            edtNomineePhone.text.toString().trim(),
            sessionManager.getUser_ID(this)!!.toInt(),
            sessionManager.getUserType(this)!!.toInt(),
            edtPanCard.text.toString().trim()
        )
    }

    fun editProfile(isEditable: Boolean) {
        if (!isEditable) {
            imgEdit.visibility = View.GONE
            edtAddress.isFocusableInTouchMode = false
            edtAddress.clearFocus()
            edtLandmark.isFocusableInTouchMode = false
            edtLandmark.clearFocus()
            edtNomineeFname.isFocusableInTouchMode = false
            edtNomineeFname.clearFocus()
            edtNomineeLName.isFocusableInTouchMode = false
            edtNomineeLName.clearFocus()
            edtNomineeEmail.isFocusableInTouchMode = false
            edtNomineeEmail.clearFocus()
            edtNomineePhone.isFocusableInTouchMode = false
            edtNomineePhone.clearFocus()
            edtState.visibility = View.GONE
            edtCity.visibility = View.GONE
            edtPincode.visibility = View.GONE
            spinState.visibility = View.VISIBLE
            spinState.isEnabled = false
            spinCity.visibility = View.VISIBLE
            spinCity.isEnabled = false
            spinPincode.visibility = View.VISIBLE
            spinPincode.isEnabled = false
            btnViewKYC.setText(getString(R.string.view_kyc))
            try {
                val imm =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                // TODO: handle exception
            }
        } else {
            imgEdit.visibility = View.VISIBLE
            edtAddress.isFocusableInTouchMode = true
            edtLandmark.isFocusableInTouchMode = true
            if (edtNomineeFname.text.length == 0) {
                edtNomineeFname.isFocusableInTouchMode = true
            }
            if (edtNomineeLName.text.length == 0) {
                edtNomineeLName.isFocusableInTouchMode = true
            }
            if (edtNomineeEmail.text.length == 0) {
                edtNomineeEmail.isFocusableInTouchMode = true
            }
            if (edtNomineePhone.text.length == 0) {
                edtNomineePhone.isFocusableInTouchMode = true
            }
            edtState.visibility = View.GONE
            edtCity.visibility = View.GONE
            edtPincode.visibility = View.GONE
            spinState.visibility = View.VISIBLE
            spinState.isEnabled = true
            spinCity.visibility = View.VISIBLE
            spinCity.isEnabled = true
            spinPincode.visibility = View.VISIBLE
            spinPincode.isEnabled = true
            btnViewKYC.setText(getString(R.string.submit))
            try {
                val inputMethodManager: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(edtFname, InputMethodManager.SHOW_IMPLICIT)
            } catch (e: Exception) {
                // TODO: handle exception
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)

        menuItem = menu.findItem(R.id.action_edit)
        menuItem!!.isVisible = true
        val item1 = menu.findItem(R.id.action_notification)
        item1.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                editProfile(true)
                item.setVisible(false)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showProgressbar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        progressBar.visibility = View.GONE
    }

    override fun onSuccessProfile(responseModel: Response<ProfileResponse>) {
        if (responseModel.body() != null) {
            loadImage(this, true, 1, responseModel.body()!!.imageUrl, imgProfile)
            edtFname.setText(responseModel.body()!!.firstName.trim())
            edtLname.setText(responseModel.body()!!.lastName.trim())
            edtEmail.setText(responseModel.body()!!.email.trim())
            edtPhone.setText(responseModel.body()!!.contactNo.trim())
//            edtLandline.setText(responseModel.body()!!.contact_landline.trim())
            edtDOB.setText(responseModel.body()!!.dob.trim())

            edtGender.setText(responseModel.body()!!.gender)
//            when (responseModel.body()!!.gender) {
//                1 -> {
//                    edtGender.setText("Male")
//                }
//                2 -> {
//                    edtGender.setText("Female")
//                }
//                else -> {
//                    edtGender.setText("")
//                }
//            }
            edtAddress.setText(responseModel.body()!!.address.trim())
//            edtPanCard.setText(responseModel.body()!!.pancard_number.trim())
//            edtLandmark.setText(responseModel.body()!!.contact_landmark.trim())
//            edtState.setText(responseModel.body()!!.contact_state.trim())
            state = responseModel.body()!!.stateName.trim()
//            edtCity.setText(responseModel.body()!!.contact_city.trim())
            city = responseModel.body()!!.cityName.trim()
//            edtPincode.setText(responseModel.body()!!.contact_pincode.trim())
            pincode = responseModel.body()!!.pincode.trim()
            autoTextState.setText(responseModel.body()!!.stateName.trim())
            autoTextCity.setText(responseModel.body()!!.cityName.trim())
            autoTextPincode.setText(responseModel.body()!!.pincode.trim())
//            edtNomineeFname.setText(responseModel.body()!!.nominee_firstName.trim())
//            edtNomineeLName.setText(responseModel.body()!!.nominee_lastName.trim())
//            edtNomineeEmail.setText(responseModel.body()!!.nominee_email_id.trim())
//            edtNomineePhone.setText(responseModel.body()!!.nominee_mobile_no.trim())

            profiledetailpresenter!!.loadStates(
                sessionManager.getAuthToken(this)!!
            )

            isBackAllowed = true
        }
    }

    override fun onSuccess(responseModel: Response<ProfileDetailsResponse>) {
        if (responseModel.body() != null) {
            loadImage(this, true, 1, responseModel.body()!!.details.profile_image, imgProfile)
            edtFname.setText(responseModel.body()!!.details.first_name.trim())
            edtLname.setText(responseModel.body()!!.details.last_name.trim())
            edtEmail.setText(responseModel.body()!!.details.contact_email_id.trim())
            edtPhone.setText(responseModel.body()!!.details.contact_mobile.trim())
            edtLandline.setText(responseModel.body()!!.details.contact_landline.trim())
            edtDOB.setText(responseModel.body()!!.details.date_of_birth.trim())
            when (responseModel.body()!!.details.gender) {
                1 -> {
                    edtGender.setText("Male")
                }
                2 -> {
                    edtGender.setText("Female")
                }
                else -> {
                    edtGender.setText("")
                }
            }
            edtAddress.setText(responseModel.body()!!.details.contact_address.trim())
            edtPanCard.setText(responseModel.body()!!.details.pancard_number.trim())
            edtLandmark.setText(responseModel.body()!!.details.contact_landmark.trim())
//            edtState.setText(responseModel.body()!!.details.contact_state.trim())
            state = responseModel.body()!!.details.contact_state.trim()
//            edtCity.setText(responseModel.body()!!.details.contact_city.trim())
            city = responseModel.body()!!.details.contact_city.trim()
//            edtPincode.setText(responseModel.body()!!.details.contact_pincode.trim())
            pincode = responseModel.body()!!.details.contact_pincode.trim()
            autoTextState.setText(responseModel.body()!!.details.contact_state.trim())
            autoTextCity.setText(responseModel.body()!!.details.contact_city.trim())
            autoTextPincode.setText(responseModel.body()!!.details.contact_pincode.trim())
            edtNomineeFname.setText(responseModel.body()!!.details.nominee_firstName.trim())
            edtNomineeLName.setText(responseModel.body()!!.details.nominee_lastName.trim())
            edtNomineeEmail.setText(responseModel.body()!!.details.nominee_email_id.trim())
            edtNomineePhone.setText(responseModel.body()!!.details.nominee_mobile_no.trim())

            profiledetailpresenter!!.loadStates(
                sessionManager.getAuthToken(this)!!
            )

            isBackAllowed = true
        }
    }

    override fun onSuccessEditProfile(int: Int, responseModel: Response<JsonObject>) {
        if (responseModel.body() != null) {
            if (int == 1) {
                val user_Id: RequestBody =
                    RequestBody.create(
                        MultipartBody.FORM,
                        sessionManager.getUser_ID(this)!!
                    )
                val user_type: RequestBody =
                    RequestBody.create(
                        MultipartBody.FORM,
                        sessionManager.getUserType(this)!!
                    )

                var body0: MultipartBody.Part? = null
                if (file0 != null) {
                    val reqFile0: RequestBody =
                        RequestBody.create(
                            MediaType.parse("multipart/form-data"),
                            file0!!
                        )
                    body0 = MultipartBody.Part.createFormData(
                        "profile",
                        file0!!.name,
                        reqFile0
                    )
                } else {
                    val attachmentEmpty =
                        RequestBody.create(MediaType.parse("text/plain"), "")
                    body0 = MultipartBody.Part.createFormData(
                        "profile",
                        "",
                        attachmentEmpty
                    );
                }
                profiledetailpresenter!!.ImageUpload(
                    sessionManager.getAuthToken(this)!!, body0, user_Id, user_type
                )
            }
            if (int == 2) {
                sessionManager.setPofile_photo(
                    this,
                    responseModel.body()!!.get("profile_image").asString
                )
                Toast.makeText(
                    this,
                    "Profile Updated succesfully",
                    Toast.LENGTH_SHORT
                ).show()
                isBackAllowed = true
                editProfile(false)
                menuItem!!.isVisible = true
            }
        }
    }

    override fun onSuccessgetStates(responseModel: Response<StateResponse>) {
        if (responseModel.body() != null) {
            stateList!!.clear()
            val allState: AllState? = AllState("Select State", 0)
            stateList!!.add(0, allState!!)
            stateList!!.addAll(responseModel.body()!!)

//            val adapter = StateAdapter(this, android.R.layout.simple_list_item_1, stateList!!)
//            autoTextState.setAdapter(adapter)


            for (i in stateList!!.indices) {
                if (TextUtils.equals(
                        stateList!![i].id.toString(),
                        state
                    )
                ) {
                    spinState.setSelection(stateList!![i].id.toInt())
                }
            }

            stateAdapter!!.notifyDataSetChanged()

        }
    }

    override fun onSuccessgetCities(responseModel: Response<StateResponse>) {
        if (responseModel.body() != null) {
            cityList!!.clear()
            val allCity: AllState? = AllState("Select City", 0)
            cityList!!.add(0, allCity!!)
            cityList!!.addAll(responseModel.body()!!)

//            val adapter = CityAdapter(this, android.R.layout.simple_list_item_1, cityList!!)
//            autoTextCity.setAdapter(adapter)

            for (i in cityList!!.indices) {
                if (TextUtils.equals(
                        cityList!![i].id.toString(),
                        city
                    )
                ) {
                    spinCity.setSelection(cityList!![i].id)
                }
            }

            cityAdapter!!.notifyDataSetChanged()

        }
    }

    override fun onSuccessgetPincodes(responseModel: Response<StateResponse>) {
        if (responseModel.body() != null) {
            pincodeList!!.clear()
            val allPincode: AllState? = AllState("Select Pincode", 0)
            pincodeList!!.add(0, allPincode!!)
            pincodeList!!.addAll(responseModel.body()!!)

            val adapter =
                PincodeAdapter(this, android.R.layout.simple_list_item_1, pincodeList!!)
            autoTextPincode.setAdapter(adapter)

            for (i in pincodeList!!.indices) {
                if (TextUtils.equals(
                        pincodeList!![i].id.toString(),
                        pincode
                    )
                ) {
                    spinPincode.setSelection(pincodeList!![i].id)
                }
            }

            pincodeAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onError(errorCode: Int) {
        isBackAllowed = true
        if (errorCode == 500) {
            Toast.makeText(this, getString(R.string.internal_server_error), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
        isBackAllowed = true

    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        isBackAllowed = true
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
                            file0 = source

                            loadImage(this, false, 1, source!!.getAbsolutePath(), imgProfile)
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
                            var path = getPath(this, contentURI!!)!!
                            val file = File(path)
                            if (file != null) {
                                if (file!!.exists()) {
                                    file0 = file
//                                    Glide.with(this).load(contentURI)
//                                        .apply(requestOptions).into(imgProfile)

                                    loadImage(this, 1, contentURI, imgProfile)
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

    override fun onBackPressed() {
        if (isBackAllowed!!) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        profiledetailpresenter!!.onStop()
    }
}
