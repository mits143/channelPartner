package com.channelpartner.view

import com.channelpartner.model.response.RenewalDetailResponse
import com.google.gson.JsonObject
import retrofit2.Response

interface RenewalDetailView {

    interface MainView {

        fun showProgressbar(int: Int)
        fun hideProgressbar(int: Int)
        fun onSuccess(responseModel: Response<RenewalDetailResponse>)
        fun onSuccess1(responseModel: Response<JsonObject>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            user_id: Int,
            user_type: Int,
            history_id: Int
        )
        fun loadDataApprove(
            token: String,
            user_id: Int,
            user_type: Int,
            history_id: Int
        )
        fun loadDataReject(
            token: String,
            user_id: Int,
            user_type: Int,
            history_id: Int
        )

        fun onStop()
    }
}