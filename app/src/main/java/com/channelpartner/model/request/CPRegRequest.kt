package com.channelpartner.model.request

data class CPRegRequest(
    val channelpartner_id: String,
    val Address: String,
    val CityId: String,
    val email: String,
    val AlternateContact: String,
    val ContactNo: String,
    val Pincode: String,
    val StateId: String,
    val DOB: String,
    val FirstName: String,
    val LastName: String,
    val Gender: Int,
    val RegistrationFromTypeId: Int,
    val RegistrationByTypeId: Int
)