package com.channelpartner.model.response.SeviceProvider

data class WorkingHour(
    var day: String,
    var from_time: String,
    var sp_id: Int,
    var sp_unique_number: String,
    var to_time: String
)