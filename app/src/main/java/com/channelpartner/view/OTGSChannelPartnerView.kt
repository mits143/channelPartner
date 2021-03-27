package com.channelpartner.view

import com.channelpartner.model.response.*
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface OTGSChannelPartnerView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccessGeneration(responseModel: Response<GenerationResponse>)
        fun onSuccessLevel(responseModel: Response<LevelResponseX>)
        fun onSuccess(responseModel: Response<OTGSChannelPartnerResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadGeneration(
            token: String
        )

        fun loadLevel(
            token: String
        )

        fun loadData(
            token: String,
            search_para: String,
            level_id: Int,
            generation_id: Int
        )

        fun onStop()
    }
}