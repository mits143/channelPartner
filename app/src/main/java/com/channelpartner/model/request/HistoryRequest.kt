package com.channelpartner.model.request

data class HistoryRequest(
    var end_date: String,
    var start_date: String,
    var status: Int,
    var user_id: Int,
    var user_type: Int
)