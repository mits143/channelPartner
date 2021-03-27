package com.channelpartner.model.response.SeviceProvider

data class ServiceProviderResponse(
    var documentDetails: List<Any>,
    var gallaryImages: List<Any>,
    var garageInformation: GarageInformation,
    var message: String,
    var packageDetails: ArrayList<PackageDetail>,
    var paymentModes: List<PaymentMode>,
    var reviewDetails: List<Any>,
    var serviceDetails: List<ServiceDetail>,
    var workingHours: List<WorkingHour>
)