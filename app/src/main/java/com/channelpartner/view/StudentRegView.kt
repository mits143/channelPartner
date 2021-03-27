package com.channelpartner.view

import com.channelpartner.model.request.*
import com.channelpartner.model.request.BoardDetail
import com.channelpartner.model.request.MediumDetail
import com.channelpartner.model.request.StandradDetail
import com.channelpartner.model.response.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface StudentRegView {

    interface MainView {

        fun showProgressbar(int: Int)
        fun hideProgressbar(int: Int)
        fun onSuccessgetStates(responseModel: Response<StateResponse>)
        fun onSuccessgetCities(responseModel: Response<StateResponse>)
        fun onSuccessgetPincodes(responseModel: Response<StateResponse>)
        fun onSuccessBoard(responseModel: Response<BoardResponse>)
        fun onSuccessStandard(responseModel: Response<StandardResponse>)
        fun onSuccessMedium(responseModel: Response<MediumResponse>)
        fun onSuccessCategory(responseModel: Response<CategoryResponse>)
        fun onSuccessSubSpecialities(responseModel: Response<SubjectSpecialitiesResponse>)
        fun onSuccess(typ: Int, responseModel: Response<JsonObject>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {
        fun loadStates(
            token: String
        )

        fun loadCities(
            token: String, state_id: Int
        )

        fun loadPincodes(
            token: String, city_id: String
        )

        fun loadBoardData(
            token: String
        )

        fun loadStandardData(
            token: String
        )

        fun loadMediumData(
            token: String
        )

        fun loadCategoryData(
            token: String
        )

        fun loadSubSpecialitiesData(
            token: String
        )

        fun loadData(
            token: String,
            amount: String,
            board_id: Int,
            contact_address: String,
            contact_city: Int,
            contact_email_id: String,
            contact_pincode: Int,
            contact_state: Int,
            created_by: Int,
            date_of_birth: String,
            first_name: String,
            gender: Int,
            gst: String,
            internet_handling_cost: String,
            last_name: String,
            medium_id: Int,
            mobile_number: String,
            std_id: Int,
            total: String
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

        fun CPImageUploads(
            token: String,
            profile: MultipartBody.Part,
            user_id: RequestBody,
            user_type: RequestBody,
            data: RequestBody,
            images: ArrayList<MultipartBody.Part>
        )

        fun onStop()
    }
}