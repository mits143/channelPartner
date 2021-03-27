package com.channelpartner.model.request.AutoHub

data class PackageDetail(
    val amount: String,
    val package_name: String,
    val service_id: String,
    val service_type: Int
)