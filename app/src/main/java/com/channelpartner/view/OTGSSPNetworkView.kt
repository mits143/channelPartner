package com.channelpartner.view

import com.channelpartner.model.response.AutoHubResponse
import com.channelpartner.model.response.OTGSChannelPartnerResponse
import retrofit2.Response

interface OTGSSPNetworkView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccessServices(responseModel: Response<AutoHubResponse>)
        fun onSuccess(value: Int, responseModel: Response<OTGSChannelPartnerResponse>)
        fun onError(value: Int, errorCode: Int)
        fun onError(value: Int, throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            user_type: String
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