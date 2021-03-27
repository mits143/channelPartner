package com.channelpartner.view

import com.channelpartner.model.response.*
import com.channelpartner.model.response.SeviceProvider.ServiceProviderResponse
import com.google.gson.JsonObject
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface CPDetailView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<CPDetailResponse>)
        fun onSuccessGarage(responseModel: Response<ServiceProviderResponse>)
        fun onSuccess1(responseModel: Response<ClassDetailResponse>)
        fun onSuccessStudent(responseModel: Response<StudentResponse>)
        fun onSuccess2(responseModel: Response<PromotionDetailResponse>)
        fun onSuccessStatics(responseModel: Response<StatisticsResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadCpDetailData(
            token: String,
            id: Int
        )

        fun loadGarageDetailData(
            token: String,
            service_provider_id: String,
            cust_id: String
        )

        fun loadClassDetailData(
            token: String,
            user_id: String,
            user_type: String,
            id: Int
        )

        fun loadTeacherDetailData(
            token: String,
            user_id: String,
            user_type: String,
            id: Int
        )

        fun loadStudentDetailData(
            token: String,
            user_id: String,
            user_type: String,
            id: Int
        )

        fun loadExpertCareerDetailData(
            token: String,
            user_id: String,
            user_type: String,
            id: Int
        )

        fun loadPromotionDetails(
            token: String,
            id: Int
        )

        fun loadstats(
            token: String,
            user_id: Int,
            user_type: Int
        )

        fun onStop()
    }
}