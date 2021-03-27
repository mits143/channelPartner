package com.channelpartner.network

import com.channelpartner.Constants
import com.channelpartner.Constants.Companion.APPROVE_COMMUNICATION
import com.channelpartner.Constants.Companion.CALCULATE_COMMISION
import com.channelpartner.Constants.Companion.CHANNELPARTNER_REGISTER
import com.channelpartner.Constants.Companion.CP_MONTHLY_INCOMEDETAILS
import com.channelpartner.Constants.Companion.CP_NET_INCOMEDETAILS
import com.channelpartner.Constants.Companion.EDIT_PROFILE
import com.channelpartner.Constants.Companion.FILTER_CP_INCOMEDETAILS
import com.channelpartner.Constants.Companion.FORGOT_PASSWORD
import com.channelpartner.Constants.Companion.GET_ALLBOARD
import com.channelpartner.Constants.Companion.GET_ALLBOARDSTANDARDS
import com.channelpartner.Constants.Companion.GET_ALLCATEGORIES
import com.channelpartner.Constants.Companion.GET_ALLCATEGORY
import com.channelpartner.Constants.Companion.GET_ALLCOURSES
import com.channelpartner.Constants.Companion.GET_ALLFAQ
import com.channelpartner.Constants.Companion.GET_ALLGENERATION
import com.channelpartner.Constants.Companion.GET_ALLLEVELS
import com.channelpartner.Constants.Companion.GET_ALLMEDIUMS
import com.channelpartner.Constants.Companion.GET_ALLMONTHS
import com.channelpartner.Constants.Companion.GET_ALLSTANDARDS
import com.channelpartner.Constants.Companion.GET_ALLSUBJECTSPECIALITIES
import com.channelpartner.Constants.Companion.GET_ALLYEARS
import com.channelpartner.Constants.Companion.GET_ALL_SERVICE_MASTERLIST
import com.channelpartner.Constants.Companion.GET_ALL_STATES
import com.channelpartner.Constants.Companion.GET_ALL_TOMONTHS
import com.channelpartner.Constants.Companion.GET_CAREEREXPERT_DETAILS
import com.channelpartner.Constants.Companion.GET_CHANNEL_PARTNER_BY_ID
import com.channelpartner.Constants.Companion.GET_CHANNEL_PARTNER_DETAIL
import com.channelpartner.Constants.Companion.GET_CHANNEL_PARTNER_DETAILS
import com.channelpartner.Constants.Companion.GET_CHANNEL_PARTNER_NETWORKLIST
import com.channelpartner.Constants.Companion.GET_CITIES
import com.channelpartner.Constants.Companion.GET_CLASS_DETAILS
import com.channelpartner.Constants.Companion.GET_CPCOMMUNICATION_APPROVEREJECTLIST
import com.channelpartner.Constants.Companion.GET_CPNETWORK_SERVICECOUNT
import com.channelpartner.Constants.Companion.GET_CPPROMOTION_DETAILS
import com.channelpartner.Constants.Companion.GET_CP_INCOMEEARNED_FROMCLASS
import com.channelpartner.Constants.Companion.GET_CP_MONTHLY_EXTRAINCOME
import com.channelpartner.Constants.Companion.GET_DIRECT_CAREERE_EXPERTS
import com.channelpartner.Constants.Companion.GET_DIRECT_CCLASSES
import com.channelpartner.Constants.Companion.GET_DIRECT_CPLIST
import com.channelpartner.Constants.Companion.GET_DIRECT_GARAGELIST
import com.channelpartner.Constants.Companion.GET_DIRECT_STUDENT
import com.channelpartner.Constants.Companion.GET_DIRECT_TEACHER
import com.channelpartner.Constants.Companion.GET_GARAGECOMMUNICATION_ADDSERVICELIST
import com.channelpartner.Constants.Companion.GET_GARAGECOMMUNICATION_RENEWSERVICELIST
import com.channelpartner.Constants.Companion.GET_GARAGE_COMMUNICATIONDETAILS
import com.channelpartner.Constants.Companion.GET_INDIRECT_CAREER_EXPERTS
import com.channelpartner.Constants.Companion.GET_INDIRECT_CLASSES
import com.channelpartner.Constants.Companion.GET_INDIRECT_CPLIST
import com.channelpartner.Constants.Companion.GET_INDIRECT_GARAGELIST
import com.channelpartner.Constants.Companion.GET_INDIRECT_STUDENTS
import com.channelpartner.Constants.Companion.GET_INDIRECT_TEACHERS
import com.channelpartner.Constants.Companion.GET_KYCLIST
import com.channelpartner.Constants.Companion.GET_NOTIFICATION_FILTERCP
import com.channelpartner.Constants.Companion.GET_NOTIFICATION_FILTER_CLASSBOOK
import com.channelpartner.Constants.Companion.GET_PINCODES
import com.channelpartner.Constants.Companion.GET_SERVICE_PROVIDERDETAILS
import com.channelpartner.Constants.Companion.GET_STUDENTSUBSCRIPTION
import com.channelpartner.Constants.Companion.GET_STUDENT_DETAILS
import com.channelpartner.Constants.Companion.GET_TEACHER_DETAILS
import com.channelpartner.Constants.Companion.GET_VEHICLEBRANDS
import com.channelpartner.Constants.Companion.GET_VEHICLESUBTYPES
import com.channelpartner.Constants.Companion.LEVELCHART_DETAILS
import com.channelpartner.Constants.Companion.LOGIN
import com.channelpartner.Constants.Companion.PACKAGE_VALUES
import com.channelpartner.Constants.Companion.PACKAGE_VALUE_CLASSBOOK
import com.channelpartner.Constants.Companion.REG_CAREER_EXPERTS
import com.channelpartner.Constants.Companion.REG_CHANNELPARTNER
import com.channelpartner.Constants.Companion.REG_CLASS
import com.channelpartner.Constants.Companion.REG_SERVICEPROVIDER
import com.channelpartner.Constants.Companion.REG_STUDENT
import com.channelpartner.Constants.Companion.REG_TEACHER
import com.channelpartner.Constants.Companion.REJECT_COMMUNICATION
import com.channelpartner.Constants.Companion.RESET_PASSWORD
import com.channelpartner.Constants.Companion.SEND_OTP
import com.channelpartner.Constants.Companion.SUBMIT_QUERY
import com.channelpartner.Constants.Companion.UPLOAD_CPIMAGES
import com.channelpartner.model.request.*
import com.channelpartner.model.request.AutoHub.AutoHubRequest
import com.channelpartner.model.response.*
import com.channelpartner.model.response.SeviceProvider.ServiceProviderResponse
import com.google.gson.JsonObject
import com.otgs.customerapp.model.response.VehicleBrandResponse
import com.otgs.customerapp.model.response.VehicleSubTypeResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface GetDataServices {

    @FormUrlEncoded
    @POST(LOGIN)
    fun login(
        @Header("AuthorizeTokenKey") token: String,
        @Field(
            value = "Email",
            encoded = false
        ) Email: String,
        @Field(
            value = "Password",
            encoded = false
        ) Password: String,
        @Field(
            value = "DeviceId",
            encoded = false
        ) DeviceId: String,
        @Field(
            value = "FCMId",
            encoded = false
        ) FCMId: String
    ): Observable<Response<LoginResponse>>

    @POST(FORGOT_PASSWORD)
    fun forgotPwd(
        @Header("AuthorizeTokenKey") token: String,
        @Field(
            value = "Email",
            encoded = false
        ) Email: String
    ): Observable<Response<JsonObject>>


    @Multipart
    @POST(REG_CLASS)
    fun regClass(
        @Header("AuthorizeTokenKey") token: String,
        @Part file: MultipartBody.Part,
        @Part("data") data: RequestBody
    ): Observable<Response<JsonObject>>

    @Multipart
    @POST(REG_TEACHER)
    fun regTeacher(
        @Header("AuthorizeTokenKey") token: String,
        @Part file: MultipartBody.Part,
        @Part("data") data: RequestBody
    ): Observable<Response<JsonObject>>

    @Multipart
    @POST(REG_STUDENT)
    fun regStudent(
        @Header("AuthorizeTokenKey") token: String,
        @Part file: MultipartBody.Part,
        @Part("data") data: RequestBody
    ): Observable<Response<JsonObject>>

    @Multipart
    @POST(REG_CAREER_EXPERTS)
    fun regCareerExperts(
        @Header("AuthorizeTokenKey") token: String,
        @Part file: MultipartBody.Part,
        @Part("data") data: RequestBody
    ): Observable<Response<JsonObject>>

    @Multipart
    @POST(CHANNELPARTNER_REGISTER)
    fun regChnnelPartner(
        @Header("AuthorizeTokenKey") token: String,
        @Part file: MultipartBody.Part,
        @Part("data") data: RequestBody
    ): Observable<Response<JsonObject>>

    @Multipart
    @POST(UPLOAD_CPIMAGES)
    fun uploadImage(
        @Header("AuthorizeTokenKey") token: String,
        @Part profile: MultipartBody.Part,
        @Part("user_id") user_id: RequestBody,
        @Part("user_type") user_type: RequestBody,
        @Part pan: MultipartBody.Part,
        @Part aadhar_card: MultipartBody.Part,
        @Part driving_license: MultipartBody.Part,
        @Part passport: MultipartBody.Part,
        @Part voter_id: MultipartBody.Part,
        @Part electricity_bill: MultipartBody.Part
    ): Observable<Response<JsonObject>>

    @Multipart
    @POST(UPLOAD_CPIMAGES)
    fun uploadImage11(
        @Header("AuthorizeTokenKey") token: String,
        @Part profile: MultipartBody.Part,
        @Part("user_id") user_id: RequestBody,
        @Part("user_type") user_type: RequestBody,
        @Part("data") data: RequestBody,
        @Part pan: MultipartBody.Part,
        @Part aadhar_card: MultipartBody.Part,
        @Part driving_license: MultipartBody.Part,
        @Part passport: MultipartBody.Part,
        @Part voter_id: MultipartBody.Part,
        @Part electricity_bill: MultipartBody.Part
    ): Observable<Response<JsonObject>>

    @Multipart
    @POST(UPLOAD_CPIMAGES)
    fun uploadImages(
        @Header("AuthorizeTokenKey") token: String,
        @Part profile: MultipartBody.Part,
        @Part("user_id") user_id: RequestBody,
        @Part("user_type") user_type: RequestBody,
        @Part("data") data: RequestBody,
        @Part images: ArrayList<MultipartBody.Part>
    ): Observable<Response<JsonObject>>

    @Multipart
    @POST(UPLOAD_CPIMAGES)
    fun uploadSPImages(
        @Header("AuthorizeTokenKey") token: String,
        @Part profile: MultipartBody.Part,
        @Part("user_id") user_id: RequestBody,
        @Part("user_type") user_type: RequestBody,
        @Part pan: MultipartBody.Part,
        @Part aadhar_card: MultipartBody.Part,
        @Part driving_license: MultipartBody.Part,
        @Part passport: MultipartBody.Part,
        @Part voter_id: MultipartBody.Part,
        @Part electricity_bill: MultipartBody.Part,
        @Part shop_license: MultipartBody.Part,
        @Part images: ArrayList<MultipartBody.Part>
    ): Observable<Response<JsonObject>>

    @Multipart
    @POST(UPLOAD_CPIMAGES)
    fun uploadImage(
        @Header("AuthorizeTokenKey") token: String,
        @Part profile: MultipartBody.Part,
        @Part("user_id") user_id: RequestBody,
        @Part("user_type") user_type: RequestBody
    ): Observable<Response<JsonObject>>

    @FormUrlEncoded
    @POST(GET_CHANNEL_PARTNER_NETWORKLIST)
    fun getChannelPartnerNetworkList(
        @Header("AuthorizeTokenKey") token: String,
        @Field(
            value = "searchKeyword",
            encoded = false
        ) searchKeyword: String,
        @Field(
            value = "levelId",
            encoded = false
        ) levelId: Int,
        @Field(
            value = "generationId",
            encoded = false
        ) generationId: Int
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_DIRECT_GARAGELIST)
    fun getDirectGarageList(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_INDIRECT_GARAGELIST)
    fun getIndirectGarageList(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @GET(GET_CPPROMOTION_DETAILS)
    fun getCpPromotionDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Path("id") id: Int
    ): Observable<Response<PromotionDetailResponse>>

    @POST(GET_CPNETWORK_SERVICECOUNT)
    fun getCpNetworkServiceCount(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<NetworkServiceCountResponse>>

    @POST(GET_DIRECT_CCLASSES)
    fun getDirectcClasses(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_INDIRECT_CLASSES)
    fun getIndirectClasses(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_DIRECT_TEACHER)
    fun getDirectTeacher(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_INDIRECT_TEACHERS)
    fun getIndirectTeachers(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_DIRECT_STUDENT)
    fun getDirectStudent(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_INDIRECT_STUDENTS)
    fun getIndirectStudents(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_DIRECT_CAREERE_EXPERTS)
    fun getDirectCareereExperts(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_INDIRECT_CAREER_EXPERTS)
    fun getIndirectCareerExperts(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_DIRECT_CPLIST)
    fun getDirectCpList(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @POST(GET_INDIRECT_CPLIST)
    fun getIndirectCPList(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<OTGSChannelPartnerResponse>>

    @GET(GET_ALL_SERVICE_MASTERLIST)
    fun getAllServiceMasterList(
        @Header("AuthorizeTokenKey") token: String,
        @Query("user_type") user_type: String
    ): Observable<Response<AutoHubResponse>>

    @POST(GET_CHANNEL_PARTNER_DETAILS)
    fun getChannelPartnerDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: OTGSChannelPartnerRequest
    ): Observable<Response<ProfileDetailsResponse>>

    @GET(GET_CHANNEL_PARTNER_BY_ID)
    fun GetChannelPartnerById(
        @Header("AuthorizeTokenKey") token: String
    ): Observable<Response<ProfileResponse>>

    @POST(EDIT_PROFILE)
    fun EditProfile(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: EditProfileRequest
    ): Observable<Response<JsonObject>>

    @FormUrlEncoded
    @POST(RESET_PASSWORD)
    fun resetPassword(
        @Header("AuthorizeTokenKey") token: String,
        @Field(
            value = "NewPassword",
            encoded = false
        ) newPwd: String,
        @Field(
            value = "OldPassword",
            encoded = false
        ) oldPwd: String
    ): Observable<Response<JsonObject>>

    @GET(GET_ALL_STATES)
    fun getAllStates(
        @Header("AuthorizeTokenKey") token: String
    ): Observable<Response<StateResponse>>

    @GET(GET_CITIES)
    fun getCities(
        @Header("AuthorizeTokenKey") token: String,
        @Path("id") state_id: Int
    ): Observable<Response<StateResponse>>

    @GET(GET_PINCODES)
    fun getPincodes(
        @Header("AuthorizeTokenKey") token: String,
        @Path("id") city_id: Int
    ): Observable<Response<StateResponse>>

    @GET(GET_CHANNEL_PARTNER_DETAIL)
    fun getChannelPartnerDetail(
        @Header("AuthorizeTokenKey") token: String,
        @Path("id") id: Int
    ): Observable<Response<CPDetailResponse>>

    @GET(GET_SERVICE_PROVIDERDETAILS)
    fun getServiceProviderDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Query("service_provider_id") service_provider_id: String,
        @Query("cust_id") cust_id: String
    ): Observable<Response<ServiceProviderResponse>>

    @POST(GET_STUDENT_DETAILS)
    fun getStudentDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<StudentResponse>>

    @POST(GET_TEACHER_DETAILS)
    fun getTeacherDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<ClassDetailResponse>>

    @POST(GET_CLASS_DETAILS)
    fun getClassDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<ClassDetailResponse>>

    @POST(GET_CAREEREXPERT_DETAILS)
    fun getCareerExpertDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<ClassDetailResponse>>

    @POST(GET_NOTIFICATION_FILTERCP)
    fun getnotificationFilterCP(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: NotificationRequest
    ): Observable<Response<NotificationResponse>>

    @POST(GET_NOTIFICATION_FILTER_CLASSBOOK)
    fun getnotificationfilterclassbook(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: NotificationRequest
    ): Observable<Response<NotificationResponse>>

    @POST(GET_GARAGECOMMUNICATION_RENEWSERVICELIST)
    fun getGarageCommunicationRenewServiceList(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: RenewalRequest
    ): Observable<Response<PackageRerquestResponse>>

    @POST(GET_GARAGECOMMUNICATION_ADDSERVICELIST)
    fun getGarageCommunicationAddServiceList(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: RenewalRequest
    ): Observable<Response<PackageRerquestResponse>>

    @POST(GET_CPCOMMUNICATION_APPROVEREJECTLIST)
    fun getCPCommunicationApproveRejectList(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: HistoryRequest
    ): Observable<Response<PackageRerquestResponse>>

    @GET(GET_ALLYEARS)
    fun getYears(@Header("AuthorizeTokenKey") token: String): Observable<Response<YearResponse>>

    @GET(GET_ALLMONTHS)
    fun getMonths(@Header("AuthorizeTokenKey") token: String): Observable<Response<MonthResponse>>

    @GET(GET_ALL_TOMONTHS)
    fun getAllToMonths(
        @Header("AuthorizeTokenKey") token: String,
        @Query("month_id") month_id: String
    ): Observable<Response<MonthResponse>>

    @POST(FILTER_CP_INCOMEDETAILS)
    fun filtercpincomedetails(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: IncomeDetailRequest
    ): Observable<Response<IncomeDetail>>

    @POST(CP_NET_INCOMEDETAILS)
    fun cpNetIncomeDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: IncomeDetailRequest1
    ): Observable<Response<IncomeDetail>>

    @POST(CP_MONTHLY_INCOMEDETAILS)
    fun cpMonthlyIncomeDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: MonthlyIncomeRequest
    ): Observable<Response<MonthlyIncomeDetailResponse>>

    @POST(REG_CHANNELPARTNER)
    fun regChannelPartner(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: CPRegRequest
    ): Observable<Response<JsonObject>>

    @GET(GET_ALLBOARD)
    fun getAllBoard(@Header("AuthorizeTokenKey") token: String): Observable<Response<StateResponse>>

    @GET(GET_ALLMEDIUMS)
    fun getAllMediums(@Header("AuthorizeTokenKey") token: String): Observable<Response<StateResponse>>

    @GET(GET_ALLSTANDARDS)
    fun getAllStandards(@Header("AuthorizeTokenKey") token: String): Observable<Response<StateResponse>>

    @GET(GET_ALLCATEGORY)
    fun getAllCategory(@Header("AuthorizeTokenKey") token: String): Observable<Response<StateResponse>>

    @GET(GET_ALLSUBJECTSPECIALITIES)
    fun getAllSubjectSpecialities(@Header("AuthorizeTokenKey") token: String): Observable<Response<StateResponse>>

    @POST(SEND_OTP)
    fun SendOTP(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<JsonObject>>

    @POST(REG_SERVICEPROVIDER)
    fun regServiceProvider(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: AutoHubRequest
    ): Observable<Response<JsonObject>>

    @POST(CALCULATE_COMMISION)
    fun CalculateCommision(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: CommissionRequest
    ): Observable<Response<JsonObject>>

    @POST(GET_GARAGE_COMMUNICATIONDETAILS)
    fun getGarageCommunicationDetails(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<RenewalDetailResponse>>

    @POST(APPROVE_COMMUNICATION)
    fun approveCommunication(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<JsonObject>>

    @POST(REJECT_COMMUNICATION)
    fun rejectCommunication(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<JsonObject>>

    @POST(GET_KYCLIST)
    fun getKycList(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<KyCResponse>>

    @GET(LEVELCHART_DETAILS)
    fun levelChartDetails(@Header("AuthorizeTokenKey") token: String): Observable<Response<LevelResponse>>

    @GET(GET_ALLCATEGORIES)
    fun getAllCategories(@Header("AuthorizeTokenKey") token: String): Observable<Response<ContactUsResponse>>

    @POST(SUBMIT_QUERY)
    fun submitQuery(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<JsonObject>>

    @POST(GET_ALLFAQ)
    fun getAllFaq(
        @Header("AuthorizeTokenKey") token: String
    ): Observable<Response<FAQResponse>>

    @GET(PACKAGE_VALUES)
    fun packageValues(@Header("AuthorizeTokenKey") token: String): Observable<Response<PackageValueResponse>>

    @GET(PACKAGE_VALUE_CLASSBOOK)
    fun packageValueClassbook(@Header("AuthorizeTokenKey") token: String): Observable<Response<ClassbookResponse>>

    @GET(GET_ALLGENERATION)
    fun getAllGeneration(@Header("AuthorizeTokenKey") token: String): Observable<Response<GenerationResponse>>

    @GET(GET_ALLLEVELS)
    fun getAllLevels(@Header("AuthorizeTokenKey") token: String): Observable<Response<LevelResponseX>>

    @POST(GET_STUDENTSUBSCRIPTION)
    fun getStudentSubscription(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<SubscrpitionResponse>>

    @GET(GET_ALLBOARDSTANDARDS)
    fun getAllBoardStandards(
        @Header("AuthorizeTokenKey") token: String
    ): Observable<Response<BoardStandardResponse>>

    @GET(GET_VEHICLESUBTYPES)
    fun getVehicleSubTypes(
        @Header("AuthorizeTokenKey") token: String,
        @Query("vehicle_type_id") vehicle_type_id: String
    ): Observable<Response<VehicleSubTypeResponse>>

    @GET(GET_VEHICLEBRANDS)
    fun getVehicleBrands(
        @Header("AuthorizeTokenKey") token: String,
        @Query("vehicle_subtype_id") vehicle_subtype_id: String
    ): Observable<Response<VehicleBrandResponse>>

    @GET(GET_ALLCOURSES)
    fun getAllCourses(
        @Header("AuthorizeTokenKey") token: String
    ): Observable<Response<StateResponse>>

    @POST(GET_CP_INCOMEEARNED_FROMCLASS)
    fun getCpIncomeEarnedFromClass(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<StatisticsResponse>>

    @POST(GET_CP_MONTHLY_EXTRAINCOME)
    fun getCpMonthlyExtraIncome(
        @Header("AuthorizeTokenKey") token: String,
        @Body jsonObject: JsonObject
    ): Observable<Response<IndividualResponse>>
}