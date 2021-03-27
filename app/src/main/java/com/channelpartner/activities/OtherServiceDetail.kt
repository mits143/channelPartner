package com.channelpartner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.channelpartner.R
import com.channelpartner.activities.AutoHub.Companion.allServiceDetailList
import com.channelpartner.adapter.BrandsAdapter
import com.channelpartner.model.request.AutoHub.*
import com.channelpartner.model.response.BrandData
import com.otgs.customerapp.model.response.VehicleBrandData
import kotlinx.android.synthetic.main.activity_other_service_detail.*
import kotlinx.android.synthetic.main.activity_other_service_detail.btnDone
import kotlinx.android.synthetic.main.activity_other_service_detail.chkBike
import kotlinx.android.synthetic.main.activity_other_service_detail.chkFourWheeler
import kotlinx.android.synthetic.main.activity_other_service_detail.chkMoped
import kotlinx.android.synthetic.main.activity_other_service_detail.chkTwoWheeler
import kotlinx.android.synthetic.main.activity_other_service_detail.chkWithGear
import kotlinx.android.synthetic.main.activity_other_service_detail.chkWithoutGear
import kotlinx.android.synthetic.main.activity_other_service_detail.llVehicletype
import kotlinx.android.synthetic.main.activity_other_service_detail.llVehicletype1
import kotlinx.android.synthetic.main.activity_other_service_detail.rvBrands
import kotlinx.android.synthetic.main.activity_package.*
import kotlinx.android.synthetic.main.activity_service_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class OtherServiceDetail : AppCompatActivity() {

    var brandsAdapter: BrandsAdapter? = null
    var brandDataList: ArrayList<VehicleBrandData> = arrayListOf()


    var serviceDetailList: ArrayList<ServiceDetail> = arrayListOf()
    var brandList: ArrayList<Brand> = arrayListOf()
    var doorstepServiceTypeList: ArrayList<DoorstepServiceType> = arrayListOf()
    var pickupDetailListc: ArrayList<PickupDetail> = arrayListOf()
    var dropDetailList: ArrayList<DropDetail> = arrayListOf()
    var vehicleTypeList: ArrayList<VehicleType> = arrayListOf()
    var serviceContactDetailList: ArrayList<ServiceContactDetail> = arrayListOf()
    var sparePartList: ArrayList<SparePartType> = arrayListOf()

    var service_ID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_service_detail)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.other_services)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        init()
    }

    fun init() {

        if (intent.extras!! != null) {
            service_ID = intent.extras!!.get("type").toString()

            chkTwoWheeler.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    llVehicletype.visibility = View.VISIBLE
                } else {
                    llVehicletype.visibility = View.GONE
                }
            }

            chkFourWheeler.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    llVehicletype1.visibility = View.VISIBLE
                } else {
                    llVehicletype1.visibility = View.GONE
                }
            }
            val layoutManager = GridLayoutManager(this, 3)
            rvBrands!!.setLayoutManager(layoutManager)
            brandsAdapter = BrandsAdapter(
                this,
                brandDataList!!,
                { brandList -> onClick(brandList) })

            rvBrands!!.setAdapter(brandsAdapter)

            btnDone.setOnClickListener {
                submit()
            }
        }
    }

    fun submit() {
        brandList.clear()
        doorstepServiceTypeList.clear()
        pickupDetailListc.clear()
        dropDetailList.clear()
        vehicleTypeList.clear()
        serviceContactDetailList.clear()
        sparePartList.clear()

        if (!chkTwoWheeler.isChecked && !chkFourWheeler.isChecked) {
            Toast.makeText(this, "Select service", Toast.LENGTH_LONG).show()
            return
        }

        if (chkTwoWheeler.isChecked) {
            if (!chkBike.isChecked && !chkMoped.isChecked) {
                Toast.makeText(this, "Select two wheeler vehicle type", Toast.LENGTH_LONG)
                    .show()
                return
            }
        }

        if (chkFourWheeler.isChecked) {
            if (!chkWithGear.isChecked && !chkWithoutGear.isChecked) {
                Toast.makeText(this, "Select four Vehicle type", Toast.LENGTH_LONG).show()
                return
            }
        }


        if (!chkUsed.isChecked && !chkNew.isChecked){
            Toast.makeText(this, "Select spare parts type", Toast.LENGTH_LONG).show()
            return
        }

        if (chkUsed.isChecked){
            val SparePartType = SparePartType(chkUsed.text.toString().trim())
            sparePartList.add(SparePartType)
        }


        if (chkNew.isChecked){
            val SparePartType = SparePartType(chkNew.text.toString().trim())
            sparePartList.add(SparePartType)
        }

        if (chkBike.isChecked) {
            val vehicleType = VehicleType(chkBike.text.toString().trim())
            vehicleTypeList.add(vehicleType)
        }

        if (chkMoped.isChecked) {
            val vehicleType = VehicleType(chkMoped.text.toString().trim())
            vehicleTypeList.add(vehicleType)
        }

        if (chkWithGear.isChecked) {
            val vehicleType = VehicleType(chkWithGear.text.toString().trim())
            vehicleTypeList.add(vehicleType)
        }

        if (chkWithoutGear.isChecked) {
            val vehicleType = VehicleType(chkWithoutGear.text.toString().trim())
            vehicleTypeList.add(vehicleType)
        }



        if (brandDataList.isNotEmpty()) {
            for (i in brandDataList.indices) {
                if (brandDataList[i].isChecked) {
                    val brandData = Brand(brandDataList!![i].vehicle_brand_name)
                    brandList.add(brandData)
                }
            }
        }

        if (brandList.isEmpty()) {
            Toast.makeText(this, "Select brand", Toast.LENGTH_LONG).show()
            return
        }

        val ServiceDetail = ServiceDetail(
            "0",
            brandList,
            doorstepServiceTypeList,
            dropDetailList,
            "0",
            "0",
            pickupDetailListc,
            serviceContactDetailList,
            service_ID,
            sparePartList,
            vehicleTypeList
        )
        serviceDetailList.add(ServiceDetail)

        var allServiceDetail = AllServiceDetail(serviceDetailList)
        allServiceDetailList.add(allServiceDetail)

        var packageDetail = PackageDetail(
            "1000",
            "",
            service_ID,
            0
        )
        Package.packageDetailList.add(packageDetail!!)

        finish()

    }

    fun onClick(data: VehicleBrandData) {
        data.isChecked = !data.isChecked
        brandsAdapter!!.notifyDataSetChanged()
    }
}