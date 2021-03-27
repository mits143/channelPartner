package com.channelpartner.model.response

data class ServiceMasterLists(
    var service_master_id: String,
    var service_name: String,
    var service_type: String,
    var service_check: Boolean
)