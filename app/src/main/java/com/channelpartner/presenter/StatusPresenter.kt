package com.channelpartner.presenter


import android.content.Context
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.network.ApiClient
import com.channelpartner.utils.NetWorkConection
import com.channelpartner.view.ProfileDetailView
import com.channelpartner.view.StatusView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class StatusPresenter : StatusView.MainPresenter {

    var context: Context? = null
    var mainView: StatusView.MainView? = null

    @NonNull
    var disposable: Disposable? = null

    constructor(context: Context?, mainView: StatusView.MainView?) {
        this.context = context
        this.mainView = mainView
    }

    override fun loadPromotionDetails(
        token: String,
        cpid: Int
    ) {
        mainView!!.showProgressbar()

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postCpPromotionDetails(
                    token,
                    cpid
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar()
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
                    mainView!!.hideProgressbar()
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
            mainView!!.hideProgressbar()
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun loadCpNetworkServiceCount(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ) {
        mainView!!.showProgressbar()

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postCpNetworkServiceCount(
                    token,
                    page_number,
                    cpid,
                    user_id,
                    user_type,
                    search_para,
                    generation_id,
                    level_id
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar()
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
                    mainView!!.hideProgressbar()
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
            mainView!!.hideProgressbar()
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