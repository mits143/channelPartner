package com.channelpartner.model.response

data class NotificationResponse(
    val Notification: List<Notification>,
    val msg: String,
    val total_pages: Int,
    val unread_messages: Int
)