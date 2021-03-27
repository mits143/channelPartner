package com.channelpartner.view

import com.channelpartner.model.response.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface CPRegistrationView {

    interface MainView {

        fun showProgressbar(type: Int)
        fun hideProgressbar(type: Int)
        fun onSuccessgetStates(responseModel: Response<StateResponse>)
        fun onSuccessgetCities(responseModel: Response<StateResponse>)
        fun onSuccessgetPincodes(responseModel: Response<StateResponse>)
        fun onSuccess(type: Int, responseModel: Response<JsonObject>)
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

        fun CPReg(
            token: String,
            account_holder_name: String,
            account_number: String,
            bank_name: String,
            branch_name: String,
            channelpartner_id: String,
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
            ifsc_code: String,
            last_name: String,
            pancard_number: String,
            gender: Int
        )

        fun commission(
            token: String,
            date_of_joining: String,
            name: String,
            paid_amount: String,
            parent_id: Int,
            shop_type: String,
            user_id: Int,
            user_type: Int
        )

        fun CPImageUpload(
            token: String,
            profile: MultipartBody.Part,
            user_id: RequestBody,
            user_type: RequestBody,
            data: RequestBody,
            pan: MultipartBody.Part,
            aadhar_card: MultipartBody.Part,
            driving_license: MultipartBody.Part,
            passport: MultipartBody.Part,
            voter_id: MultipartBody.Part,
            electricity_bill: MultipartBody.Part
        )

        fun postCPRegisterData(
            token: String,
            file: MultipartBody.Part,
            data: RequestBody
        )

        fun onStop()
    }
}