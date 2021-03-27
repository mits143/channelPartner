package com.channelpartner.view

import com.channelpartner.model.response.AutoHubResponse
import com.channelpartner.model.response.ContactUsResponse
import com.google.gson.JsonObject
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface ContactView {

    interface MainView {

        fun showProgressbar(type: Int)
        fun hideProgressbar(type: Int)
        fun onSuccess(responseModel: Response<ContactUsResponse>)
        fun onSuccess1(responseModel: Response<JsonObject>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String
        )

        fun submitData(
            token: String,
            user_id: String,
            user_type: String,
            query: String,
            category_id: String
        )

        fun onStop()
    }
}