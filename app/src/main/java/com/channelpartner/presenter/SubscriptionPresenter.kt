package com.channelpartner.presenter


import android.content.Context
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.network.ApiClient
import com.channelpartner.utils.NetWorkConection
import com.channelpartner.view.OTGSChannelPartnerView
import com.channelpartner.view.SubscripitionView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class SubscriptionPresenter : SubscripitionView.MainPresenter {

    var context: Context? = null
    var mainView: SubscripitionView.MainView? = null

    @NonNull
    var disposable: Disposable? = null

    constructor(context: Context?, mainView: SubscripitionView.MainView?) {
        this.context = context
        this.mainView = mainView
    }

    override fun loadData(
        token: String,
        stud_id: Int,
        user_id: Int,
        user_type: Int
    ) {
        mainView!!.showProgressbar()

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .getStudentSubscription(
                    token,
                    stud_id,
                    user_id,
                    user_type
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

    override fun onStop() {
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}