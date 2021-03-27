package com.channelpartner.model.request

data class NotificationRequest(
    val notification_type: String,
    val app_code: String,
    val page_number: String,
    val user_id: Int,
    val user_type: Int
)