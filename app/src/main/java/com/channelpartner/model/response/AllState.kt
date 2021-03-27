package com.channelpartner.model.response
import com.google.gson.annotations.SerializedName

data class AllState(
    @SerializedName("name")
    var name: String,
    @SerializedName("id")
    var id: Int
)