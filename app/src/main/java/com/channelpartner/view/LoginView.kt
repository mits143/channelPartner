package com.channelpartner.view

import com.channelpartner.model.response.LoginResponse
import retrofit2.Response

interface LoginView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<LoginResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            Email: String,
            Password: String,
            DeviceId: String,
            FCMId: String
        )

        fun onStop()
    }
}