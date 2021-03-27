package com.channelpartner.model.response

data class SubscrpitionResponse(
    var career_experts: List<CareerExpert>,
    var classes: List<Classe>,
    var courses: List<Course>,
    var message: String,
    var teachers: List<Teacher>
)