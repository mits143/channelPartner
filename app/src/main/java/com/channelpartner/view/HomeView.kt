package com.channelpartner.view

import com.channelpartner.model.response.AutoHubResponse
import retrofit2.Response

interface HomeView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<AutoHubResponse>)
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