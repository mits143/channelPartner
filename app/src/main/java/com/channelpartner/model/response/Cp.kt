package com.channelpartner.model.response

data class Cp(
    val communication_status: Int,
    val gagare_address: String,
    val gagare_mobile: String,
    val gagare_name: String,
    val history_id: Int,
    val request_date: String
)