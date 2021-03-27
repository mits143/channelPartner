package com.channelpartner.model.response

data class AllBoard(
    var board: String,
    var board_id: Int,
    var standards: ArrayList<StandardData>
)