package com.channelpartner.view

import com.channelpartner.model.response.AutoHubResponse
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response
import retrofit2.http.Header

interface AutoHubView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<AutoHubResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            user_type: String
        )

        fun onStop()
    }
}