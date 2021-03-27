package com.channelpartner.model.response

data class CategoryResponse(
    val AllCategories: List<CategoryData>,
    val message: String
)