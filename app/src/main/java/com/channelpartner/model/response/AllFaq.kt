package com.channelpartner.model.response

data class AllFaq(
    var type: String,
    var question: String,
    var answer: String,
    var active: Boolean,
    var id: Int

)