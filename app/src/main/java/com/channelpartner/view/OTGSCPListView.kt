package com.channelpartner.view

import com.channelpartner.model.response.*
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface OTGSCPListView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccessGeneration(responseModel: Response<GenerationResponse>)
        fun onSuccessLevel(responseModel: Response<LevelResponseX>)
        fun onSuccess(value: Int, responseModel: Response<OTGSChannelPartnerResponse>)
        fun onError(value: Int, errorCode: Int)
        fun onError(value: Int, throwable: Throwable)
    }

    interface MainPresenter {
        fun loadGeneration(
            token: String
        )

        fun loadLevel(
            token: String
        )

        fun loadDirectData(
            token: String,
            page_number: Int,
            cpid: String,
            user_id: Int,
            user_type: Int,
            search_para: String,
            generation_id: Int,
            level_id: Int
        )

        fun loadIndirectData(
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