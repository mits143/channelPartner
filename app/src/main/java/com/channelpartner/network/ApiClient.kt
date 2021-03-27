package com.channelpartner.network

import com.channelpartner.Constants.Companion.BASE_URL
import com.channelpartner.Constants.Companion.NEW_BASE_URL
import com.channelpartner.Constants.Companion.SECRET_KEY
import com.channelpartner.model.request.*
import com.channelpartner.model.request.AutoHub.*
import com.channelpartner.model.request.BoardDetail
import com.channelpartner.model.request.MediumDetail
import com.channelpartner.model.request.PackageDetail
import com.channelpartner.model.request.StandradDetail
import com.channelpartner.model.request.SubjetcSpeciality
import com.channelpartner.model.response.*
import com.channelpartner.model.response.SeviceProvider.ServiceProviderResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.otgs.customerapp.model.response.VehicleBrandResponse
import com.otgs.customerapp.model.response.VehicleSubTypeResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {

    private val myAppService: GetDataServices

    companion object {
        private var apiClient: ApiClient? = null
        val instance: ApiClient
            get() {
                if (apiClient == null) {
                    apiClient =
                        ApiClient()
                }
                return apiClient as ApiClient
            }
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Secret_Key", SECRET_KEY)
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(NEW_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        myAppService = retrofit.create(GetDataServices::class.java)
    }

    fun postLogin(
        token: String,
        Email: String,
        Password: String,
        DeviceId: String,
        FCMId: String
    ): Observable<Response<LoginResponse>> {
        return myAppService.login(
            token,
            Email,
            Password,
            DeviceId,
            FCMId
        )
    }

    fun postForgotPwd(
        token: String,
        Email: String
    ): Observable<Response<JsonObject>> {
        return myAppService.forgotPwd(
            token,
            Email
        )
    }

    fun postClassRegister(
        token: String,
        file: MultipartBody.Part,
        data: RequestBody
    ): Observable<Response<JsonObject>> {
        return myAppService.regClass(
            token,
            file,
            data
        )
    }

    fun postTeacherRegistration(
        token: String,
        file: MultipartBody.Part,
        data: RequestBody
    ): Observable<Response<JsonObject>> {
        return myAppService.regTeacher(
            token,
            file,
            data
        )
    }

    fun postRegStudent(
        token: String,
        file: MultipartBody.Part,
        data: RequestBody
    ): Observable<Response<JsonObject>> {
        return myAppService.regStudent(
            token,
            file,
            data
        )
    }

    fun postExpertRegistration(
        token: String,
        file: MultipartBody.Part,
        data: RequestBody
    ): Observable<Response<JsonObject>> {
        return myAppService.regCareerExperts(
            token,
            file,
            data
        )
    }

    fun postCPRegistration(
        token: String,
        file: MultipartBody.Part,
        data: RequestBody
    ): Observable<Response<JsonObject>> {
        return myAppService.regChnnelPartner(
            token,
            file,
            data
        )
    }

    fun postImage(
        token: String,
        profile: MultipartBody.Part,
        user_id: RequestBody,
        user_type: RequestBody,
        pan: MultipartBody.Part,
        aadhar_card: MultipartBody.Part,
        driving_license: MultipartBody.Part,
        passport: MultipartBody.Part,
        voter_id: MultipartBody.Part,
        electricity_bill: MultipartBody.Part
    ): Observable<Response<JsonObject>> {
        return myAppService.uploadImage(
            token,
            profile,
            user_id,
            user_type,
            pan,
            aadhar_card,
            driving_license,
            passport,
            voter_id,
            electricity_bill
        )
    }

    fun postImage1(
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
    ): Observable<Response<JsonObject>> {
        return myAppService.uploadImage11(
            token,
            profile,
            user_id,
            user_type,
            data,
            pan,
            aadhar_card,
            driving_license,
            passport,
            voter_id,
            electricity_bill
        )
    }

    fun postImages(
        token: String,
        profile: MultipartBody.Part,
        user_id: RequestBody,
        user_type: RequestBody,
        data: RequestBody,
        images: ArrayList<MultipartBody.Part>
    ): Observable<Response<JsonObject>> {
        return myAppService.uploadImages(
            token,
            profile,
            user_id,
            user_type,
            data,
            images
        )
    }

    fun postSPImages(
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
    ): Observable<Response<JsonObject>> {
        return myAppService.uploadSPImages(
            token,
            profile,
            user_id,
            user_type,
            pan,
            aadhar_card,
            driving_license,
            passport,
            voter_id,
            electricity_bill,
            shop_license,
            images
        )
    }

    fun postImage(
        token: String,
        profile: MultipartBody.Part,
        user_id: RequestBody,
        user_type: RequestBody
    ): Observable<Response<JsonObject>> {
        return myAppService.uploadImage(
            token,
            profile,
            user_id,
            user_type
        )
    }

    fun postChannelPartnerNetworkList(
        token: String,
        searchKeyword: String,
        levelId: Int,
        generationId: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        return myAppService.getChannelPartnerNetworkList(
            token, searchKeyword, levelId, generationId
        )
    }

    fun postDirectGarageList(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number,
                cpid,
                user_id,
                user_type,
                search_para,
                generation_id,
                level_id
            )
        return myAppService.getDirectGarageList(
            token, jsonObject
        )
    }

    fun postIndirectGarageList(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getIndirectGarageList(
            token, jsonObject
        )
    }

    fun postCpPromotionDetails(
        token: String,
        id: Int
    ): Observable<Response<PromotionDetailResponse>> {
        return myAppService.getCpPromotionDetails(
            token,
            id
        )
    }

    fun postCpNetworkServiceCount(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<NetworkServiceCountResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getCpNetworkServiceCount(
            token, jsonObject
        )
    }

    fun postDirectcClasses(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getDirectcClasses(
            token, jsonObject
        )
    }

    fun postIndirectClasses(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getIndirectClasses(
            token, jsonObject
        )
    }

    fun postDirectTeacher(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getDirectTeacher(
            token, jsonObject
        )
    }

    fun postIndirectTeachers(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getIndirectTeachers(
            token, jsonObject
        )
    }

    fun postDirectStudent(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getDirectStudent(
            token, jsonObject
        )
    }

    fun postIndirectStudents(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getIndirectStudents(
            token, jsonObject
        )
    }

    fun postDirectCareereExperts(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getDirectCareereExperts(
            token, jsonObject
        )
    }

    fun postIndirectCareerExperts(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getIndirectCareerExperts(
            token, jsonObject
        )
    }

    fun postDirectCPList(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getDirectCpList(
            token, jsonObject
        )
    }

    fun postIndirectCPList(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<OTGSChannelPartnerResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getIndirectCPList(
            token, jsonObject
        )
    }

    fun postProfileDetail(
        token: String,
        page_number: Int,
        cpid: String,
        user_id: Int,
        user_type: Int,
        search_para: String,
        generation_id: Int,
        level_id: Int
    ): Observable<Response<ProfileDetailsResponse>> {
        val jsonObject =
            OTGSChannelPartnerRequest(
                page_number, cpid, user_id, user_type, search_para, generation_id,
                level_id
            )
        return myAppService.getChannelPartnerDetails(
            token, jsonObject
        )
    }

    fun GetChannelPartnerById(
        token: String
    ): Observable<Response<ProfileResponse>> {
        return myAppService.GetChannelPartnerById(token)
    }

    fun postEditProfile(
        token: String,
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
        last_name: String,
        nominee_email_id: String,
        nominee_firstName: String,
        nominee_lastName: String,
        nominee_mobile_no: String,
        user_id: Int,
        user_type: Int,
        pancard_number: String
    ): Observable<Response<JsonObject>> {
        val jsonObject = EditProfileRequest(
            contact_address,
            contact_city,
            contact_email_id,
            contact_landline,
            contact_landmark,
            contact_mobile,
            contact_pincode,
            contact_state,
            date_of_birth,
            first_name,
            last_name,
            nominee_email_id,
            nominee_firstName,
            nominee_lastName,
            nominee_mobile_no,
            user_id,
            user_type,
            pancard_number
        )
        return myAppService.EditProfile(
            token, jsonObject
        )
    }

    fun postResetPwd(
        token: String,
        newPwd: String,
        oldPwd: String
    ): Observable<Response<JsonObject>> {
        return myAppService.resetPassword(
            token,
            newPwd,
            oldPwd
        )
    }

    fun getAllServiceMasterList(
        token: String,
        user_type: String
    ): Observable<Response<AutoHubResponse>> {
        return myAppService.getAllServiceMasterList(
            token,
            user_type
        )
    }

    fun getAllStates(
        token: String
    ): Observable<Response<StateResponse>> {
        return myAppService.getAllStates(
            token
        )
    }

    fun getCities(
        token: String,
        state_id: Int
    ): Observable<Response<StateResponse>> {
        return myAppService.getCities(
            token,
            state_id
        )
    }

    fun getPincodes(
        token: String,
        city_id: Int
    ): Observable<Response<StateResponse>> {
        return myAppService.getPincodes(
            token,
            city_id
        )
    }

    fun postChannelPartnerDetail(
        token: String,
        id: Int
    ): Observable<Response<CPDetailResponse>> {
        return myAppService.getChannelPartnerDetail(
            token,
            id
        )
    }

    fun getServiceProviderDetails(
        token: String,
        service_provider_id: String,
        cust_id: String
    ): Observable<Response<ServiceProviderResponse>> {
        return myAppService.getServiceProviderDetails(
            token,
            service_provider_id,
            cust_id
        )
    }

    fun postStudentDetails(
        token: String,
        user_id: String,
        user_type: String,
        stud_id: Int
    ): Observable<Response<StudentResponse>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        jsonObject.addProperty("stud_id", stud_id)
        return myAppService.getStudentDetails(
            token, jsonObject
        )
    }

    fun postTeacherDetails(
        token: String,
        user_id: String,
        user_type: String,
        teacher_id: Int
    ): Observable<Response<ClassDetailResponse>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        jsonObject.addProperty("teacher_id", teacher_id)
        return myAppService.getTeacherDetails(
            token, jsonObject
        )
    }

    fun postClassDetails(
        token: String,
        user_id: String,
        user_type: String,
        class_id: Int
    ): Observable<Response<ClassDetailResponse>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        jsonObject.addProperty("class_id", class_id)
        return myAppService.getClassDetails(
            token, jsonObject
        )
    }

    fun postCareerExpertDetails(
        token: String,
        user_id: String,
        user_type: String,
        career_expert: Int
    ): Observable<Response<ClassDetailResponse>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        jsonObject.addProperty("career_expert", career_expert)
        return myAppService.getCareerExpertDetails(
            token, jsonObject
        )
    }

    fun postNotificationFilterCP(
        token: String,
        notification_type: String,
        app_code: String,
        page_number: String,
        user_id: Int,
        user_type: Int
    ): Observable<Response<NotificationResponse>> {
        val jsonObject = NotificationRequest(
            notification_type,
            app_code,
            page_number,
            user_id,
            user_type
        )
        return myAppService.getnotificationFilterCP(
            token, jsonObject
        )
    }

    fun postNotificationFilterClassbook(
        token: String,
        notification_type: String,
        app_code: String,
        page_number: String,
        user_id: Int,
        user_type: Int
    ): Observable<Response<NotificationResponse>> {
        val jsonObject = NotificationRequest(
            notification_type,
            app_code,
            page_number,
            user_id,
            user_type
        )
        return myAppService.getnotificationfilterclassbook(
            token, jsonObject
        )
    }

    fun posttGarageCommunicationRenewServiceList(
        token: String,
        user_id: Int,
        user_type: Int
    ): Observable<Response<PackageRerquestResponse>> {
        val jsonObject = RenewalRequest(
            user_id,
            user_type
        )
        return myAppService.getGarageCommunicationRenewServiceList(
            token, jsonObject
        )
    }

    fun posttGarageCommunicationAddServiceList(
        token: String,
        user_id: Int,
        user_type: Int
    ): Observable<Response<PackageRerquestResponse>> {
        val jsonObject = RenewalRequest(
            user_id,
            user_type
        )
        return myAppService.getGarageCommunicationAddServiceList(
            token, jsonObject
        )
    }

    fun postCPCommunicationApproveRejectList(
        token: String,
        end_date: String,
        start_date: String,
        status: Int,
        user_id: Int,
        user_type: Int
    ): Observable<Response<PackageRerquestResponse>> {
        val jsonObject = HistoryRequest(
            end_date,
            start_date,
            status,
            user_id,
            user_type
        )
        return myAppService.getCPCommunicationApproveRejectList(
            token, jsonObject
        )
    }

    fun getAllYears(
        token: String
    ): Observable<Response<YearResponse>> {
        return myAppService.getYears(
            token
        )
    }

    fun getAllMonths(
        token: String
    ): Observable<Response<MonthResponse>> {
        return myAppService.getMonths(
            token
        )
    }

    fun getAllToMonths(
        token: String,
        month_id: String
    ): Observable<Response<MonthResponse>> {
        return myAppService.getAllToMonths(
            token,
            month_id
        )
    }

    fun postFilterCpIncomeDetails(
        token: String,
        cpid: Int,
        from_month: Int,
        to_month: Int,
        user_id: Int,
        user_type: Int,
        year: Int
    ): Observable<Response<IncomeDetail>> {
        val jsonObject = IncomeDetailRequest(
            cpid,
            from_month,
            to_month,
            user_id,
            user_type,
            year
        )
        return myAppService.filtercpincomedetails(
            token, jsonObject
        )
    }

    fun postCpNetIncomeDetails(
        token: String,
        cpid: Int,
        user_id: Int,
        user_type: Int,
        year: Int
    ): Observable<Response<IncomeDetail>> {
        val jsonObject = IncomeDetailRequest1(
            cpid,
            user_id,
            user_type,
            year
        )
        return myAppService.cpNetIncomeDetails(
            token, jsonObject
        )
    }

    fun postCpMonthlyIncomeDetails(
        token: String,
        cpid: Int,
        month: Int,
        user_id: Int,
        user_type: Int,
        year: Int
    ): Observable<Response<MonthlyIncomeDetailResponse>> {
        val jsonObject = MonthlyIncomeRequest(
            cpid,
            month,
            user_id,
            user_type,
            year
        )
        return myAppService.cpMonthlyIncomeDetails(
            token, jsonObject
        )
    }

    fun postRegChannelPartner(
        token: String,
        channelpartner_id: String,
        contact_address: String,
        contact_city: String,
        contact_email_id: String,
        contact_landline: String,
        contact_mobile: String,
        contact_pincode: String,
        contact_state: String,
        date_of_birth: String,
        first_name: String,
        last_name: String,
        gender: Int,
        RegistrationFromTypeId: Int,
        RegistrationByTypeId: Int
    ): Observable<Response<JsonObject>> {
        val jsonObject = CPRegRequest(
            channelpartner_id,
            contact_address,
            contact_city,
            contact_email_id,
            contact_landline,
            contact_mobile,
            contact_pincode,
            contact_state,
            date_of_birth,
            first_name,
            last_name,
            gender,
            RegistrationFromTypeId,
            RegistrationByTypeId
        )
        return myAppService.regChannelPartner(
            token, jsonObject
        )
    }

    fun getAllBoard(
        token: String
    ): Observable<Response<StateResponse>> {
        return myAppService.getAllBoard(
            token
        )
    }

    fun getAllMediums(
        token: String
    ): Observable<Response<StateResponse>> {
        return myAppService.getAllMediums(
            token
        )
    }

    fun getAllStandards(
        token: String
    ): Observable<Response<StateResponse>> {
        return myAppService.getAllStandards(
            token
        )
    }

    fun getAllCategory(
        token: String
    ): Observable<Response<StateResponse>> {
        return myAppService.getAllCategory(
            token
        )
    }

    fun getAllSubjectSpecialities(
        token: String
    ): Observable<Response<StateResponse>> {
        return myAppService.getAllSubjectSpecialities(
            token
        )
    }

    fun senOTP(
        token: String,
        mobile_number: String
    ): Observable<Response<JsonObject>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("mobile_no", mobile_number)
        return myAppService.SendOTP(
            token,
            jsonObject
        )
    }

    fun postRegServiceProvider(
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
    ): Observable<Response<JsonObject>> {
        val autoHubRequest = AutoHubRequest(
            all_service_details,
            business_code,
            channelpartner_id,
            garage_information,
            gst,
            internet_handling_cost,
            package_details,
            payment_mode,
            total,
            working_hours
        )
        return myAppService.regServiceProvider(
            token, autoHubRequest
        )
    }

    fun postCommission(
        token: String,
        date_of_joining: String,
        name: String,
        paid_amount: String,
        parent_id: Int,
        shop_type: String,
        user_id: Int,
        user_type: Int
    ): Observable<Response<JsonObject>> {
        val commissionRequest = CommissionRequest(
            date_of_joining,
            name,
            paid_amount,
            parent_id,
            shop_type,
            user_id,
            user_type
        )
        return myAppService.CalculateCommision(
            token, commissionRequest
        )
    }

    fun postGarageCommunicationDetails(
        token: String,
        user_id: Int,
        user_type: Int,
        history_id: Int
    ): Observable<Response<RenewalDetailResponse>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        jsonObject.addProperty("history_id", history_id)
        return myAppService.getGarageCommunicationDetails(
            token, jsonObject
        )
    }

    fun postApproveCommunication(
        token: String,
        user_id: Int,
        user_type: Int,
        history_id: Int
    ): Observable<Response<JsonObject>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        jsonObject.addProperty("history_id", history_id)
        return myAppService.approveCommunication(
            token, jsonObject
        )
    }

    fun postRejectCommunication(
        token: String,
        user_id: Int,
        user_type: Int,
        history_id: Int
    ): Observable<Response<JsonObject>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        jsonObject.addProperty("history_id", history_id)
        return myAppService.rejectCommunication(
            token, jsonObject
        )
    }

    fun postKyc(
        token: String,
        user_id: Int,
        user_type: Int
    ): Observable<Response<KyCResponse>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        return myAppService.getKycList(
            token, jsonObject
        )
    }

    fun getLevelChartDetails(
        token: String
    ): Observable<Response<LevelResponse>> {
        return myAppService.levelChartDetails(
            token
        )
    }

    fun getAllCategories(
        token: String
    ): Observable<Response<ContactUsResponse>> {
        return myAppService.getAllCategories(
            token
        )
    }

    fun postSubmitQuery(
        token: String,
        user_id: String,
        user_type: String,
        query: String,
        category_id: String
    ): Observable<Response<JsonObject>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        jsonObject.addProperty("query", query)
        jsonObject.addProperty("category_id", category_id)
        return myAppService.submitQuery(
            token, jsonObject
        )
    }

    fun getAllFaq(
        token: String
    ): Observable<Response<FAQResponse>> {
        return myAppService.getAllFaq(
            token
        )
    }

    fun getpackageValues(
        token: String
    ): Observable<Response<PackageValueResponse>> {
        return myAppService.packageValues(
            token
        )
    }

    fun packageValueClassbook(
        token: String
    ): Observable<Response<ClassbookResponse>> {
        return myAppService.packageValueClassbook(
            token
        )
    }

    fun getAllGeneration(
        token: String
    ): Observable<Response<GenerationResponse>> {
        return myAppService.getAllGeneration(
            token
        )
    }

    fun getAllLevels(
        token: String
    ): Observable<Response<LevelResponseX>> {
        return myAppService.getAllLevels(
            token
        )
    }

    fun getStudentSubscription(
        token: String,
        stud_id: Int,
        user_id: Int,
        user_type: Int
    ): Observable<Response<SubscrpitionResponse>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("stud_id", stud_id)
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        return myAppService.getStudentSubscription(token, jsonObject)
    }

    fun getAllBoardStandards(
        token: String
    ): Observable<Response<BoardStandardResponse>> {
        return myAppService.getAllBoardStandards(token)
    }

    fun getVehicleSubTypes(
        token: String,
        vehicle_type_id: String
    ): Observable<Response<VehicleSubTypeResponse>> {
        return myAppService.getVehicleSubTypes(token, vehicle_type_id)
    }

    fun getVehicleBrands(
        token: String,
        vehicle_subtype_id: String
    ): Observable<Response<VehicleBrandResponse>> {
        return myAppService.getVehicleBrands(token, vehicle_subtype_id)
    }

    fun getAllCourses(
        token: String
    ): Observable<Response<StateResponse>> {
        return myAppService.getAllCourses(token)
    }

    fun getCpIncomeEarnedFromClass(
        token: String,
        user_id: Int,
        user_type: Int
    ): Observable<Response<StatisticsResponse>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", user_id)
        jsonObject.addProperty("user_type", user_type)
        return myAppService.getCpIncomeEarnedFromClass(token, jsonObject)
    }

    fun getCpMonthlyExtraIncome(
        token: String,
        month_id: Int,
        year_id: Int,
        type: Int
    ): Observable<Response<IndividualResponse>> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("month_id", month_id)
        jsonObject.addProperty("year_id", year_id)
        jsonObject.addProperty("type", type)
        return myAppService.getCpMonthlyExtraIncome(token, jsonObject)
    }
}