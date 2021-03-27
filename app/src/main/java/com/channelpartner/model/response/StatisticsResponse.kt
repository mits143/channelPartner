package com.channelpartner.model.response

data class StatisticsResponse(
    var amount_earned_class_self_stud: String,
    var amount_earned_class_stud_outside: String,
    var amount_earned_class_stud_regi_by_cp: String,
    var amount_earned_class_stud_regi_by_other_cp: String,
    var amount_earned_class_stud_subscribed: String,
    var class_self_stud: Int,
    var class_stud_outside: Int,
    var class_stud_regi_by_cp: Int,
    var class_stud_regi_by_other_cp: Int,
    var class_stud_subscribed: Int,
    var month_year: String,
    var total_earned: String
)