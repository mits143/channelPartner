package com.channelpartner.model.response

data class CategoryData(
    val category_id: Int,
    val category_name: String,
    var isChecked: Boolean
)