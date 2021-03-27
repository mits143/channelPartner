package com.channelpartner.view

import com.channelpartner.model.response.*
import okhttp3.internal.http2.ErrorCode
import retrofit2.Response
import retrofit2.http.Header

interface IndividualInccomeView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccessYear(responseModel: Response<YearResponse>)
        fun onSuccessMonth(responseModel: Response<MonthResponse>)
        fun onSuccess(responseModel: Response<IndividualResponse>)
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
        fun loadData(
            token: String,
            month_id: Int,
            year_id: Int,
            type: Int
        )

        fun onStop()
    }
}