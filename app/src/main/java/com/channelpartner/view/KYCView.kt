package com.channelpartner.view

import com.channelpartner.model.response.KyCResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface KYCView {

    interface MainView {

        fun showProgressbar(int: Int)
        fun hideProgressbar(int: Int)
        fun onSuccess(responseModel: Response<KyCResponse>)
        fun onSuccess1(responseModel: Response<JsonObject>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            user_id: Int,
            user_type: Int
        )

        fun loadData1(
            token: String,
            profile: MultipartBody.Part,
            user_id: RequestBody,
            user_type: RequestBody,
            pan: MultipartBody.Part,
            aadhar_card: MultipartBody.Part,
            driving_license: MultipartBody.Part,
            passport: MultipartBody.Part,
            voter_id: MultipartBody.Part,
            electricity_bill: MultipartBody.Part
        )

        fun onStop()
    }
}