package com.channelpartner.model.request

data class CommissionRequest(
    var date_of_joining: String,
    var name: String,
    var paid_amount: String,
    var parent_id: Int,
    var shop_type: String,
    var user_id: Int,
    var user_type: Int
)