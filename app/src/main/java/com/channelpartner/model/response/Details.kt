package com.channelpartner.model.response

data class Details(
    val account_holder_name: String,
    val account_number: String,
    val bank_name: String,
    val branch_name: String,
    val channelpartner_id: Int,
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
    val ifsc_code: String,
    val last_name: String,
    val mobile_number: String,
    val nominee_email_id: String,
    val nominee_firstName: String,
    val nominee_lastName: String,
    val nominee_mobile_no: String,
    val profile_image: String,
    val pancard_number: String,
    val gender: Int
)