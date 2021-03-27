package com.channelpartner.view

import com.otgs.customerapp.model.response.VehicleBrandResponse
import com.otgs.customerapp.model.response.VehicleSubTypeResponse
import retrofit2.Response

interface ServiceDetailView {

    interface MainView {

        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccessVehicleSubType(responseModel: Response<VehicleSubTypeResponse>)
        fun onSuccessVehicleBrand(responseModel: Response<VehicleBrandResponse>)
        fun onError(errorCode: Int)
        fun onError(throwable: Throwable)
    }

    interface MainPresenter {

        fun loadVehicleSubTypeData(
            token: String,
            vehicle_type_id: String
        )

        fun loadVehicleBrandData(
            token: String,
            vehicle_subtype_id: String
        )

        fun onStop()
    }
}