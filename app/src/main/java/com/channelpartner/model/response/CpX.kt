package com.channelpartner.model.response

data class CpX(
    var communication_name: String,
    var communication_type: Int,
    var garage_address: String,
    var garage_mobile_no: String,
    var garage_name: String,
    var history_id: Int,
    var new_drop: List<Any>,
    var new_opening_closing_time: List<Any>,
    var new_pickup: List<Any>,
    var new_services: List<NewService>,
    var owner_name: String,
    var renewal_approved_rejecte_date: String,
    var renewal_date: String,
    var renewed_services: List<Any>,
    var status: Int
)