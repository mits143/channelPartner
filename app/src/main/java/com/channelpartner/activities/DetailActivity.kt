package com.channelpartner.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.channelpartner.R
import com.channelpartner.adapter.AutoHubAdapter
import com.channelpartner.adapter.ImagesAdapter
import com.channelpartner.adapter.ServicesAdapter
import com.channelpartner.model.response.*
import com.channelpartner.model.response.SeviceProvider.PackageDetail
import com.channelpartner.model.response.SeviceProvider.ServiceProviderResponse
import com.channelpartner.presenter.CPDetailPresenter
import com.channelpartner.presenter.GarageRegistrationPresenter
import com.channelpartner.utils.utility
import com.channelpartner.utils.utility.loadImage
import com.channelpartner.view.CPDetailView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_auto_hub.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.edtAddress
import kotlinx.android.synthetic.main.activity_detail.edtCity
import kotlinx.android.synthetic.main.activity_detail.edtDOB
import kotlinx.android.synthetic.main.activity_detail.edtEmail
import kotlinx.android.synthetic.main.activity_detail.edtFname
import kotlinx.android.synthetic.main.activity_detail.edtGender
import kotlinx.android.synthetic.main.activity_detail.edtLandmark
import kotlinx.android.synthetic.main.activity_detail.edtLname
import kotlinx.android.synthetic.main.activity_detail.edtPhone
import kotlinx.android.synthetic.main.activity_detail.edtPincode
import kotlinx.android.synthetic.main.activity_detail.edtState
import kotlinx.android.synthetic.main.activity_detail.imgProfile
import kotlinx.android.synthetic.main.activity_detail.progressBar
import kotlinx.android.synthetic.main.toolbar.*
import org.reactivestreams.Subscription
import retrofit2.Response

class DetailActivity : AppCompatActivity(), CPDetailView.MainView {

    var cpDetailPresenter: CPDetailPresenter? = null
    var isBackAllowed: Boolean? = null
    var cp_id = ""
    var class_name = ""
    var name = ""
    var generation_id = 0
    var level_id = 0

    var imagesAdapter: ImagesAdapter? = null
    var files: ArrayList<Uri> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolBar)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        if (intent.extras != null) {
            cp_id =
                if (TextUtils.isEmpty(intent.extras!!.getString("cp_id"))) "" else intent.extras!!.getString(
                    "cp_id"
                )!!
            class_name =
                if (TextUtils.isEmpty(intent.extras!!.getString("class_name"))) "" else intent.extras!!.getString(
                    "class_name"
                )!!
            name =
                if (TextUtils.isEmpty(intent.extras!!.getString("name"))) "" else intent.extras!!.getString(
                    "name"
                )!!
        }
        init()
    }

    fun init() {
        cpDetailPresenter = CPDetailPresenter(this, this)

        when (class_name) {
            "OTGSChannelPartner" -> {
                llEarnedPercentage.visibility = View.GONE
                llEstablishmentDate.visibility = View.GONE
                llStdOther.visibility = View.GONE
                llBoard.visibility = View.GONE
                llStandard.visibility = View.GONE
                llMedium.visibility = View.GONE
                llSujbectSpecialities.visibility = View.GONE
                llTeachingExp.visibility = View.GONE
                txtTitle.text = getString(R.string.cp_detail)
                cpDetailPresenter!!.loadCpDetailData(
                    sessionManager.getAuthToken(this)!!,
                    cp_id.toInt()
                )
                txtAnalysis.setOnClickListener {
                    startActivity(
                        Intent(this, AnalysisActivity::class.java).putExtra(
                            "cp_id", cp_id
                        )
                    )
                }

                txtPromotion.setOnClickListener {
                    cpDetailPresenter!!.loadPromotionDetails(
                        sessionManager.getAuthToken(this)!!,
                        cp_id.toInt()
                    )
                }
            }
            "OTGS_SP_Network", "FragmentGarageList" -> {
                txtCurrentLevel.visibility = View.GONE
                llEarnedPercentage.visibility = View.VISIBLE
                llStdOther.visibility = View.GONE
                llExpiryDate.visibility = View.GONE
                llGeneration.visibility = View.GONE
                llGender.visibility = View.GONE
                llEstablishmentDate.visibility = View.GONE
                llDOB.visibility = View.GONE
                llBoard.visibility = View.GONE
                llStandard.visibility = View.GONE
                llMedium.visibility = View.GONE
                llSujbectSpecialities.visibility = View.GONE
                llTeachingExp.visibility = View.GONE
                txtTitle.text = getString(R.string.garage_detail)
                txtAnalysis.text = "Services"
                txtAnalysis.setOnClickListener {

                }
                txtPromotion.visibility = View.GONE
                cpDetailPresenter!!.loadGarageDetailData(
                    sessionManager.getAuthToken(this)!!,
                    cp_id,
                    "0"
                )
            }
            "FragmentCpList" -> {
                llEarnedPercentage.visibility = View.GONE
                llStdOther.visibility = View.GONE
                llExpiryDate.visibility = View.GONE
                llGeneration.visibility = View.GONE
                llBoard.visibility = View.GONE
                llStandard.visibility = View.GONE
                llMedium.visibility = View.GONE
                llSujbectSpecialities.visibility = View.GONE
                llTeachingExp.visibility = View.GONE
                txtTitle.text = getString(R.string.cp_detail)
                txtAnalysis.visibility = View.GONE
                txtPromotion.visibility = View.GONE
                cpDetailPresenter!!.loadCpDetailData(
                    sessionManager.getAuthToken(this)!!,
                    cp_id.toInt()
                )
            }
            "FragmentStudent" -> {
                txtCurrentLevel.visibility = View.GONE
                llEarnedPercentage.visibility = View.VISIBLE
                llEstablishmentDate.visibility = View.GONE
                llExpiryDate.visibility = View.GONE
                llGeneration.visibility = View.GONE
                llTeachingExp.visibility = View.GONE
                llSujbectSpecialities.visibility = View.GONE
                llSujbectSpecialities.visibility = View.GONE
                llLandmark.visibility = View.GONE
                llRegDate.visibility = View.GONE
                txtTitle.text = getString(R.string.student_detail)
                txtAnalysis.text = getString(R.string.subscrpition)
                txtPromotion.text = getString(R.string.payment)
                txtPromotion.visibility = View.GONE
                cpDetailPresenter!!.loadStudentDetailData(
                    sessionManager.getAuthToken(this)!!,
                    sessionManager.getUser_ID(this)!!,
                    sessionManager.getUserType(this)!!,
                    cp_id.toInt()
                )
                txtAnalysis.setOnClickListener {
                    startActivity(
                        Intent(this, SubScriptionActivity::class.java).putExtra(
                            "cp_id",
                            cp_id
                        )
                    )
                }
                txtPromotion.setOnClickListener {
                }

            }
            "FragmentClass" -> {
                txtFname.hint = "Class Name"
                txtCurrentLevel.visibility = View.GONE
                llLname.visibility = View.GONE
                llEarnedPercentage.visibility = View.GONE
                llExpiryDate.visibility = View.GONE
                llGeneration.visibility = View.GONE
                llDOB.visibility = View.GONE
                llLandmark.visibility = View.GONE
                llGender.visibility = View.GONE
                llRegNumber.visibility = View.VISIBLE
                llCourses.visibility = View.VISIBLE
                llDesc.visibility = View.VISIBLE
                txtTitle.text = getString(R.string.class_detail)
                txtAnalysis.text = getString(R.string.statistics)
                txtPromotion.visibility = View.GONE
                cpDetailPresenter!!.loadClassDetailData(
                    sessionManager.getAuthToken(this)!!,
                    sessionManager.getUser_ID(this)!!,
                    sessionManager.getUserType(this)!!,
                    cp_id.toInt()
                )
                txtAnalysis.setOnClickListener {
                    type = 1
                    cpDetailPresenter!!.loadstats(
                        sessionManager.getAuthToken(this)!!,
                        sessionManager.getUser_ID(this)!!.toInt(),
                        8
                    )
                }
                rvGallery.visibility = View.VISIBLE
                val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                rvGallery!!.setLayoutManager(layoutManager)
                imagesAdapter = ImagesAdapter(this, files!!)
                rvGallery!!.setAdapter(imagesAdapter)

            }
            "FragmentTeacher" -> {
                txtCurrentLevel.visibility = View.GONE
                llEarnedPercentage.visibility = View.GONE
                llEstablishmentDate.visibility = View.GONE
                llExpiryDate.visibility = View.GONE
                llGeneration.visibility = View.GONE
                llLandmark.visibility = View.GONE
                llDOB.visibility = View.GONE
                llCourses.visibility = View.VISIBLE
                txtTitle.text = getString(R.string.teacher_detail)
                txtAnalysis.text = getString(R.string.statistics)
                txtPromotion.visibility = View.GONE
                cpDetailPresenter!!.loadTeacherDetailData(
                    sessionManager.getAuthToken(this)!!,
                    sessionManager.getUser_ID(this)!!,
                    sessionManager.getUserType(this)!!,
                    cp_id.toInt()
                )
                txtAnalysis.setOnClickListener {
                    type = 2
                    cpDetailPresenter!!.loadstats(
                        sessionManager.getAuthToken(this)!!,
                        sessionManager.getUser_ID(this)!!.toInt(),
                        7
                    )
                }
            }
            "FragmentExpertCareer" -> {
                txtCurrentLevel.visibility = View.GONE
                llEarnedPercentage.visibility = View.GONE
                llEstablishmentDate.visibility = View.GONE
                llExpiryDate.visibility = View.VISIBLE
                llGeneration.visibility = View.GONE
                llDOB.visibility = View.VISIBLE
                llBoard.visibility = View.GONE
                llStandard.visibility = View.GONE
                llLandmark.visibility = View.GONE
                llGender.visibility = View.VISIBLE
                txtTitle.text = getString(R.string.exp_career_detail)
                txtAnalysis.text = getString(R.string.payment)
                txtPromotion.visibility = View.GONE
                txtAnalysis.visibility = View.GONE
                llHeader.visibility = View.GONE
                cpDetailPresenter!!.loadExpertCareerDetailData(
                    sessionManager.getAuthToken(this)!!,
                    sessionManager.getUser_ID(this)!!,
                    sessionManager.getUserType(this)!!,
                    cp_id.toInt()
                )
                txtAnalysis.setOnClickListener {
                }
            }
        }
    }

    override fun showProgressbar() {
        isBackAllowed = false
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        isBackAllowed = true
        progressBar.visibility = View.GONE
    }

    override fun onSuccess(responseModel: Response<CPDetailResponse>) {
        if (responseModel.body() != null) {
            loadImage(
                this,
                true,
                1,
                responseModel.body()!!.imageUrl,
                imgProfile
            )
            if (TextUtils.equals(class_name, "OTGSChannelPartner"))
                txtCurrentLevel.text =
                    "Current Level: " + responseModel.body()!!.currentLevel
            txtCpNumber.text = "C.P Code: " + responseModel.body()!!.channelpartner_No
            edtFname.text = responseModel.body()!!.firstName
            edtLname.text = responseModel.body()!!.lastName
            edtGender.text = responseModel.body()!!.gender
            edtDOB.text = responseModel.body()!!.dob
            edtEmail.text = responseModel.body()!!.email
            edtPhone.text = responseModel.body()!!.contactNo
            edtAddress.text = responseModel.body()!!.address
            edtState.text = responseModel.body()!!.stateName
            edtLandmark.text = responseModel.body()!!.contact_landmark
            edtCity.text = responseModel.body()!!.cityName
            edtPincode.text = responseModel.body()!!.pincode
            edtRegDate.text = responseModel.body()!!.cp_reg_date
            edtExpiryDate.text = responseModel.body()!!.cp_ex_date
            edtGeneration.text = responseModel.body()!!.cp_generation
            edtIntroducerName.text = responseModel.body()!!.introducerName
            edtIntroducerName.setOnClickListener {
                startActivity(
                    Intent(this, DetailActivity::class.java)
                        .putExtra(
                            "cp_id",
                            responseModel.body()!!.cp_introducer_id.toString()
                        )
                        .putExtra("class_name", "OTGSChannelPartner")
                )
            }
        }
    }

    override fun onSuccessGarage(responseModel: Response<ServiceProviderResponse>) {
        if (responseModel.body() != null) {
//            loadImage(
//                this,
//                true,
//                1,
//                responseModel.body()!!.garageInformation.profile_image,
//                imgProfile
//            )
            txtCpNumber.text =
                "Garage Code: " + responseModel.body()!!.garageInformation.service_provider_uniqueNumber
            edtFname.text = responseModel.body()!!.garageInformation.owner_first_name
            edtLname.text = responseModel.body()!!.garageInformation.owner_last_name
//            edtGender.text = responseModel.body()!!.garageInformation.ge
            edtDOB.text = responseModel.body()!!.garageInformation.date_of_establish
            edtEmail.text = responseModel.body()!!.garageInformation.contact_email_id
            edtPhone.text = responseModel.body()!!.garageInformation.mobile_number
            edtAddress.text = responseModel.body()!!.garageInformation.contact_address
            edtState.text = responseModel.body()!!.garageInformation.contact_state
            edtLandmark.text = responseModel.body()!!.garageInformation.contact_landmark
            edtCity.text = responseModel.body()!!.garageInformation.contact_city
            edtPincode.text = responseModel.body()!!.garageInformation.contact_pincode
            edtRegDate.text = responseModel.body()!!.garageInformation.date_of_establish
//            edtExpiryDate.text = responseModel.body()!!.garageInformation.cp_ex_date
//            edtGeneration.text = responseModel.body()!!.garageInformation.cp_geleration
            edtIntroducerName.text = responseModel.body()!!.garageInformation.channelPartnerName
            edtIntroducerName.setOnClickListener {
                startActivity(
                    Intent(this, DetailActivity::class.java)
                        .putExtra(
                            "cp_id",
                            responseModel.body()!!.garageInformation.channelPartnerId.toString()
                        )
                        .putExtra("class_name", "OTGSChannelPartner")
                )
            }
            txtAnalysis.setOnClickListener {
                showservicesDialog(responseModel.body()!!.packageDetails)
            }

        }
    }

    override fun onSuccess1(responseModel: Response<ClassDetailResponse>) {
        if (responseModel.body() != null) {
            loadImage(
                this,
                true,
                1,
                responseModel.body()!!.details.logo_path,
                imgProfile
            )
            if (TextUtils.equals(class_name, "FragmentTeacher")) {
                txtCpNumber.text = "Teacher Code: " + responseModel.body()!!.details.unique_no

            } else if (TextUtils.equals(class_name, "FragmentExpertCareer")) {
                txtCpNumber.text = "Career Expert Code: " + responseModel.body()!!.details.unique_no
            } else {
                txtCpNumber.text = "Class Code: " + responseModel.body()!!.details.unique_no
            }
            edtFname.text = responseModel.body()!!.details.name
            edtLname.text = responseModel.body()!!.details.name
            edtEmail.text = responseModel.body()!!.details.email
            edtPhone.text = responseModel.body()!!.details.contact
            edtGender.text = responseModel.body()!!.details.gender
            edtAddress.text = responseModel.body()!!.details.address
            edtState.text = responseModel.body()!!.details.state
            edtCity.text = responseModel.body()!!.details.city_id
            edtPincode.text = responseModel.body()!!.details.pincode
            edtRegDate.text = responseModel.body()!!.details.establishment_date
            edtIntroducerName.text = responseModel.body()!!.details.introducer_name
            edtRegistrationNumber.text = responseModel.body()!!.details.reg_number
            txtDesc.text = responseModel.body()!!.details.description

            edtStdOther.text = responseModel.body()!!.details.std_other
            edtEstablishmentDate.text = responseModel.body()!!.details.establishment_date
            edtTeachingExp.text = responseModel.body()!!.details.teaching_exp

            var board = StringBuffer()
            var standard = StringBuffer()
            var medium = StringBuffer()
            var courses = StringBuffer()
            var subjetc_speciality = StringBuffer()

            if (!TextUtils.equals(class_name, "FragmentExpertCareer")) {
                if (responseModel.body()!!.details.board_details != null || responseModel.body()!!.details.board_details.size != 0) {
                    for (i in responseModel.body()!!.details.board_details.indices) {
                        board.append(responseModel.body()!!.details.board_details.get(i).board)
                        board.append(", ")
                    }
                    edtBoard.text = board.toString()
                }
                if (responseModel.body()!!.details.standrad_details != null || responseModel.body()!!.details.standrad_details.size != 0) {
                    for (i in responseModel.body()!!.details.standrad_details.indices) {
                        standard.append(responseModel.body()!!.details.standrad_details.get(i).standard)
                        standard.append(", ")
                    }
                    edtStandard.text = standard
                }
                if (responseModel.body()!!.details.course_details != null || responseModel.body()!!.details.course_details.size != 0) {
                    for (i in responseModel.body()!!.details.course_details.indices) {
                        courses.append(responseModel.body()!!.details.course_details.get(i).course_name)
                        courses.append(", ")
                    }
                    edtCoures.text = courses
                }
            }
            if (responseModel.body()!!.details.medium_details != null || responseModel.body()!!.details.medium_details.size != 0) {
                for (i in responseModel.body()!!.details.medium_details.indices) {
                    medium.append(responseModel.body()!!.details.medium_details.get(i).medium)
                    medium.append(", ")
                }
                edtMedium.text = medium
            }
            if (responseModel.body()!!.details.subjetc_speciality != null || responseModel.body()!!.details.subjetc_speciality.size != 0) {
                for (i in responseModel.body()!!.details.subjetc_speciality.indices) {
                    subjetc_speciality.append(
                        responseModel.body()!!.details.subjetc_speciality.get(
                            i
                        ).speciality
                    )
                    subjetc_speciality.append(", ")
                }
                edtSubSpecaility.text = subjetc_speciality
            }

            if (responseModel.body()!!.details.gallary_images.isNotEmpty()) {
                files.clear()
                for (i in responseModel.body()!!.details.gallary_images.indices) {
                    files.add(Uri.parse(responseModel.body()!!.details.gallary_images[i].image))
                    imagesAdapter!!.notifyDataSetChanged()
                }
            }
            edtIntroducerName.setOnClickListener {
                startActivity(
                    Intent(this, DetailActivity::class.java)
                        .putExtra("cp_id", responseModel.body()!!.details.created_by.toString())
                        .putExtra("class_name", "OTGSChannelPartner")
                )
            }

        }
    }

    override fun onSuccessStudent(responseModel: Response<StudentResponse>) {
        if (responseModel.body() != null) {
            loadImage(
                this,
                true,
                1,
                responseModel.body()!!.details.profile_photo,
                imgProfile
            )
            txtCpNumber.text = "Student Code: " + responseModel.body()!!.details.unique_no
            edtFname.text = responseModel.body()!!.details.firstName
            edtLname.text = responseModel.body()!!.details.lastName
            edtEmail.text = responseModel.body()!!.details.contact_email_id
            edtPhone.text = responseModel.body()!!.details.mobile_number
            edtAddress.text = responseModel.body()!!.details.contact_address
            edtState.text = responseModel.body()!!.details.contact_state
            edtGender.text = responseModel.body()!!.details.gender
            edtCity.text = responseModel.body()!!.details.contact_city
            edtPincode.text = responseModel.body()!!.details.contact_pincode
            edtEstablishmentDate.text = responseModel.body()!!.details.date_of_birth
            edtIntroducerName.text = responseModel.body()!!.details.introducer_name
            edtStdOther.text = responseModel.body()!!.details.std_id
            edtDOB.text = responseModel.body()!!.details.date_of_birth
            edtBoard.text = responseModel.body()!!.details.board_id
            edtMedium.text = responseModel.body()!!.details.medium_id
            edtEarnedPercentage.text = responseModel.body()!!.details.total_earned
            edtIntroducerName.setOnClickListener {
                startActivity(
                    Intent(this, DetailActivity::class.java)
                        .putExtra("cp_id", responseModel.body()!!.details.created_by.toString())
                        .putExtra("class_name", "OTGSChannelPartner")
                )
            }

//            edtStandard.text = responseModel.body()!!.details.date_of_birth

//            edtSubSpecaility.text = responseModel.body()!!.details.date_of_birth
        }
    }

    override fun onSuccess2(responseModel: Response<PromotionDetailResponse>) {
        if (responseModel.body() != null) {
            showDialog(responseModel.body()!!)
        }
    }

    override fun onSuccessStatics(responseModel: Response<StatisticsResponse>) {
        if (responseModel.body() != null) {
            showStatistics(responseModel.body()!!)
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

    private fun showDialog(cpDetails: PromotionDetailResponse) {
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_promotion, null)
        val dialog = BottomSheetDialog(this)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogView)
        val txtCurrentLevel = dialogView.findViewById(R.id.txtCurrentLevel) as TextView
        val txtNextLevel = dialogView.findViewById(R.id.txtNextLevel) as TextView
        val txtTarget = dialogView.findViewById(R.id.txtTarget) as TextView
        val txtAchieved = dialogView.findViewById(R.id.txtAchieved) as TextView
        val txtPending = dialogView.findViewById(R.id.txtPending) as TextView

        txtCurrentLevel.text = cpDetails.currentLevel.toString()
        txtNextLevel.text = cpDetails.nextLevel.toString()
        txtTarget.text = cpDetails.target.toString()
        txtAchieved.text = cpDetails.achieved.toString()
        txtPending.text = cpDetails.pending.toString()

        dialog.show()

    }

    private fun showservicesDialog(dataList: ArrayList<PackageDetail>) {
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_recyclerview, null)
        val dialog = BottomSheetDialog(this)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogView)
        val rvDialog = dialogView.findViewById(R.id.rvDialog) as RecyclerView
        val btnDone = dialogView.findViewById(R.id.btnDone) as Button
        btnDone.visibility = View.GONE

        val layoutManager = LinearLayoutManager(this)
        rvDialog.setLayoutManager(layoutManager)
        val adapter = ServicesAdapter(
            this,
            dataList,
            { dataList -> onClick(dataList) })

        rvDialog.setAdapter(adapter)

        dialog.show()

    }

    private fun onClick(dataList: PackageDetail) {

    }

    var type = 0
    private fun showStatistics(data: StatisticsResponse) {
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_stats, null)
        val dialog = BottomSheetDialog(this)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogView)
        val txtSubscribed = dialogView.findViewById(R.id.txtSubscribed) as TextView
        val txtSubscribedNo = dialogView.findViewById(R.id.txtSubscribedNo) as TextView
        val txtSubscribedAmt = dialogView.findViewById(R.id.txtSubscribedAmt) as TextView
        val txtSelfStudent = dialogView.findViewById(R.id.txtSelfStudent) as TextView
        val txtSelfStudentNo = dialogView.findViewById(R.id.txtSelfStudentNo) as TextView
        val txtSelfStudentAmt = dialogView.findViewById(R.id.txtSelfStudentAmt) as TextView
        val txtStudentOutside = dialogView.findViewById(R.id.txtStudentOutside) as TextView
        val txtStudentOutsideNo = dialogView.findViewById(R.id.txtStudentOutsideNo) as TextView
        val txtStudentOutsideAmt = dialogView.findViewById(R.id.txtStudentOutsideAmt) as TextView
        val txtRegisteredBy = dialogView.findViewById(R.id.txtRegisteredBy) as TextView
        val txtRegisteredByNo = dialogView.findViewById(R.id.txtRegisteredByNo) as TextView
        val txtRegisteredByAmt = dialogView.findViewById(R.id.txtRegisteredByAmt) as TextView
        val txtRegisteredByOther = dialogView.findViewById(R.id.txtRegisteredByOther) as TextView
        val txtTotalEarning = dialogView.findViewById(R.id.txtTotalEarning) as TextView
        val txtRegisteredByOtherNo =
            dialogView.findViewById(R.id.txtRegisteredByOtherNo) as TextView
        val txtRegisteredByOtherAmt =
            dialogView.findViewById(R.id.txtRegisteredByOtherAmt) as TextView

        if (type != 1)
            txtSubscribed.text = "Subscribed by  Teacher"
        txtSubscribedNo.text = data.class_stud_subscribed.toString()
        txtSubscribedAmt.text = data.amount_earned_class_stud_subscribed
        if (type != 1)
            txtSelfStudent.text = "Self-Students"
        txtSelfStudentNo.text = data.class_self_stud.toString()
        txtSelfStudentAmt.text = data.amount_earned_class_self_stud
        if (type != 1)
            txtStudentOutside.text = "Students outside  Teacher"
        txtStudentOutsideNo.text = data.class_stud_outside.toString()
        txtStudentOutsideAmt.text = data.amount_earned_class_stud_outside
        txtRegisteredBy.text = "Registered by" + sessionManager.getName(this)
        txtRegisteredByNo.text = data.class_stud_regi_by_cp.toString()
        txtRegisteredByAmt.text = data.amount_earned_class_stud_regi_by_cp
//            txtRegisteredByOther.text = data.royalty_income.toString()
        txtRegisteredByOtherNo.text = data.class_stud_regi_by_other_cp.toString()
        txtRegisteredByOtherAmt.text = data.amount_earned_class_stud_regi_by_other_cp
        txtTotalEarning.text = "Total Earning In June 2020: " + data.total_earned

        dialog.show()

    }

    override fun onBackPressed() {
        if (isBackAllowed!!) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cpDetailPresenter!!.onStop()
    }
}
