package com.channelpartner.presenter


import android.content.Context
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.network.ApiClient
import com.channelpartner.utils.NetWorkConection
import com.channelpartner.view.OTGSChannelPartnerView
import com.channelpartner.view.OTGSSPNetworkView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class OTGSSPNetworkPresenter : OTGSSPNetworkView.MainPresenter {

    var context: Context? = null
    var mainView: OTGSSPNetworkView.MainView? = null

    @NonNull
    var disposable: Disposable? = null

    constructor(context: Context?, mainView: OTGSSPNetworkView.MainView?) {
        this.context = context
        this.mainView = mainView
    }

    override fun loadData(
        token: String,
        user_type: String
    ) {
        mainView!!.showProgressbar()

        if (NetWorkConection.isNEtworkConnected(context!!)) {
            disposable = ApiClient.instance
                .getAllServiceMasterList(
                    token,
                    user_type
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar()
                    val responseCode = listResponse.code()
                    when (responseCode) {
                        200, 201, 202, 204 -> {
                            mainView!!.onSuccessServices(listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(3, responseCode)
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
                    mainView!!.onError(3, error)
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

    override fun loadDirectData(
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
                .postDirectGarageList(
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
                            mainView!!.onSuccess(1, listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(1, responseCode)
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
                    mainView!!.onError(1, error)
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

    override fun loadIndirectData(
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
                .postIndirectGarageList(
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
                            mainView!!.onSuccess(2, listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(2, responseCode)
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
                    mainView!!.onError(2, error)
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