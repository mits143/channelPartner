package com.channelpartner.model.response

data class All(
    var channelPartnerId: Int,
    var currentLevel: String,
    var incomePercentage: String,
    var promotionLevel: String,
    var target: String,
    var type: String
)