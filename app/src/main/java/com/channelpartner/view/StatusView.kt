package com.channelpartner.view

import com.channelpartner.model.response.*
import com.google.gson.JsonObject
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface StatusView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<PromotionDetailResponse>)
        fun onSuccess1(responseModel: Response<NetworkServiceCountResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {

        fun loadPromotionDetails(
            token: String,
            id: Int
        )

        fun loadCpNetworkServiceCount(
            token: String,
            page_number: Int,
            cpid: String,
            user_id: Int,
            user_type: Int,
            search_para: String,
            generation_id: Int,
            level_id: Int
        )

        fun onStop()
    }
}