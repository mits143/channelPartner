package com.channelpartner.view

import com.channelpartner.model.response.AutoHubResponse
import com.channelpartner.model.response.FAQResponse
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface FAQView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<FAQResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String
        )

        fun onStop()
    }
}