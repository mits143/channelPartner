//package com.channelpartner.presenter
//
//
//import android.content.Context
//import android.widget.Toast
//import com.channelpartner.R
//import com.channelpartner.model.request.*
//import com.channelpartner.network.ApiClient
//import com.channelpartner.utils.NetWorkConection
//import com.channelpartner.view.TeacherRegView
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.annotations.NonNull
//import io.reactivex.disposables.Disposable
//import io.reactivex.schedulers.Schedulers
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import retrofit2.HttpException
//
//
//class TeacherRegPresenter : TeacherRegView.MainPresenter {
//
//    var context: Context? = null
//    var mainView: TeacherRegView.MainView? = null
//
//    @NonNull
//    var disposable: Disposable? = null
//
//    constructor(context: Context?, mainView: TeacherRegView.MainView?) {
//        this.context = context
//        this.mainView = mainView
//    }
//
//    override fun loadStates(
//        token: String
//    ) {
//        mainView!!.showProgressbar(1)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getAllStates(
//                    token
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(1)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessgetStates(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(1)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(1)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadCities(
//        token: String,
//        state_id: Int
//    ) {
//        mainView!!.showProgressbar(2)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getCities(
//                    token,
//                    state_id
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(2)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessgetCities(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(2)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(2)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadPincodes(
//        token: String,
//        city_id: String
//    ) {
//        mainView!!.showProgressbar(3)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getPincodes(
//                    token,
//                    city_id
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(3)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessgetPincodes(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(3)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(3)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadBoardDataNew(
//        token: String
//    ) {
//        mainView!!.showProgressbar(4)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getAllBoardStandards(
//                    token
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(4)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessBoard1(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(4)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(4)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadBoardData(
//        token: String
//    ) {
//        mainView!!.showProgressbar(4)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getAllBoard(
//                    token
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(4)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessBoard(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(4)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(4)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadStandardData(
//        token: String
//    ) {
//        mainView!!.showProgressbar(5)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getAllStandards(
//                    token
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(5)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessStandard(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(5)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(5)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadCoursesData(
//        token: String
//    ) {
//        mainView!!.showProgressbar(5)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getAllCourses(
//                    token
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(5)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessCourses(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(5)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(5)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadMediumData(
//        token: String
//    ) {
//        mainView!!.showProgressbar(6)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getAllMediums(
//                    token
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(6)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessMedium(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(6)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(6)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadCategoryData(
//        token: String
//    ) {
//        mainView!!.showProgressbar(7)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getAllCategory(
//                    token
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(7)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessCategory(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(7)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(7)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadSubSpecialitiesData(
//        token: String
//    ) {
//        mainView!!.showProgressbar(8)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .getAllSubjectSpecialities(
//                    token
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(8)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccessSubSpecialities(listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(8)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(8)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun loadData(
//        token: String,
//        address: String,
//        alternate_contact: String,
//        course_details: List<CourseDetail>,
//        board_details: List<BoardDetail>,
//        category_details: List<CategoryDetail>,
//        state_id: Int,
//        city_id: Int,
//        contact: String,
//        created_by: Int,
//        description: String,
//        email: String,
//        establishment_date: String,
//        gst: String,
//        internet_handling_cost: String,
//        last_name: String,
//        medium_details: List<MediumDetail>,
//        name: String,
//        other_speciality: String,
//        package_details: List<PackageDetail>,
//        pincode: Int,
//        standrad_details: List<StandradDetail>,
//        std_other: String,
//        subjetc_speciality: List<SubjetcSpeciality>,
//        teaching_exp: String,
//        total: String,
//        amount: String,
//        gender: Int
//    ) {
//        mainView!!.showProgressbar(9)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .postTeacherRegistration(
//                    token,
//                    "",
//                    address,
//                    alternate_contact,
//                    course_details,
//                    board_details,
//                    category_details,
//                    state_id,
//                    city_id,
//                    contact,
//                    created_by,
//                    description,
//                    email,
//                    establishment_date,
//                    gst,
//                    internet_handling_cost,
//                    last_name,
//                    medium_details,
//                    name,
//                    other_speciality,
//                    package_details,
//                    pincode,
//                    standrad_details,
//                    std_other,
//                    subjetc_speciality,
//                    teaching_exp,
//                    total,
//                    amount,
//                    gender
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(9)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccess(1, listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(9)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(9)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun commission(
//        token: String,
//        date_of_joining: String,
//        name: String,
//        paid_amount: String,
//        parent_id: Int,
//        shop_type: String,
//        user_id: Int,
//        user_type: Int
//    ) {
//        mainView!!.showProgressbar(9)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .postCommission(
//                    token,
//                    date_of_joining,
//                    name,
//                    paid_amount,
//                    parent_id,
//                    shop_type,
//                    user_id,
//                    user_type
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(9)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccess(2, listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(9)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(9)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun CPImageUploads(
//        token: String,
//        profile: MultipartBody.Part,
//        user_id: RequestBody,
//        user_type: RequestBody,
//        data: RequestBody,
//        images: ArrayList<MultipartBody.Part>
//    ) {
//        mainView!!.showProgressbar(9)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .postImages(
//                    token,
//                    profile,
//                    user_id,
//                    user_type,
//                    data,
//                    images
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listResponse ->
//                    mainView!!.hideProgressbar(9)
//                    val responseCode = listResponse.code()
//                    when (responseCode) {
//                        200, 201, 202, 204 -> {
//                            mainView!!.onSuccess(3, listResponse)
//                        }
//                        400, 401, 500 -> {
//                            mainView!!.onError(responseCode)
//                        }
//                    }
//                }, { error ->
//                    mainView!!.hideProgressbar(9)
//                    if (error is HttpException) {
//                        val response = (error as HttpException).response()
//                        when (response!!.code()) {
//                            //Responce Code
//                        }
//                    } else {
//                        //Handle Other Errors
//                    }
//                    mainView!!.onError(error)
//                })
//        } else {
//            mainView!!.hideProgressbar(9)
//            Toast.makeText(
//                context!!,
//                context!!.getString(R.string.no_internet_connection),
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
//
//    override fun onStop() {
//        if (disposable != null) {
//            disposable!!.dispose()
//        }
//    }
//}