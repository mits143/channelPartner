package com.channelpartner.presenter


import android.content.Context
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.network.ApiClient
import com.channelpartner.utils.NetWorkConection
import com.channelpartner.view.OTGSChannelPartnerView
import com.channelpartner.view.KYCView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException


class KycPresenter : KYCView.MainPresenter {

    var context: Context? = null
    var mainView: KYCView.MainView? = null

    @NonNull
    var disposable: Disposable? = null

    constructor(context: Context?, mainView: KYCView.MainView?) {
        this.context = context
        this.mainView = mainView
    }

    override fun loadData(
        token: String,
        user_id: Int,
        user_type: Int
    ) {
        mainView!!.showProgressbar(1)

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postKyc(
                    token,
                    user_id,
                    user_type
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar(1)
                    val responseCode = listResponse.code()
                    when (responseCode) {
                         200, 201, 202, 204 -> {
                            mainView!!.onSuccess(listResponse)
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

    override fun loadData1(
        token: String,
        profile: MultipartBody.Part,
        user_id: RequestBody,
        user_type: RequestBody,
        pan: MultipartBody.Part,
        aadhar_card: MultipartBody.Part,
        driving_license: MultipartBody.Part,
        passport: MultipartBody.Part,
        voter_id: MultipartBody.Part,
        electricity_bill: MultipartBody.Part
    ) {
        mainView!!.showProgressbar(2)

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postImage(
                    token,
                    profile,
                    user_id,
                    user_type,
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
                    mainView!!.hideProgressbar(2)
                    val responseCode = listResponse.code()
                    when (responseCode) {
                         200, 201, 202, 204 -> {
                            mainView!!.onSuccess1(listResponse)
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

    override fun onStop() {
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}