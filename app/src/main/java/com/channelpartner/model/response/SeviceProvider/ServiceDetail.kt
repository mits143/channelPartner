package com.channelpartner.model.response.SeviceProvider

data class ServiceDetail(
    var area_limit: String,
    var brands: List<Brand>,
    var dropDetails: List<Any>,
    var labour_cost: String,
    var pick_drop_flag: String,
    var pickupDetails: List<Any>,
    var serviceContactDetails: List<Any>,
    var service_id: Int,
    var spare_part_type: String,
    var vehicle_types: List<VehicleType>
)