package com.channelpartner.model.response

data class AutoHubResponse(
    val ServiceMasterLists: List<ServiceMasterLists>,
    val message: String,
    val status: String
)