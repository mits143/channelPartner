package com.channelpartner.model.request

import com.google.gson.annotations.SerializedName

data class ClassRegRequest(
    @SerializedName("Address")
    var address: String, // 46,Udhana Surat
    @SerializedName("AlternateContact")
    var alternateContact: String, // 9909260584
    @SerializedName("ApprovalDate")
    var approvalDate: String, // 10/08/1994
    @SerializedName("ApproveStatus")
    var approveStatus: Boolean, // true
    @SerializedName("BoardId")
    var boardId: Int, // 1
    @SerializedName("CityId")
    var cityId: Int, // 2
    @SerializedName("ContactNo")
    var contactNo: String, // 8141624468
    @SerializedName("DOB")
    var dOB: String, // 10/08/1994
    @SerializedName("Description")
    var description: String, // Latest Class
    @SerializedName("email")
    var email: String, // r5848@gmail.com
    @SerializedName("EstablishmentDate")
    var establishmentDate: String, // 10/08/1994
    @SerializedName("FirstName")
    var firstName: String, // FirstName
    @SerializedName("Gender")
    var gender: String, // Male
    @SerializedName("LastName")
    var lastName: String, // LastName
    @SerializedName("MediumId")
    var mediumId: Int, // 1
    @SerializedName("Name")
    var name: String, // Chate11 Classes
    @SerializedName("Pincode")
    var pincode: String, // 394210
    @SerializedName("ReferCode")
    var referCode: String, // Class101
    @SerializedName("RegistrationByTypeId")
    var registrationByTypeId: Int, // 1
    @SerializedName("RegistrationFromTypeId")
    var registrationFromTypeId: Int, // 1
    @SerializedName("RegistrationNo")
    var registrationNo: String, // RR9909260584
    @SerializedName("StandardId")
    var standardId: Int, // 1
    @SerializedName("StateId")
    var stateId: Int, // 1
    @SerializedName("TeachingExperience")
    var teachingExperience: String, // 1.2 years
    @SerializedName("UniqueNo")
    var uniqueNo: String // Class101
)