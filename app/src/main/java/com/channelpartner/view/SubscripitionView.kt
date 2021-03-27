package com.channelpartner.view

import com.channelpartner.model.response.SubscrpitionResponse
import retrofit2.Response

interface SubscripitionView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<SubscrpitionResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            stud_id: Int,
            user_id: Int,
            user_type: Int
        )

        fun onStop()
    }
}