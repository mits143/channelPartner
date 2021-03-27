package com.channelpartner.model.request.AutoHub

data class AutoHubRequest(
    val all_service_details: List<AllServiceDetail>,
    val business_code: String,
    val channelpartner_id: String,
    val garage_information: GarageInformation,
    val gst: String,
    val internet_handling_cost: String,
    val package_details: List<PackageDetail>,
    val payment_mode: List<PaymentMode>,
    val total: String,
    val working_hours: ArrayList<WorkingHour>
)