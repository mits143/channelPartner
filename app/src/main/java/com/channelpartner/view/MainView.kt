package com.channelpartner.view

import com.channelpartner.model.response.*
import com.google.gson.JsonObject
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface MainView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<JsonObject>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            mobileNumber: String
        )

        fun onStop()
    }
}