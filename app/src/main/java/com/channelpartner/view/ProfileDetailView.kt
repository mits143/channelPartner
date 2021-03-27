package com.channelpartner.view

import com.channelpartner.model.response.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface ProfileDetailView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccessProfile(responseModel: Response<ProfileResponse>)
        fun onSuccess(responseModel: Response<ProfileDetailsResponse>)
        fun onSuccessEditProfile(int: Int, responseModel: Response<JsonObject>)
        fun onSuccessgetStates(responseModel: Response<StateResponse>)
        fun onSuccessgetCities(responseModel: Response<StateResponse>)
        fun onSuccessgetPincodes(responseModel: Response<StateResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String
        )
        fun loadData(
            token: String,
            page_number: Int,
            cpid: String,
            user_id: Int,
            user_type: Int,
            search_para: String,
            generation_id: Int,
            level_id: Int
        )

        fun editProfile(
            token: String,
            contact_address: String,
            contact_city: String,
            contact_email_id: String,
            contact_landline: String,
            contact_landmark: String,
            contact_mobile: String,
            contact_pincode: String,
            contact_state: String,
            date_of_birth: String,
            first_name: String,
            last_name: String,
            nominee_email_id: String,
            nominee_firstName: String,
            nominee_lastName: String,
            nominee_mobile_no: String,
            user_id: Int,
            user_type: Int,
            pancard_number: String
        )

        fun loadStates(
            token: String
        )

        fun loadCities(
            token: String, state_id: Int
        )

        fun loadPincodes(
            token: String, city_id: Int
        )

        fun ImageUpload(
            token: String,
            profile: MultipartBody.Part,
            user_id: RequestBody,
            user_type: RequestBody
        )

        fun onStop()
    }
}