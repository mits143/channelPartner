package com.channelpartner.model.response

data class PackageDetail(
    var amount: String,
    var class_name: String,
    var gst: String,
    var internet_handling_cost: String,
    var paid_date: String,
    var sr_no: Int,
    var std: String,
    var total: String
)