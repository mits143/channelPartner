package com.channelpartner

interface Constants {
    companion object {
        const val BASE_URL = "http://otgsservices-001-site1.itempurl.com/api/"
        const val NEW_BASE_URL = "http://otgsservices-001-site1.itempurl.com/api/v1/"
        const val IMAGE_URL = "http://onthegoservices.in/"
        const val LOGIN = "Common/Login"
        const val FORGOT_PASSWORD = "Common/ForgotPassword"
        const val REG_CLASS = "Registration/ClassesRegister"
        const val REG_TEACHER = "Registration/TeacherRegister"
        const val REG_CAREER_EXPERTS = "Registration/CareerExpertRegister"
        const val REG_STUDENT = "Registration/StudentRegister"
        const val CHANNELPARTNER_REGISTER = "ChannelPartner/Register"
        const val UPLOAD_CPIMAGES = "uploadCpImages"
        const val GET_ALL_STATES = "Common/GetStates"
        const val GET_CITIES = "Common/GetCitiesByStateId/{id}"
        const val GET_PINCODES = "Common/GetPincodeByCityId/{id}"
        const val EDIT_PROFILE = "EditProfile"
        const val GET_CHANNEL_PARTNER_DETAILS = "getChannelPartnerDetails"
        const val GET_CHANNEL_PARTNER_BY_ID = "ChannelPartner/GetChannelPartnerById"
        const val GET_ALL_SERVICE_MASTERLIST = "GetAllServiceMasterList?"
        const val GET_CHANNEL_PARTNER_NETWORKLIST = "ChannelPartner/GetChannelPartnersList"
        const val GET_DIRECT_GARAGELIST = "getDirectGarageList"
        const val GET_INDIRECT_GARAGELIST = "getIndirectGarageList"
        const val GET_CPPROMOTION_DETAILS = "ChannelPartner/GetChannelPartnerPromotion/{id}"
        const val GET_CPNETWORK_SERVICECOUNT = "getCpNetworkServiceCount"
        const val GET_DIRECT_CCLASSES = "getDirectcClasses"
        const val GET_INDIRECT_CLASSES = "getIndirectClasses"
        const val GET_DIRECT_TEACHER = "getDirectTeacher"
        const val GET_INDIRECT_TEACHERS = "getIndirectTeachers"
        const val GET_DIRECT_STUDENT = "getDirectStudent"
        const val GET_INDIRECT_STUDENTS = "getIndirectStudents"
        const val GET_DIRECT_CAREERE_EXPERTS = "getDirectCareereExperts"
        const val GET_INDIRECT_CAREER_EXPERTS = "getIndirectCareerExperts"
        const val RESET_PASSWORD = "Common/ChangePassword"
        const val GET_CHANNEL_PARTNER_DETAIL = "ChannelPartner/GetChannelPartnerById/{id}"
        const val GET_SERVICE_PROVIDERDETAILS = "getServiceProviderDetails"
        const val GET_DIRECT_CPLIST = "getDirectCpList"
        const val GET_INDIRECT_CPLIST = "getIndirectCPList"
        const val GET_STUDENT_DETAILS = "getStudentDetails"
        const val GET_TEACHER_DETAILS = "getTeacherDetails"
        const val GET_CLASS_DETAILS = "getClassDetails"
        const val GET_CAREEREXPERT_DETAILS = "getCareerExpertDetails"
        const val GET_NOTIFICATION_FILTERCP = "notificationFilterCP"
        const val GET_NOTIFICATION_FILTER_CLASSBOOK = "notificationFilterClassbook"
        const val GET_GARAGECOMMUNICATION_RENEWSERVICELIST =
            "getGarageCommunicationRenewServiceList"
        const val GET_GARAGECOMMUNICATION_ADDSERVICELIST = "getGarageCommunicationAddServiceList"
        const val GET_CPCOMMUNICATION_APPROVEREJECTLIST = "getCPCommunicationApproveRejectList"
        const val GET_ALLYEARS = "Common/GetYears"
        const val GET_ALLMONTHS = "Common/GetMonths"
        const val GET_ALL_TOMONTHS = "getAllToMonths"
        const val FILTER_CP_INCOMEDETAILS = "filterCpIncomeDetails"
        const val CP_NET_INCOMEDETAILS = "cpNetIncomeDetails"
        const val CP_MONTHLY_INCOMEDETAILS = "CPMonthlyIncomeDetails"
        const val REG_CHANNELPARTNER = "regChannelPartner"
        const val GET_ALLBOARD = "Common/GetBoards"
        const val GET_ALLMEDIUMS = "Common/GetMediums"
        const val GET_ALLSTANDARDS = "Common/GetBoards"
        const val GET_ALLCATEGORY = "Common/GetBoards"
        const val GET_ALLSTANDARD = "Common/GetStandard"
        const val GET_ALLSUBJECTSPECIALITIES = "Common/GetSubjectSpeciality"
        const val SEND_OTP = "SendOTP"
        const val REG_SERVICEPROVIDER = "regServiceProvider"
        const val CALCULATE_COMMISION = "CalculateCommision"
        const val GET_GARAGE_COMMUNICATIONDETAILS = "getGarageCommunicationDetails"
        const val APPROVE_COMMUNICATION = "approveCommunication"
        const val REJECT_COMMUNICATION = "rejectCommunication"
        const val GET_KYCLIST = "getKycList"
        const val LEVELCHART_DETAILS = "Common/GetLevelChartInformations"
        const val GET_ALLCATEGORIES = "getAllCategories"
        const val SUBMIT_QUERY = "submitQuery"
        const val GET_ALLFAQ = "Common/GetFAQs"
        const val PACKAGE_VALUES = "packageValues"
        const val PACKAGE_VALUE_CLASSBOOK = "packageValueClassbook"
        const val GET_ALLGENERATION = "Common/GetGenerations"
        const val GET_ALLLEVELS = "Common/GetLevels"
        const val GET_STUDENTSUBSCRIPTION = "getStudentSubscription"
        const val GET_ALLBOARDSTANDARDS = "getAllBoardStandards"
        const val GET_VEHICLESUBTYPES = "getVehicleSubTypes?"
        const val GET_VEHICLEBRANDS = "getVehicleBrands?"
        const val GET_ALLCOURSES = "getAllCourses"
        const val GET_CP_INCOMEEARNED_FROMCLASS = "getCpIncomeEarnedFromClass"
        const val GET_CP_MONTHLY_EXTRAINCOME = "getCpMonthlyExtraIncome"
        const val ABOUT_US = "Common/GetTermsAndConditionsPage"

        const val SECRET_KEY = "ChannelPartner-y18PJltrUUfTYFfgvUpNkIs7YBLfHRA1"
        const val KEY_AUTHTOKEN = "authtoken"
        const val KEY_TOKEN = "token"
        const val KEY_USERID = "userid"
        const val KEY_USERTYPE = "usertype"
        const val KEY_EMAIL = "email"
        const val KEY_NAME = "name"
        const val KEY_UNIQUENO = "uniqueno"
        const val IS_FIRST_ATEMPT = "is_first_atempt"
        const val KEY_MOBILE_NO = "mobile_no"
        const val KEY_PROFILE_PHOTO = "profile_photo"
    }
}