package com.channelpartner.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    var message: String,
    var data: Data
)