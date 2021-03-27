//package com.channelpartner.presenter
//
//
//import android.content.Context
//import android.widget.Toast
//import com.channelpartner.R
//import com.channelpartner.model.request.*
//import com.channelpartner.network.ApiClient
//import com.channelpartner.utils.NetWorkConection
//import com.channelpartner.view.StudentRegView
//import com.channelpartner.view.LoginView
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.annotations.NonNull
//import io.reactivex.disposables.Disposable
//import io.reactivex.schedulers.Schedulers
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import retrofit2.HttpException
//
//
//class StudentRegPresenter : StudentRegView.MainPresenter {
//
//    var context: Context? = null
//    var mainView: StudentRegView.MainView? = null
//
//    @NonNull
//    var disposable: Disposable? = null
//
//    constructor(context: Context?, mainView: StudentRegView.MainView?) {
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
//        city_id: Int
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
//        amount: String,
//        board_id: Int,
//        contact_address: String,
//        contact_city: Int,
//        contact_email_id: String,
//        contact_pincode: Int,
//        contact_state: Int,
//        created_by: Int,
//        date_of_birth: String,
//        first_name: String,
//        gender: Int,
//        gst: String,
//        internet_handling_cost: String,
//        last_name: String,
//        medium_id: Int,
//        mobile_number: String,
//        std_id: Int,
//        total: String
//    ) {
//        mainView!!.showProgressbar(9)
//
//        if (NetWorkConection.isNEtworkConnected(context!!)) {
//
//            disposable = ApiClient.instance
//                .postRegStudent(
//                    token,
//                    amount,
//                    board_id,
//                    contact_address,
//                    contact_city,
//                    contact_email_id,
//                    contact_pincode,
//                    contact_state,
//                    created_by,
//                    date_of_birth,
//                    first_name,
//                    gender,
//                    gst,
//                    internet_handling_cost,
//                    last_name,
//                    medium_id,
//                    mobile_number,
//                    std_id,
//                    total
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