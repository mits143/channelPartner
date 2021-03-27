package com.channelpartner.view

import com.channelpartner.model.request.AutoHub.AllServiceDetail
import com.channelpartner.model.request.AutoHub.GarageInformation
import com.channelpartner.model.request.AutoHub.PaymentMode
import com.channelpartner.model.request.AutoHub.WorkingHour
import com.channelpartner.model.response.AutoHubResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface DetailView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(type: Int, responseModel: Response<JsonObject>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String,
            all_service_details: List<AllServiceDetail>,
            business_code: String,
            channelpartner_id: String,
            garage_information: GarageInformation,
            gst: String,
            internet_handling_cost: String,
            package_details: List<com.channelpartner.model.request.AutoHub.PackageDetail>,
            payment_mode: List<PaymentMode>,
            total: String,
            working_hours: ArrayList<WorkingHour>
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

        fun SPImageUploads(
            token: String,
            profile: MultipartBody.Part,
            user_id: RequestBody,
            user_type: RequestBody,
            pan: MultipartBody.Part,
            aadhar_card: MultipartBody.Part,
            driving_license: MultipartBody.Part,
            passport: MultipartBody.Part,
            voter_id: MultipartBody.Part,
            electricity_bill: MultipartBody.Part,
            shop_license: MultipartBody.Part,
            images: ArrayList<MultipartBody.Part>
        )

        fun onStop()
    }
}