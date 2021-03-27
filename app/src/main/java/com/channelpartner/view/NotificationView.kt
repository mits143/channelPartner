package com.channelpartner.view

import com.channelpartner.model.response.NotificationResponse
import retrofit2.Response

interface NotificationView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<NotificationResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            notification_type: String,
            app_code: String,
            page_number: String,
            user_id: Int,
            user_type: Int
        )

        fun loadData1(
            token: String,
            notification_type: String,
            app_code: String,
            page_number: String,
            user_id: Int,
            user_type: Int
        )

        fun onStop()
    }
}