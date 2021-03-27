package com.channelpartner.view

import com.channelpartner.model.response.LevelResponse
import retrofit2.Response

interface LevelView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(responseModel: Response<LevelResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadData(
            token: String
        )

        fun onStop()
    }
}