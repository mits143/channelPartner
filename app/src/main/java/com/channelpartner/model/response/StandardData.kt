package com.channelpartner.model.response

data class StandardData(
    val std_id: Int,
    val standard: String,
    var isChecked: Boolean
)