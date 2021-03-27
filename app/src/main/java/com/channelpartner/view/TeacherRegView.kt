package com.channelpartner.view

import com.channelpartner.model.request.*
import com.channelpartner.model.request.BoardDetail
import com.channelpartner.model.request.MediumDetail
import com.channelpartner.model.request.PackageDetail
import com.channelpartner.model.request.StandradDetail
import com.channelpartner.model.request.SubjetcSpeciality
import com.channelpartner.model.response.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.time.temporal.TemporalAmount

interface TeacherRegView {

    interface MainView {

        fun showProgressbar(int: Int)
        fun hideProgressbar(int: Int)
        fun onSuccessgetStates(responseModel: Response<StateResponse>)
        fun onSuccessgetCities(responseModel: Response<StateResponse>)
        fun onSuccessgetPincodes(responseModel: Response<StateResponse>)
        fun onSuccessBoard(responseModel: Response<BoardResponse>)
        fun onSuccessBoard1(responseModel: Response<BoardStandardResponse>)
        fun onSuccessStandard(responseModel: Response<StandardResponse>)
        fun onSuccessCourses(responseModel: Response<CoursesResponse>)
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
            token: String, city_id: Int
        )

        fun loadBoardDataNew(
            token: String
        )

        fun loadBoardData(
            token: String
        )

        fun loadStandardData(
            token: String
        )

        fun loadCoursesData(
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
            address: String,
            alternate_contact: String,
            course_details: List<CourseDetail>,
            board_details: List<BoardDetail>,
            category_details: List<CategoryDetail>,
            state_id: Int,
            city_id: Int,
            contact: String,
            created_by: Int,
            description: String,
            email: String,
            establishment_date: String,
            gst: String,
            internet_handling_cost: String,
            last_name: String,
            medium_details: List<MediumDetail>,
            name: String,
            other_speciality: String,
            package_details: List<PackageDetail>,
            pincode: Int,
            standrad_details: List<StandradDetail>,
            std_other: String,
            subjetc_speciality: List<SubjetcSpeciality>,
            teaching_exp: String,
            total: String,
            amount: String,
            gender: Int
        )

        fun CPImageUploads(
            token: String,
            profile: MultipartBody.Part,
            user_id: RequestBody,
            user_type: RequestBody,
            data: RequestBody,
            images: ArrayList<MultipartBody.Part>
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

        fun onStop()
    }
}