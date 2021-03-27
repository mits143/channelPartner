package com.channelpartner.model.response


import com.google.gson.annotations.SerializedName

data class Data(
    var email: String,
    var firstname: String,
    var is_first_atempt: Int,
    var lastname: String,
    var message: String,
    var mobile_no: String,
    var status: String,
    var uniqueNo: String,
    var userId: String,
    var usertype: String,
    var profile_photo: String,
    var token: String,
    var authorizeTokenKey: String
)