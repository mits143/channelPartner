package com.channelpartner.view

import com.channelpartner.model.response.LoginResponse
import com.channelpartner.model.response.StateResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface ClassRegView {
    interface MainView {
        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccessCommonData(int: Int, responseModel: Response<StateResponse>)
        fun onSuccess(responseModel: Response<JsonObject>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun getStates(
            token: String
        )

        fun getCities(
            token: String,
            id: Int
        )

        fun getPincodes(
            token: String,
            id: Int
        )

        fun getBoard(
            token: String
        )

        fun getMedium(
            token: String
        )

        fun getStandard(
            token: String
        )

        fun postClassRegisterData(
            token: String,
            file: MultipartBody.Part,
            data: RequestBody
        )

        fun postSchoolRegisterData(
            token: String,
            file: MultipartBody.Part,
            data: RequestBody
        )

        fun postStudentRegisterData(
            token: String,
            file: MultipartBody.Part,
            data: RequestBody
        )

        fun postTeacherRegisterData(
            token: String,
            file: MultipartBody.Part,
            data: RequestBody
        )

        fun postExpertRegisterData(
            token: String,
            file: MultipartBody.Part,
            data: RequestBody
        )

        fun onStop()
    }
}