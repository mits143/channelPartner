package com.channelpartner.model.request

data class MonthlyIncomeRequest(
    val cpid: Int,
    val month: Int,
    val user_id: Int,
    val user_type: Int,
    val year: Int
)