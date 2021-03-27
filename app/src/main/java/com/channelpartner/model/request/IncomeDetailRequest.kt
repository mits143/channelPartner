package com.channelpartner.model.request

data class IncomeDetailRequest(
    val cpid: Int,
    val from_month: Int,
    val to_month: Int,
    val user_id: Int,
    val user_type: Int,
    val year: Int
)