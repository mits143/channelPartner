package com.channelpartner.view

import com.channelpartner.model.response.*
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response

interface MyIncomeView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccessYear(responseModel: Response<YearResponse>)
        fun onSuccessMonth(responseModel: Response<MonthResponse>)
        fun onSuccessMonth1(responseModel: Response<MonthResponse>)
        fun onSuccessIncomeDetail(responseModel: Response<IncomeDetail>)
        fun onSuccessMonthlyIncomeDetail(responseModel: Response<MonthlyIncomeDetailResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadYearData(
            token: String
        )

        fun loadMonthData(
            token: String
        )

        fun getAllToMonths(
            token: String,
            month_id: String
        )

        fun loadIncomeYearData(
            token: String,
            cpid: Int,
            from_month: Int,
            to_month: Int,
            user_id: Int,
            user_type: Int,
            year: Int
        )

        fun loadNetIncomeData(
            token: String,
            cpid: Int,
            user_id: Int,
            user_type: Int,
            year: Int
        )

        fun loadMonthlyIncomeDetailsData(
            token: String,
            cpid: Int,
            month: Int,
            user_id: Int,
            user_type: Int,
            year: Int
        )

        fun onStop()
    }
}