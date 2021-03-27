package com.channelpartner.model.request

data class ChangePwdRequest(
    val new_password: String,
    val old_password: String
)