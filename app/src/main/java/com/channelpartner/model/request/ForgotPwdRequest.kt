package com.channelpartner.model.request

data class ForgotPwdRequest(
    var username: String,
    var user_type: String
)