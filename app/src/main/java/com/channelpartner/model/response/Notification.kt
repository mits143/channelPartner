package com.channelpartner.model.response

data class Notification(
    val msg: String,
    val notification_id: Int,
    val order_id: Int,
    val read: String,
    val title: String
)