package com.channelpartner.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.channelpartner.Constants.Companion.IMAGE_URL
import com.channelpartner.R
import com.channelpartner.model.response.KyCResponse
import com.channelpartner.presenter.KycPresenter
import com.channelpartner.utils.FileUtils.getPath
import com.channelpartner.utils.utility
import com.channelpartner.utils.utility.OPERATION_CAPTURE_PHOTO
import com.channelpartner.utils.utility.OPERATION_CHOOSE_PHOTO
import com.channelpartner.utils.utility.capturePhoto
import com.channelpartner.utils.utility.loadImage
import com.channelpartner.utils.utility.openGallery
import com.channelpartner.utils.utility.source
import com.channelpartner.view.KYCView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_kyc.*
import kotlinx.android.synthetic.main.activity_kyc.imgAadhar
import kotlinx.android.synthetic.main.activity_kyc.imgDriving
import kotlinx.android.synthetic.main.activity_kyc.imgElectric
import kotlinx.android.synthetic.main.activity_kyc.imgPan
import kotlinx.android.synthetic.main.activity_kyc.imgPassprt
import kotlinx.android.synthetic.main.activity_kyc.imgVoter
import kotlinx.android.synthetic.main.activity_kyc.rlSubmit
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import java.net.URISyntaxException

class KYC1 : AppCompatActivity(), KYCView.MainView {

    var kycPresenter: KycPresenter? = null

    var type = 0
    var file0: File? = null
    var file1: File? = null
    var file2: File? = null
    var file3: File? = null
    var file4: File? = null
    var file5: File? = null
    var file6: File? = null

    var image1 = ""
    var image2 = ""
    var image3 = ""
    var image4 = ""
    var image5 = ""
    var image6 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kyc)
        setSupportActionBar(toolBar)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        txtTitle.text = getString(R.string.kyc)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        init()

    }

    fun init() {

        kycPresenter = KycPresenter(this, this)
        kycPresenter!!.loadData(
            sessionManager.getAuthToken(this)!!,
            sessionManager.getUser_ID(this)!!.toInt(),
            sessionManager.getUserType(this)!!.toInt()
        )
        imgPan.setOnClickListener {
            if (TextUtils.isEmpty(image1)) {
                type = 1
                showDialog()
            }
        }
        imgAadhar.setOnClickListener {
            if (TextUtils.isEmpty(image2)) {
                type = 2
                showDialog()
            }
        }
        imgDriving.setOnClickListener {
            if (TextUtils.isEmpty(image3)) {
                type = 3
                showDialog()
            }
        }
        imgPassprt.setOnClickListener {
            if (TextUtils.isEmpty(image4)) {
                type = 4
                showDialog()
            }
        }
        imgVoter.setOnClickListener {
            if (TextUtils.isEmpty(image5)) {
                type = 5
                showDialog()
            }
        }
        imgElectric.setOnClickListener {
            if (TextUtils.isEmpty(image6)) {
                type = 6
                showDialog()
            }
        }
        rlSubmit.setOnClickListener {
            submit()
        }
    }

    fun submit() {
        val user_Id: RequestBody =
            RequestBody.create(
                MultipartBody.FORM,
                sessionManager.getUser_ID(this)!!
            )
        val user_type: RequestBody =
            RequestBody.create(MultipartBody.FORM, sessionManager.getUserType(this)!!)

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
        kycPresenter!!.loadData1(
            sessionManager.getAuthToken(this)!!,
            body0,
            user_Id,
            user_type,
            body1,
            body2,
            body3,
            body4,
            body5,
            body6
        )
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

    override fun showProgressbar(int: Int) {
        if (int == 2){
            rlSubmit.isClickable = false
            btnSubmit.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressbar(int: Int) {
        if (int == 2){
            rlSubmit.isClickable = true
            btnSubmit.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    override fun onSuccess(responseModel: Response<KyCResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.List.size > 0) {
            for (i in responseModel.body()!!.List.indices) {
                if (responseModel.body()!!.List[i].document_type == 1) {
                    image1 = responseModel.body()!!.List[0].image_url
                    loadImage(
                        this,
                        true,
                        2,
                        image1,
                        imgPan
                    )
                }
                if (responseModel.body()!!.List[i].document_type == 2) {
                    image2 = responseModel.body()!!.List[1].image_url
                    loadImage(
                        this,
                        true,
                        2,
                        image2,
                        imgAadhar
                    )
                }
                if (responseModel.body()!!.List[i].document_type == 3) {
                    image3 = responseModel.body()!!.List[2].image_url
                    loadImage(
                        this,
                        true,
                        2,
                        image3,
                        imgDriving
                    )
                }
                if (responseModel.body()!!.List[i].document_type == 4) {
                    image4 = responseModel.body()!!.List[3].image_url
                    loadImage(
                        this,
                        true,
                        2,
                        image4,
                        imgPassprt
                    )
                }
                if (responseModel.body()!!.List[i].document_type == 5) {
                    image5 = responseModel.body()!!.List[4].image_url
                    loadImage(
                        this,
                        true,
                        2,
                        image5,
                        imgVoter
                    )
                }
                if (responseModel.body()!!.List[i].document_type == 6) {
                    image6 = responseModel.body()!!.List[5].image_url
                    loadImage(
                        this,
                        true,
                        2,
                        image6,
                        imgElectric
                    )
                }
            }
        }
    }

    override fun onSuccess1(responseModel: Response<JsonObject>) {
    }

    override fun onError(errorCode: Int) {

    }

    override fun onError(throwable: Throwable) {
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
                            var path = getPath(this, contentURI!!)!!
                            val file = File(path)
                            if (file != null) {
                                if (file!!.exists()) {
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
}