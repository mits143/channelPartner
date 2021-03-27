package com.channelpartner.model.request

data class OTGSChannelPartnerRequest(
    val page_number: Int,
    val cpid: String,
    val user_id: Int,
    val user_type: Int,
    val search_para: String,
    val generation_id: Int,
    val level_id: Int
)