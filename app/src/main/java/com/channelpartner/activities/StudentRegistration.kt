//package com.channelpartner.activities
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.BitmapFactory
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.net.Uri
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.text.TextUtils
//import android.util.Log
//import android.view.MenuItem
//import android.view.View
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.DecodeFormat
//import com.bumptech.glide.load.engine.DiskCacheStrategy
//import com.bumptech.glide.request.RequestOptions
//import com.channelpartner.R
//import com.channelpartner.adapter.*
//import com.channelpartner.model.request.*
//import com.channelpartner.model.request.BoardDetail
//import com.channelpartner.model.request.StandradDetail
//import com.channelpartner.model.request.MediumDetail
//import com.channelpartner.model.request.CategoryDetail
//import com.channelpartner.model.request.PackageDetail
//import com.channelpartner.model.request.SubjetcSpeciality
//import com.channelpartner.model.response.*
//import com.channelpartner.presenter.StudentRegPresenter
//import com.channelpartner.utils.MyEditTextDatePicker
//import com.channelpartner.utils.NetWorkConection
//import com.channelpartner.utils.utility
//import com.channelpartner.utils.utility.DateDialog
//import com.channelpartner.utils.utility.OPERATION_CAPTURE_PHOTO
//import com.channelpartner.utils.utility.OPERATION_CHOOSE_PHOTO
//import com.channelpartner.utils.utility.capturePhoto
//import com.channelpartner.utils.utility.openGallery
//import com.channelpartner.utils.utility.source
//import com.channelpartner.view.ClassRegView
//import com.channelpartner.view.StudentRegView
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.gson.Gson
//import com.google.gson.JsonObject
//import com.kuber.vpn.Utils.sessionManager
//import kotlinx.android.synthetic.main.activity_class_registration.*
//import kotlinx.android.synthetic.main.dialog_class.*
//import kotlinx.android.synthetic.main.toolbar.*
//import okhttp3.MediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import retrofit2.Response
//import java.io.File
//
//class StudentRegistration : AppCompatActivity(), StudentRegView.MainView {
//
//    var studentregpresenter: StudentRegPresenter? = null
//
//    var newBoardAdapter: NewBoardAdapter? = null
//    var newStandardAdapter: NewStandardAdapter? = null
//    var newMediumAdapter: NewMediumAdapter? = null
//    var newCategoryAdapter: NewCategoryAdapter? = null
//    var subjSpecialitiesAdapter: SubjSpecialitiesAdapter? = null
//
//    var genderList: ArrayList<GenderData>? = arrayListOf()
//    var stateList: ArrayList<AllState>? = arrayListOf()
//    var cityList: ArrayList<AllState>? = arrayListOf()
//    var pincodeList: ArrayList<AllState>? = arrayListOf()
//    var boardList: ArrayList<BoardData>? = arrayListOf()
//    var standardList: ArrayList<StandardData>? = arrayListOf()
//    var mediumList: ArrayList<MediumData>? = arrayListOf()
//    var categoryList: ArrayList<CategoryData>? = arrayListOf()
//    var specialityList: ArrayList<AllSpeciality>? = arrayListOf()
//
//    var board_details: ArrayList<BoardDetail>? = arrayListOf()
//    var category_details: ArrayList<CategoryDetail>? = arrayListOf()
//    var medium_details: ArrayList<MediumDetail>? = arrayListOf()
//    var standrad_details: ArrayList<StandradDetail>? = arrayListOf()
//    var package_details: ArrayList<PackageDetail>? = arrayListOf()
//    var subjetc_speciality: ArrayList<SubjetcSpeciality>? = arrayListOf()
//
//    var myedittextdatepicker: MyEditTextDatePicker? = null
//    var gender = 0;
//    var type = 0;
//    var state = 0;
//    var city = 0;
//    var pincode = 0;
//    var total = 0L;
//    var gst = 0;
//    var ihc = 0;
//    var std_id = 0;
//    var medium_id = 0;
//    var board_id = 0;
//    var file0: File? = null;
//    var parts: ArrayList<MultipartBody.Part> = arrayListOf()
//    var files: ArrayList<Uri> = arrayListOf()
//
//    var paid_amount = 0L;
//    var id: Int = 0;
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_class_registration)
//        setSupportActionBar(toolBar)
//        txtTitle.text = getString(R.string.student_reg)
//        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
//        toolBar.setNavigationOnClickListener {
//            onBackPressed()
//        }
//        init()
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    fun init() {
//        studentregpresenter = StudentRegPresenter(this, this)
//
//        edtClassDate.setHint("D.O.B")
//        edtClassDate.setOnClickListener {
//            DateDialog(this, edtClassDate)
//        }
//
//        edtFName.hint = getString(R.string.first_name)
//        edtLname.visibility = View.VISIBLE
//        autoTextGender.visibility = View.VISIBLE
//        autoTextCategory.visibility = View.GONE
//        edtSubSpecaility.visibility = View.GONE
//        edtTeachingExp.visibility = View.GONE
//        edtDesc.visibility = View.GONE
//        imgPhoto.visibility = View.GONE
//        imgLogo.visibility = View.GONE
//        edtClassRegistrationNumber.visibility = View.GONE
//
//        genderList!!.clear()
//        genderList!!.add(GenderData(1, "Male"))
//        genderList!!.add(GenderData(2, "Female"))
//        val adapter = GenderAdapter(this, android.R.layout.simple_list_item_1, genderList!!)
//        autoTextGender.setAdapter(adapter)
//
//        studentregpresenter!!.loadStates(
//            sessionManager.getAuthToken(this)!!)
//        studentregpresenter!!.loadBoardData(
//            sessionManager.getAuthToken(this)!!)
//        studentregpresenter!!.loadStandardData(
//            sessionManager.getAuthToken(this)!!)
//        studentregpresenter!!.loadMediumData(
//            sessionManager.getAuthToken(this)!!)
//        studentregpresenter!!.loadCategoryData(
//            sessionManager.getAuthToken(this)!!)
//        studentregpresenter!!.loadSubSpecialitiesData(
//            sessionManager.getAuthToken(this)!!)
//
//        autoTextGender.setOnItemClickListener() { parent, _, position, id ->
//            val selectedPoi = parent.adapter.getItem(position) as GenderData?
//            autoTextGender.setText(selectedPoi?.gender)
//            gender = selectedPoi?.id!!.toInt()
//        }
//
//        autoTextGender.setOnTouchListener { v, event ->
//            autoTextGender.showDropDown()
//            false
//        }
//
//        autoTextState.setOnItemClickListener() { parent, _, position, id ->
//            val selectedPoi = parent.adapter.getItem(position) as AllState?
//            autoTextState.setText(selectedPoi?.name)
//            state = selectedPoi?.id!!.toInt()
//            studentregpresenter!!.loadCities(
//                sessionManager.getAuthToken(this)!!,selectedPoi.id.toString())
//        }
//
//        autoTextState.setOnTouchListener { v, event ->
//            autoTextState.showDropDown()
//            false
//        }
//
//        autoTextCity.setOnItemClickListener() { parent, _, position, id ->
//            val selectedPoi = parent.adapter.getItem(position) as AllState?
//            autoTextCity.setText(selectedPoi?.name)
//            city = selectedPoi?.id!!.toInt()
//            studentregpresenter!!.loadPincodes(
//                sessionManager.getAuthToken(this)!!,selectedPoi.id.toString())
//        }
//
//        autoTextCity.setOnTouchListener { v, event ->
//            autoTextCity.showDropDown()
//            false
//        }
//
//        autoTextPincode.setOnItemClickListener() { parent, _, position, id ->
//            val selectedPoi = parent.adapter.getItem(position) as AllState?
//            pincode = selectedPoi?.id!!.toInt()
//            autoTextPincode.setText(selectedPoi.name)
//        }
//
//        autoTextPincode.setOnTouchListener { v, event ->
//            autoTextPincode.showDropDown()
//            false
//        }
//
//        autoTextBoard.setOnClickListener {
//            if (boardList!!.size != 0)
//                showBoardDialog(boardList!!)
//        }
//
//        autoTextStandard.setOnClickListener {
//            if (standardList!!.size != 0)
//                showStandardDialog(standardList!!)
//        }
//
//        autoTextMedium.setOnClickListener {
//            if (mediumList!!.size != 0)
//                showMediumDialog(mediumList!!)
//        }
//
//        autoTextCategory.setOnClickListener {
//            if (categoryList!!.size != 0)
//                showCategoryDialog(categoryList!!)
//        }
//
//        edtSubSpecaility.setOnClickListener {
//            if (specialityList!!.size != 0)
//                showSubSpecialitiesDialog(specialityList!!)
//        }
//
//        imgEdit.setOnClickListener {
//            type = 0
//            showDialog()
//        }
//
//        imgLogo.setOnClickListener {
//            type = 0
//            showDialog()
//        }
//
//        imgPhoto.setOnClickListener {
//
//            type = 1
//            showDialog()
//        }
//
//        btnSubmit.setOnClickListener(View.OnClickListener {
//            submit()
//        })
//    }
//
//    fun submit() {
//        if (TextUtils.isEmpty(edtFName.text.toString().trim())) {
//            edtFName.setError("Class cannot be empty")
//            edtFName.requestFocus()
//            return
//        }
//        if (!utility.isValidEmail(edtEmailID.text.toString().trim())) {
//            edtEmailID.setError("Enter valid email")
//            edtEmailID.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtPhone.text.toString().trim())) {
//            edtPhone.setError("Phone no. cannot be empty")
//            edtPhone.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtClassDate.text.toString().trim())) {
//            edtClassDate.setError("Select Date")
//            edtClassDate.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(edtAltNo.text.toString().trim())) {
//            edtAltNo.setError("Alt no. cannot be empty")
//            edtAltNo.requestFocus()
//            return
//        }
////        if (TextUtils.isEmpty(edtStdOther.text.toString().trim())) {
////            edtStdOther.setError("Std other cannot be empty")
////            edtStdOther.requestFocus()
////            return
////        }
//        if (TextUtils.isEmpty(edtAddress.text.toString().trim())) {
//            edtAddress.setError("Address cannot be empty")
//            edtAddress.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(autoTextState.text.toString().trim())) {
//            autoTextState.error = "Select state"
//            autoTextState.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(autoTextCity.text.toString().trim())) {
//            autoTextCity.error = "Select city"
//            autoTextCity.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(autoTextPincode.text.toString().trim())) {
//            autoTextPincode.error = "Select pincode"
//            autoTextPincode.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(autoTextBoard.text.toString().trim())) {
//            autoTextBoard.setError("Select board")
//            autoTextBoard.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(autoTextStandard.text.toString().trim())) {
//            autoTextStandard.setError("Select standard")
//            autoTextStandard.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(autoTextMedium.text.toString().trim())) {
//            autoTextMedium.setError("Select medium")
//            autoTextMedium.requestFocus()
//            return
//        }
////        if (TextUtils.isEmpty(autoTextCategory.text.toString().trim())) {
////            autoTextCategory.setError(getString(R.string.class_name_empty))
////            autoTextCategory.requestFocus()
////            return
////        }
////        if (TextUtils.isEmpty(edtSubSpecaility.text.toString().trim())) {
////            edtSubSpecaility.setError(getString(R.string.class_name_empty))
////            edtSubSpecaility.requestFocus()
////            return
////        }
////        if (TextUtils.isEmpty(edtTeachingExp.text.toString().trim())) {
////            edtTeachingExp.setError(getString(R.string.class_name_empty))
////            edtTeachingExp.requestFocus()
////            return
////        }
////        if (TextUtils.isEmpty(edtDesc.text.toString().trim())) {
////            edtDesc.setError(getString(R.string.class_name_empty))
////            edtDesc.requestFocus()
////            return
////        }
//
////        if (file0 == null) {
////            Toast.makeText(this, "Select class Logo", Toast.LENGTH_LONG).show()
////            return
////        }
//
////        if (files.isEmpty()) {
////            Toast.makeText(this, "Select Class picture", Toast.LENGTH_LONG).show()
////            return
////        }
//
//        val jsonObject = StudentRegRequest(
//            "0",
//            board_id,
//            edtAddress.text.toString().trim(),
//            city,
//            edtEmailID.text.toString().trim(),
//            pincode,
//            state,
//            sessionManager.getUser_ID(this)!!.toInt(),
//            edtClassDate.text.toString().trim(),
//            edtFName.text.toString().trim(),
//            gender,
//            "0",
//            "0",
//            edtLname.text.toString().trim(),
//            medium_id,
//            edtPhone.text.toString().trim(),
//            std_id,
//            "0"
//        )
//
//        val json = Gson().toJson(jsonObject)
//
//        val user_Id: RequestBody =
//            RequestBody.create(
//                MultipartBody.FORM,
//                sessionManager.getUser_ID(this)
//            )
//        val user_type: RequestBody =
//            RequestBody.create(MultipartBody.FORM, "6")
//
//        val data: RequestBody =
//            RequestBody.create(MultipartBody.FORM, json)
//
//        var body0: MultipartBody.Part? = null
//        if (file0 != null) {
//            val reqFile0: RequestBody =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file0!!)
//            body0 = MultipartBody.Part.createFormData("profile", file0!!.name, reqFile0)
//        } else {
//            val attachmentEmpty =
//                RequestBody.create(MediaType.parse("text/plain"), "")
//            body0 = MultipartBody.Part.createFormData("profile", "", attachmentEmpty);
//        }
//
//        if (files != null) {
//            parts.clear()
//            for (i in files.indices) {
//                parts.add(utility.prepareFilePart(this, "gallary", files.get(i))!!)
//            }
//        }
//        studentregpresenter!!.CPImageUploads(
//            sessionManager.getAuthToken(this)!!,
//            body0,
//            user_Id,
//            user_type,
//            data,
//            parts
//        )
//    }
//
//    override fun showProgressbar(type: Int) {
//        when (type) {
//            9 -> {
//                rlSubmit.isClickable = false
//                btnSubmit.visibility = View.GONE
//                progressBar.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    override fun hideProgressbar(type: Int) {
//        when (type) {
//            9 -> {
//                rlSubmit.isClickable = true
//                btnSubmit.visibility = View.VISIBLE
//                progressBar.visibility = View.GONE
//            }
//        }
//    }
//
//    override fun onSuccessgetStates(responseModel: Response<StateResponse>) {
//        if (responseModel.body() != null) {
//            stateList!!.clear()
//            stateList!!.addAll(responseModel.body()!!)
//            val adapter = StateAdapter(this, android.R.layout.simple_list_item_1, stateList!!)
//            autoTextState.setAdapter(adapter)
//        }
//    }
//
//    override fun onSuccessgetCities(responseModel: Response<StateResponse>) {
//        if (responseModel.body() != null) {
//            cityList!!.clear()
//            cityList!!.addAll(responseModel.body()!!)
//
//            val adapter = CityAdapter(this, android.R.layout.simple_list_item_1, cityList!!)
//            autoTextCity.setAdapter(adapter)
//        }
//    }
//
//    override fun onSuccessgetPincodes(responseModel: Response<StateResponse>) {
//        if (responseModel.body() != null) {
//            pincodeList!!.clear()
//            pincodeList!!.addAll(responseModel.body()!!)
//
//            val adapter = PincodeAdapter(this, android.R.layout.simple_list_item_1, pincodeList!!)
//            autoTextPincode.setAdapter(adapter)
//        }
//    }
//
//    override fun onSuccessBoard(responseModel: Response<BoardResponse>) {
//        if (responseModel.body() != null) {
//            boardList!!.clear()
//            boardList!!.addAll(responseModel.body()!!.AllBoard)
//        }
//    }
//
//    override fun onSuccessStandard(responseModel: Response<StandardResponse>) {
//        if (responseModel.body() != null) {
//            standardList!!.clear()
//            standardList!!.addAll(responseModel.body()!!.AllBoard)
//        }
//    }
//
//    override fun onSuccessMedium(responseModel: Response<MediumResponse>) {
//        if (responseModel.body() != null) {
//            mediumList!!.clear()
//            mediumList!!.addAll(responseModel.body()!!.AllBoard)
//        }
//    }
//
//    override fun onSuccessCategory(responseModel: Response<CategoryResponse>) {
//        if (responseModel.body() != null) {
//            categoryList!!.clear()
//            categoryList!!.addAll(responseModel.body()!!.AllCategories)
//        }
//    }
//
//    override fun onSuccessSubSpecialities(responseModel: Response<SubjectSpecialitiesResponse>) {
//        if (responseModel.body() != null) {
//            specialityList!!.clear()
//            specialityList!!.addAll(responseModel.body()!!.AllSpecialities)
//        }
//    }
//
//    override fun onSuccess(type: Int, responseModel: Response<JsonObject>) {
//        if (responseModel.body() != null) {
//            if (type == 1) {
//                id = responseModel.body()!!.get("stud_id").asInt
//
//                studentregpresenter!!.commission(
//                    sessionManager.getAuthToken(this)!!,
//                    edtClassDate.text.toString().trim(),
//                    edtFName.text.toString().trim(),
//                    paid_amount.toString(),
//                    sessionManager.getUser_ID(this)!!.toInt(),
//                    "u",
//                    id,
//                    6
//                )
//            }
//
//            if (type == 2) {
//            }
//
//            if (type == 3) {
//                Toast.makeText(
//                    this!!,
//                    "Successfully register",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//        }
//    }
//
//    override fun onError(errorCode: Int) {
//        if (errorCode == 500) {
//            Toast.makeText(this, getString(R.string.internal_server_error), Toast.LENGTH_SHORT)
//                .show()
//        } else {
//            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onError(throwable: Throwable) {
//        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<out String>
//        , grantedResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
//        when (requestCode) {
//            1 ->
//                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
//                    PackageManager.PERMISSION_GRANTED
//                ) {
//                    openGallery(this)
//                } else {
//                    //show("Unfortunately You are Denied Permission to Perform this Operataion.")
//                }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            OPERATION_CAPTURE_PHOTO ->
//                if (resultCode == Activity.RESULT_OK) {
//                    var requestOptions = RequestOptions()
//                    requestOptions = requestOptions.format(DecodeFormat.PREFER_ARGB_8888)
//                    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
//
//                    if (utility.source != null) {
//                        if (utility.source!!.exists()) {
//                            if (type == 0) {
//                                file0 = source
//                                Glide.with(this).load(utility.source!!.getAbsolutePath())
//                                    .apply(requestOptions).into(imgProfile)
//                            } else {
//                                val uri: Uri = Uri.fromFile(utility.source)
//                                try {
//                                    files.add(uri)
//                                    Glide.with(this).load(uri)
//                                        .apply(requestOptions).into(imgPhoto)
//                                } catch (e: java.lang.Exception) {
//                                    Log.e("TAG", "File select error", e)
//                                }
//                            }
//                        }
//                    }
//
//                }
//            OPERATION_CHOOSE_PHOTO ->
//                if (resultCode == Activity.RESULT_OK) {
//                    var requestOptions = RequestOptions()
//                    requestOptions = requestOptions.format(DecodeFormat.PREFER_ARGB_8888)
//                    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        val uri: Uri = data!!.data!!
//                        if (type == 0) {
//                            try {
//                                val path = utility.getPath(this, uri)!!
//                                val file = File(path)
//                                file0 = file
//                                Glide.with(this).load(uri)
//                                    .apply(requestOptions).into(imgProfile)
//                            } catch (e: java.lang.Exception) {
//                                Log.e("TAG", "File select error", e)
//                            }
//                        } else {
//                            try {
//                                files.add(uri)
//                                Glide.with(this).load(uri)
//                                    .apply(requestOptions).into(imgPhoto)
//                            } catch (e: java.lang.Exception) {
//                                Log.e("TAG", "File select error", e)
//                            }
//                        }
//                    }
//                }
//        }
//    }
//
//    private fun showDialog() {
//        val dialogView: View = layoutInflater.inflate(R.layout.dialog_image, null)
//        val dialog = BottomSheetDialog(this)
//        dialog.setCanceledOnTouchOutside(true)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(dialogView)
//
//        val txtTakePhoto = dialogView.findViewById(R.id.txtTakePhoto) as TextView
//        val txtChoosePhoto = dialogView.findViewById(R.id.txtChoosePhoto) as TextView
//
//        txtTakePhoto.setOnClickListener {
//            dialog.dismiss()
//            capturePhoto(this)
//        }
//
//        txtChoosePhoto.setOnClickListener {
//            dialog.dismiss()
//            val checkSelfPermission = ContextCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) + ContextCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
//                //Requests permissions to be granted to this application at runtime
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(
//                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        android.Manifest.permission.READ_EXTERNAL_STORAGE
//                    ), 1
//                )
//            } else {
//                openGallery(this)
//            }
//        }
//        dialog.show()
//
//    }
//
//    private fun showBoardDialog(boardList1: ArrayList<BoardData>) {
//        val dialogView: View = layoutInflater.inflate(R.layout.dialog_recyclerview, null)
//        val dialog = BottomSheetDialog(this)
//        dialog.setCanceledOnTouchOutside(true)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(dialogView)
//
//        val rvDialog = dialogView.findViewById(R.id.rvDialog) as RecyclerView
//        val btnDone = dialogView.findViewById(R.id.btnDone) as Button
//
//        val layoutManager = LinearLayoutManager(this)
//        rvDialog!!.setLayoutManager(layoutManager)
//        newBoardAdapter =
//            NewBoardAdapter(this, boardList1!!) { boardList1 -> onClick(boardList1) }
//        rvDialog!!.setAdapter(newBoardAdapter)
//
//        btnDone.setOnClickListener {
//            board_details!!.clear()
//            val sb = StringBuffer()
//            for (i in boardList1.indices) {
//                if (boardList1.get(i).isChecked) {
//                    val json = BoardDetail(boardList1[i].id.toInt())
//                    board_details!!.add(json)
//                    sb.append(boardList1[i].board)
//                    sb.append(", ")
//                }
//            }
//            autoTextBoard.setText(sb.toString())
//            dialog.dismiss()
//        }
//        dialog.show()
//
//    }
//
//    private fun showStandardDialog(standardList1: ArrayList<StandardData>) {
//        val dialogView: View = layoutInflater.inflate(R.layout.dialog_recyclerview, null)
//        val dialog = BottomSheetDialog(this)
//        dialog.setCanceledOnTouchOutside(true)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(dialogView)
//
//        val rvDialog = dialogView.findViewById(R.id.rvDialog) as RecyclerView
//        val btnDone = dialogView.findViewById(R.id.btnDone) as Button
//
//        val layoutManager = LinearLayoutManager(this)
//        rvDialog!!.setLayoutManager(layoutManager)
//        newStandardAdapter =
//            NewStandardAdapter(this, standardList1!!) { standardList1 -> onClick(standardList1) }
//        rvDialog!!.setAdapter(newStandardAdapter)
//
//        btnDone.setOnClickListener {
//            standrad_details!!.clear()
//            package_details!!.clear()
//            val sb = StringBuffer()
//            for (i in standardList1.indices) {
//                if (standardList1[i].isChecked) {
//                    val json = StandradDetail(standardList1[i].std_id.toInt(),0)
//                    standrad_details!!.add(json)
//                    sb.append(standardList1[i].standard)
//                    sb.append(", ")
//
//                    val json1 = PackageDetail("5000", standardList1[i].standard)
//                    package_details!!.add(json1)
//                }
//            }
//            autoTextStandard.setText(sb.toString())
//            paid_amount = (5000L * package_details!!.size)
//            gst = (900 * package_details!!.size)
//            ihc = (118 * package_details!!.size)
//            total = (paid_amount) + gst + ihc
//            dialog.dismiss()
//        }
//        dialog.show()
//
//    }
//
//    private fun showMediumDialog(mediumList1: ArrayList<MediumData>) {
//        val dialogView: View = layoutInflater.inflate(R.layout.dialog_recyclerview, null)
//        val dialog = BottomSheetDialog(this)
//        dialog.setCanceledOnTouchOutside(true)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(dialogView)
//
//        val rvDialog = dialogView.findViewById(R.id.rvDialog) as RecyclerView
//        val btnDone = dialogView.findViewById(R.id.btnDone) as Button
//
//        val layoutManager = LinearLayoutManager(this)
//        rvDialog!!.setLayoutManager(layoutManager)
//        newMediumAdapter =
//            NewMediumAdapter(this, mediumList1!!) { mediumList1 -> onClick(mediumList1) }
//        rvDialog!!.setAdapter(newMediumAdapter)
//
//        btnDone.setOnClickListener {
//            medium_details!!.clear()
//            val sb = StringBuffer()
//            for (i in mediumList1.indices) {
//                if (mediumList1.get(i).isChecked) {
//                    val json = MediumDetail(mediumList1[i].id.toInt())
//                    medium_details!!.add(json)
//                    sb.append(mediumList1[i].medium)
//                    sb.append(", ")
//                }
//            }
//            autoTextMedium.setText(sb.toString())
//            dialog.dismiss()
//        }
//        dialog.show()
//
//    }
//
//    private fun showCategoryDialog(categoryList1: ArrayList<CategoryData>) {
//        val dialogView: View = layoutInflater.inflate(R.layout.dialog_recyclerview, null)
//        val dialog = BottomSheetDialog(this)
//        dialog.setCanceledOnTouchOutside(true)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(dialogView)
//
//        val rvDialog = dialogView.findViewById(R.id.rvDialog) as RecyclerView
//        val btnDone = dialogView.findViewById(R.id.btnDone) as Button
//
//        val layoutManager = LinearLayoutManager(this)
//        rvDialog!!.setLayoutManager(layoutManager)
//        newCategoryAdapter =
//            NewCategoryAdapter(this, categoryList1!!) { categoryList1 -> onClick(categoryList1) }
//        rvDialog!!.setAdapter(newCategoryAdapter)
//
//        btnDone.setOnClickListener {
//            category_details!!.clear()
//            val sb = StringBuffer()
//            for (i in categoryList1.indices) {
//                if (categoryList1.get(i).isChecked) {
//                    val json = CategoryDetail(categoryList1[i].category_id)
//                    category_details!!.add(json)
//                    sb.append(categoryList1[i].category_name)
//                    sb.append(", ")
//                }
//            }
//            autoTextCategory.setText(sb.toString())
//            dialog.dismiss()
//        }
//        dialog.show()
//
//    }
//
//    private fun showSubSpecialitiesDialog(subSpecialitiesList1: ArrayList<AllSpeciality>) {
//        val dialogView: View = layoutInflater.inflate(R.layout.dialog_recyclerview, null)
//        val dialog = BottomSheetDialog(this)
//        dialog.setCanceledOnTouchOutside(true)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(dialogView)
//
//        val rvDialog = dialogView.findViewById(R.id.rvDialog) as RecyclerView
//        val btnDone = dialogView.findViewById(R.id.btnDone) as Button
//
//        val layoutManager = LinearLayoutManager(this)
//        rvDialog!!.setLayoutManager(layoutManager)
//        subjSpecialitiesAdapter =
//            SubjSpecialitiesAdapter(this, subSpecialitiesList1!!) { subSpecialitiesList1 ->
//                onClick(
//                    subSpecialitiesList1
//                )
//            }
//        rvDialog!!.setAdapter(subjSpecialitiesAdapter)
//
//        btnDone.setOnClickListener {
//            subjetc_speciality!!.clear()
//            val sb = StringBuffer()
//            for (i in subSpecialitiesList1.indices) {
//                if (subSpecialitiesList1.get(i).isChecked) {
//                    val json =
//                        SubjetcSpeciality(subSpecialitiesList1[i].sub_speciality_id.toString())
//                    subjetc_speciality!!.add(json)
//                    sb.append(subSpecialitiesList1[i].sub_speciality_name)
//                    sb.append(", ")
//                }
//            }
//            edtSubSpecaility.setText(sb.toString())
//            dialog.dismiss()
//        }
//        dialog.show()
//    }
//
//    fun onClick(data: BoardData) {
//        for (i in boardList!!.indices) {
//            if (boardList!![i].isChecked) {
//                boardList!![i].isChecked = false
//            }
//        }
//        data.isChecked = !data.isChecked
//        newBoardAdapter!!.notifyDataSetChanged()
//        board_id = data.id.toInt()
//    }
//
//    fun onClick(data: StandardData) {
//        for (i in standardList!!.indices) {
//            if (standardList!![i].isChecked) {
//                standardList!![i].isChecked = false
//            }
//        }
//        data.isChecked = !data.isChecked
//        newStandardAdapter!!.notifyDataSetChanged()
//        std_id = data.std_id
//    }
//
//    fun onClick(data: MediumData) {
//        for (i in mediumList!!.indices) {
//            if (mediumList!![i].isChecked) {
//                mediumList!![i].isChecked = false
//            }
//        }
//        data.isChecked = !data.isChecked
//        newMediumAdapter!!.notifyDataSetChanged()
//        medium_id = data.id.toInt()
//    }
//
//    fun onClick(data: CategoryData) {
//        data.isChecked = !data.isChecked
//        newCategoryAdapter!!.notifyDataSetChanged()
//    }
//
//    fun onClick(data: AllSpeciality) {
//        data.isChecked = !data.isChecked
//        subjSpecialitiesAdapter!!.notifyDataSetChanged()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        studentregpresenter!!.onStop()
//    }
//
//}
//
