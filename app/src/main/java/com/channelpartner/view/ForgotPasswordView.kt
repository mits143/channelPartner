package com.channelpartner.view

import com.google.gson.JsonObject
import retrofit2.Response

interface ForgotPasswordView {

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
            Email: String
        )

        fun onStop()
    }
}