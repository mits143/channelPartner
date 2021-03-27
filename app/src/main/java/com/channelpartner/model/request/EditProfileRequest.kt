package com.channelpartner.model.request

data class EditProfileRequest(
    val contact_address: String,
    val contact_city: String,
    val contact_email_id: String,
    val contact_landline: String,
    val contact_landmark: String,
    val contact_mobile: String,
    val contact_pincode: String,
    val contact_state: String,
    val date_of_birth: String,
    val first_name: String,
    val last_name: String,
    val nominee_email_id: String,
    val nominee_firstName: String,
    val nominee_lastName: String,
    val nominee_mobile_no: String,
    val user_id: Int,
    val user_type: Int,
    val pancard_number: String
)