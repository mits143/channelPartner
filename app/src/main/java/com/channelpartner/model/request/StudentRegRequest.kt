package com.channelpartner.model.request

data class StudentRegRequest(
    val amount: String,
    val board_id: Int,
    val contact_address: String,
    val contact_city: Int,
    val contact_email_id: String,
    val contact_pincode: Int,
    val contact_state: Int,
    val created_by: Int,
    val date_of_birth: String,
    val first_name: String,
    val gender: Int,
    val gst: String,
    val internet_handling_cost: String,
    val last_name: String,
    val medium_id: Int,
    val mobile_number: String,
    val std_id: Int,
    val total: String
)