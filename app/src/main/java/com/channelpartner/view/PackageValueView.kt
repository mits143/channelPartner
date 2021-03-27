package com.channelpartner.view

import com.channelpartner.model.response.ClassbookResponse
import com.channelpartner.model.response.PackageValueResponse
import retrofit2.Response

interface PackageValueView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<PackageValueResponse>)
        fun onSuccessClassbook(responseModel: Response<ClassbookResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String
        )
        fun loadDataClassbook(
            token: String
        )

        fun onStop()
    }
}