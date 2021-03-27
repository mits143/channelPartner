package com.channelpartner.presenter


import android.content.Context
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.network.ApiClient
import com.channelpartner.utils.NetWorkConection
import com.channelpartner.view.CPRegistrationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException


class CPRegistrationPresenter : CPRegistrationView.MainPresenter {

    var context: Context? = null
    var mainView: CPRegistrationView.MainView? = null

    @NonNull
    var disposable: Disposable? = null

    constructor(context: Context?, mainView: CPRegistrationView.MainView?) {
        this.context = context
        this.mainView = mainView
    }

    override fun loadStates(
        token: String
    ) {
        mainView!!.showProgressbar(1)

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .getAllStates(
                    token
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar(1)
                    val responseCode = listResponse.code()
                    when (responseCode) {
                        200, 201, 202, 204 -> {
                            mainView!!.onSuccessgetStates(listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar(1)
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar(1)
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun loadCities(
        token: String,
        state_id: Int
    ) {
        mainView!!.showProgressbar(2)

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .getCities(
                    token,
                    state_id
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar(2)
                    val responseCode = listResponse.code()
                    when (responseCode) {
                        200, 201, 202, 204 -> {
                            mainView!!.onSuccessgetCities(listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar(2)
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar(2)
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun loadPincodes(
        token: String,
        city_id: Int
    ) {
        mainView!!.showProgressbar(3)

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .getPincodes(
                    token,
                    city_id
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar(3)
                    val responseCode = listResponse.code()
                    when (responseCode) {
                        200, 201, 202, 204 -> {
                            mainView!!.onSuccessgetPincodes(listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar(3)
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar(3)
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun postCPRegisterData(
        token: String,
        file: MultipartBody.Part,
        data: RequestBody
    ) {
        mainView!!.showProgressbar(4)

        if (NetWorkConection.isNEtworkConnected(context!!)) {
            disposable = ApiClient.instance
                .postCPRegistration(
                    token,
                    file,
                    data
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar(4)
                    when (val responseCode = listResponse.code()) {
                        200, 201, 202, 204 -> {
                            mainView!!.onSuccess(1, listResponse)
                        }
                        400, 401, 409, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar(4)
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar(4)
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun CPReg(
        token: String,
        account_holder_name: String,
        account_number: String,
        bank_name: String,
        branch_name: String,
        channelpartner_id: String,
        contact_address: String,
        contact_city: String,
        contact_email_id: String,
        contact_landline: String,
        contact_landmark: String,
        contact_mobile: String,
        contact_pincode: String,
        contact_state: String,
        date_of_birth: String,
        first_name: String,
        ifsc_code: String,
        last_name: String,
        pancard_number: String,
        gender: Int
    ) {
        mainView!!.showProgressbar(4)

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postRegChannelPartner(
                    token,
                    channelpartner_id,
                    contact_address,
                    contact_city,
                    contact_email_id,
                    contact_landline,
                    contact_mobile,
                    contact_pincode,
                    contact_state,
                    date_of_birth,
                    first_name,
                    last_name,
                    gender,
                    1,
                    1
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar(4)
                    val responseCode = listResponse.code()
                    when (responseCode) {
                        200, 201, 202, 204 -> {
                            mainView!!.onSuccess(1, listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar(4)
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar(4)
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun commission(
        token: String,
        date_of_joining: String,
        name: String,
        paid_amount: String,
        parent_id: Int,
        shop_type: String,
        user_id: Int,
        user_type: Int
    ) {
        mainView!!.showProgressbar(9)

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postCommission(
                    token,
                    date_of_joining,
                    name,
                    paid_amount,
                    parent_id,
                    shop_type,
                    user_id,
                    user_type
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar(9)
                    val responseCode = listResponse.code()
                    when (responseCode) {
                        200, 201, 202, 204 -> {
                            mainView!!.onSuccess(2, listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar(9)
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar(9)
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun CPImageUpload(
        token: String,
        profile: MultipartBody.Part,
        user_id: RequestBody,
        user_type: RequestBody,
        data: RequestBody,
        pan: MultipartBody.Part,
        aadhar_card: MultipartBody.Part,
        driving_license: MultipartBody.Part,
        passport: MultipartBody.Part,
        voter_id: MultipartBody.Part,
        electricity_bill: MultipartBody.Part
    ) {
        mainView!!.showProgressbar(4)

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postImage1(
                    token,
                    profile,
                    user_id,
                    user_type,
                    data,
                    pan,
                    aadhar_card,
                    driving_license,
                    passport,
                    voter_id,
                    electricity_bill
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar(4)
                    val responseCode = listResponse.code()
                    when (responseCode) {
                        200, 201, 202, 204 -> {
                            mainView!!.onSuccess(3, listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar(4)
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar(4)
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun onStop() {
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}