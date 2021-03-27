package com.channelpartner.presenter


import android.content.Context
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.network.ApiClient
import com.channelpartner.utils.NetWorkConection
import com.channelpartner.view.OTGSChannelPartnerView
import com.channelpartner.view.GarageRegView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class GarageRegistrationPresenter : GarageRegView.MainPresenter {

    var context: Context? = null
    var mainView: GarageRegView.MainView? = null

    @NonNull
    var disposable: Disposable? = null

    constructor(context: Context?, mainView: GarageRegView.MainView?) {
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

    override fun loadData(
        token: String,
        mobileNumber: String
    ) {
        mainView!!.showProgressbar(4)

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .senOTP(
                    token,
                    mobileNumber
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar(4)
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