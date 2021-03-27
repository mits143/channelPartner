package com.channelpartner.model.response

data class DetailsXXX(
    var total_earned: String,
    var board_id: String,
    var contact_address: String,
    var contact_city: String,
    var contact_email_id: String,
    var contact_pincode: String,
    var contact_state: String,
    var created_by: Int,
    var date_of_birth: String,
    var firstName: String,
    var gender: String,
    var introducer_name: String,
    var lastName: String,
    var medium_id: String,
    var mobile_number: String,
    var package_details: List<PackageDetail>,
    var profile_photo: String,
    var std_id: String,
    var tota_gst: Double,
    var total_amount: Double,
    var total_ihc: Double,
    var unique_no: String
)