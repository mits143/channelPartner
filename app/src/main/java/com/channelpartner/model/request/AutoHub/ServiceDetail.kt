package com.channelpartner.model.request.AutoHub

data class ServiceDetail(
    var area_limit: String,
    var brands: ArrayList<Brand>,
    var doorstep_service_type: ArrayList<DoorstepServiceType>,
    var drop_details: ArrayList<DropDetail>,
    var labour_cost: String,
    var pick_drop_flag: String,
    var pickup_details: ArrayList<PickupDetail>,
    var service_contact_details: ArrayList<ServiceContactDetail>,
    var service_id: String,
    var spare_part_type: ArrayList<SparePartType>,
    var vehicle_types: ArrayList<VehicleType>
)