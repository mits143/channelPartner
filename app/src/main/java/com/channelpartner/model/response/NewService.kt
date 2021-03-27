package com.channelpartner.model.response

data class NewService(
    var `package`: String,
    var package_amount: String,
    var service_name: String,
    var service_type: Int
)