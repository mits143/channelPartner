package com.channelpartner.model.response

data class PromotionDetailResponse(
    var CpDetails: CpDetailsX,
    var message: String,

    var achieved: Int,
    var currentLevel: String,
    var nextLevel: String,
    var pending: Int,
    var target: Int
)