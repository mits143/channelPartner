package com.channelpartner.model.response

data class CpDetails(
    val month: String,
    val year: String,
    val level_difference_income: Int,
    val net_income: Int,
    val residual_income: Int,
    val royalty_income: Int,
    val tds: Int,
    val total_income: Int
)