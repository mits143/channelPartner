package com.channelpartner.model.response

data class IndividualResponse(
    var static_table: List<StaticTable>,
    var total_ce_registered: Int,
    var total_class_registered: Int,
    var total_garage_registered: Int,
    var total_income: String,
    var total_student_registered: Int,
    var total_teacher_registered: Int,
    var total_turnover: String
)