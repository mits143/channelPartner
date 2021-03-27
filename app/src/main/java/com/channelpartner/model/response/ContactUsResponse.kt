package com.channelpartner.model.response

data class ContactUsResponse(
    var Allcat: List<Allcat>,
    var address: String,
    var email: String,
    var message: String,
    var phone: String,
    var website: String
)