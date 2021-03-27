package com.channelpartner.model.response

data class PackageRerquestResponse(
    val approve_reject_btn: String,
    val cp: List<Cp>,
    val msg: String
)