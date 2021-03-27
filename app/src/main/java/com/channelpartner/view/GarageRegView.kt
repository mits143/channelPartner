package com.channelpartner.view

import com.channelpartner.model.response.*
import com.google.gson.JsonObject
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface GarageRegView {

    interface MainView {

        fun showProgressbar(type: Int)
        fun hideProgressbar(type: Int)
        fun onSuccessgetStates(responseModel: Response<StateResponse>)
        fun onSuccessgetCities(responseModel: Response<StateResponse>)
        fun onSuccessgetPincodes(responseModel: Response<StateResponse>)
        fun onSuccess(responseModel: Response<JsonObject>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadStates(
            token: String
        )

        fun loadCities(
            token: String,
            state_id: Int
        )

        fun loadPincodes(
            token: String,
            city_id: Int
        )

        fun loadData(
            token: String,
            mobileNumber: String
        )

        fun onStop()
    }
}