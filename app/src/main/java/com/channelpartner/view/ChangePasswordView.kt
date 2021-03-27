package com.channelpartner.view

import com.channelpartner.model.response.AutoHubResponse
import com.google.gson.JsonObject
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface ChangePasswordView {

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
            new_password: String,
            old_password: String
        )

        fun onStop()
    }
}