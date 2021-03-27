package com.channelpartner.view

import com.channelpartner.model.response.PackageRerquestResponse
import retrofit2.Response

interface RenewalView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<PackageRerquestResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            user_id: Int,
            user_type: Int
        )

        fun loadData(
            token: String,
            end_date: String,
            start_date: String,
            status: Int,
            user_id: Int,
            user_type: Int
        )

        fun onStop()
    }
}