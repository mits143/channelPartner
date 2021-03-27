package com.channelpartner.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.channelpartner.R
import com.channelpartner.activities.AutoHub.Companion.allServiceDetailList
import com.channelpartner.adapter.BrandsAdapter
import com.channelpartner.adapter.PickDropAdapter
import com.channelpartner.adapter.SubTypeVehicleAdapter
import com.channelpartner.model.request.AutoHub.*
import com.channelpartner.model.response.Pickdrp
import com.channelpartner.presenter.ServiceDetailPresenter
import com.channelpartner.view.ServiceDetailView
import com.kuber.vpn.Utils.sessionManager
import com.otgs.customerapp.model.response.VehicleBrandData
import com.otgs.customerapp.model.response.VehicleBrandResponse
import com.otgs.customerapp.model.response.VehicleSubTypeData
import com.otgs.customerapp.model.response.VehicleSubTypeResponse
import kotlinx.android.synthetic.main.activity_service_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response


class ServiceDetails : AppCompatActivity(), ServiceDetailView.MainView {

    var presenter: ServiceDetailPresenter? = null

    var VehicleSubTypeAdapter: SubTypeVehicleAdapter? = null
    var VehicleSubTypeAdapter1: SubTypeVehicleAdapter? = null

    var vehicleSubTypeList: ArrayList<VehicleSubTypeData>? = arrayListOf()
    var vehicleSubTypeList1: ArrayList<VehicleSubTypeData>? = arrayListOf()

    var serviceDetailList: ArrayList<ServiceDetail> = arrayListOf()

    var brandsAdapter: BrandsAdapter? = null
    var brandsAdapter1: BrandsAdapter? = null
    var brandDataList: ArrayList<VehicleBrandData> = arrayListOf()
    var brandDataList1: ArrayList<VehicleBrandData> = arrayListOf()

    var pickAdapter: PickDropAdapter? = null
    var pickList: ArrayList<Pickdrp> = arrayListOf()

    var dropAdapter: PickDropAdapter? = null
    var dropList: ArrayList<Pickdrp> = arrayListOf()

    var brandList: ArrayList<Brand> = arrayListOf()
    var doorstepServiceTypeList: ArrayList<DoorstepServiceType> = arrayListOf()
    var pickupDetailListc: ArrayList<PickupDetail> = arrayListOf()
    var dropDetailList: ArrayList<DropDetail> = arrayListOf()
    var vehicleTypeList: ArrayList<VehicleType> = arrayListOf()
    var serviceContactDetailList: ArrayList<ServiceContactDetail> = arrayListOf()
    var sparePartList: ArrayList<SparePartType> = arrayListOf()

    var service_ID = ""
    var pick_drop_flag = "0"
    var type = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.services)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        init()
    }

    fun init() {
        if (intent.extras!! != null) {
            service_ID = intent.extras!!.get("type").toString()

            presenter = ServiceDetailPresenter(this, this)

            if (TextUtils.equals(service_ID, "1")) {
                chkBike.text = "Bike"
                chkMoped.text = "Moped"

                txtTwoVehicleType.setText("Select 2 wheel vehicle type :-")
                txtTwoVehicleBrand.setText("Select 2 wheel brand :-")
                txtLabourcharge.setText("Enter 2 wheel labour charges:-")

                presenter!!.loadVehicleSubTypeData(
                    sessionManager.getAuthToken(this@ServiceDetails)!!,
                    "1"
                )
            }
            if (TextUtils.equals(service_ID, "2")) {
                chkBike.text = "With Gear"
                chkMoped.text = "Without Gear"

                txtTwoVehicleType.setText("Select 4 wheel vehicle type :-")
                txtTwoVehicleBrand.setText("Select 4 wheel brand :-")
                txtLabourcharge.setText("Enter 4 wheel labour charges:-")

                presenter!!.loadVehicleSubTypeData(
                    sessionManager.getAuthToken(this@ServiceDetails)!!,
                    "2"
                )
            }
            if (TextUtils.equals(service_ID, "3")) {
                llDoorServicing.visibility = View.VISIBLE
                llPick.visibility = View.GONE
                llDrop.visibility = View.GONE

                txtTwoVehicleType.setText("Select 2 wheel vehicle type :-")
                txtFourVehicleType.setText("Select 4 wheel vehicle type :-")

                txtTwoVehicleBrand.setText("Select 2 wheel brand :-")
                txtFourVehicleBrand.setText("Select 4 wheel brand :-")

                presenter!!.loadVehicleSubTypeData(
                    sessionManager.getAuthToken(this@ServiceDetails)!!,
                    "1"
                )
                chkTwoWheeler.setOnCheckedChangeListener { buttonView, isChecked ->
                    type = 1
                    if (isChecked) {
                        presenter!!.loadVehicleSubTypeData(
                            sessionManager.getAuthToken(this@ServiceDetails)!!,
                            "1"
                        )
                        rvType.visibility = View.VISIBLE
                        rvBrands.visibility = View.VISIBLE
                        llVehicletype.visibility = View.VISIBLE
                        llLabourCharge.visibility = View.VISIBLE
                        txtTwoVehicleType.visibility = View.VISIBLE
                        txtTwoVehicleBrand.visibility = View.VISIBLE
                    } else {
                        vehicleSubTypeList!!.clear()
                        brandDataList!!.clear()
                        rvType.visibility = View.GONE
                        rvBrands.visibility = View.GONE
                        llVehicletype.visibility = View.VISIBLE
                        llLabourCharge.visibility = View.GONE
                        txtTwoVehicleType.visibility = View.GONE
                        txtTwoVehicleBrand.visibility = View.GONE
                    }
                }

                chkFourWheeler.setOnCheckedChangeListener { buttonView, isChecked ->
                    type = 2
                    if (isChecked) {
                        presenter!!.loadVehicleSubTypeData(
                            sessionManager.getAuthToken(this@ServiceDetails)!!,
                            "2"
                        )
                        rvType1.visibility = View.VISIBLE
                        rvBrands1.visibility = View.VISIBLE
//                        llVehicletype1.visibility = View.VISIBLE
                        llLabourCharge1.visibility = View.VISIBLE
                        txtFourVehicleType.visibility = View.VISIBLE
                        txtFourVehicleBrand.visibility = View.VISIBLE
                    } else {
                        vehicleSubTypeList1!!.clear()
                        brandDataList1!!.clear()
                        rvType1.visibility = View.GONE
                        rvBrands1.visibility = View.GONE
//                        llVehicletype1.visibility = View.GONE
                        llLabourCharge1.visibility = View.GONE
                        txtFourVehicleType.visibility = View.GONE
                        txtFourVehicleBrand.visibility = View.GONE
                    }
                }
            }
        }

        val layoutManager = GridLayoutManager(this, 3)
        rvType!!.setLayoutManager(layoutManager)
        VehicleSubTypeAdapter = SubTypeVehicleAdapter(
            this,
            vehicleSubTypeList!!,
            { vehicleSubTypeList -> onClick(vehicleSubTypeList) })

        rvType!!.setAdapter(VehicleSubTypeAdapter)

        val lytManager = GridLayoutManager(this, 3)
        rvType1!!.setLayoutManager(lytManager)
        VehicleSubTypeAdapter1 = SubTypeVehicleAdapter(
            this,
            vehicleSubTypeList1!!,
            { vehicleSubTypeList1 -> onClick1(vehicleSubTypeList1) })

        rvType1!!.setAdapter(VehicleSubTypeAdapter1)

        val layoutManager1 = GridLayoutManager(this, 3)
        rvBrands!!.setLayoutManager(layoutManager1)
        brandsAdapter = BrandsAdapter(
            this,
            brandDataList!!,
            { brandDataList -> onClick(brandDataList) })

        rvBrands!!.setAdapter(brandsAdapter)

        val lytManager1 = GridLayoutManager(this, 3)
        rvBrands1!!.setLayoutManager(lytManager1)
        brandsAdapter1 = BrandsAdapter(
            this,
            brandDataList1!!,
            { brandDataList1 -> onClick1(brandDataList1) })

        rvBrands1!!.setAdapter(brandsAdapter1)

        pickList.add(Pickdrp("09:00 - 10:00", "", false))
        pickList.add(Pickdrp("10:00 - 11:00", "", false))
        pickList.add(Pickdrp("11:00 - 12:00", "", false))
        pickList.add(Pickdrp("12:00 - 13:00", "", false))
        pickList.add(Pickdrp("13:00 - 14:00", "", false))
        pickList.add(Pickdrp("14:00 - 15:00", "", false))
        pickList.add(Pickdrp("15:00 - 16:00", "", false))
        pickList.add(Pickdrp("17:00 - 18:00", "", false))
        pickList.add(Pickdrp("18:00 - 19:00", "", false))
        pickList.add(Pickdrp("19:00 - 20:00", "", false))
        pickList.add(Pickdrp("20:00 - 21:00", "", false))
        pickList.add(Pickdrp("21:00 - 22:00", "", false))
        val layoutManager2 = LinearLayoutManager(this)
        rvpick!!.setLayoutManager(layoutManager2)
        pickAdapter = PickDropAdapter(
            this,
            pickList!!,
            { pickList -> onClick(pickList) })

        rvpick!!.setAdapter(pickAdapter)

        rbYes.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (isChecked) {
                    rvpick.visibility = View.VISIBLE
                } else {
                    rvpick.visibility = View.GONE
                }
            }
        }

        dropList.add(Pickdrp("09:00 - 10:00", "", false))
        dropList.add(Pickdrp("10:00 - 11:00", "", false))
        dropList.add(Pickdrp("11:00 - 12:00", "", false))
        dropList.add(Pickdrp("12:00 - 13:00", "", false))
        dropList.add(Pickdrp("13:00 - 14:00", "", false))
        dropList.add(Pickdrp("14:00 - 15:00", "", false))
        dropList.add(Pickdrp("15:00 - 16:00", "", false))
        dropList.add(Pickdrp("17:00 - 18:00", "", false))
        dropList.add(Pickdrp("18:00 - 19:00", "", false))
        dropList.add(Pickdrp("19:00 - 20:00", "", false))
        dropList.add(Pickdrp("20:00 - 21:00", "", false))
        dropList.add(Pickdrp("21:00 - 22:00", "", false))
        val layoutManager3 = LinearLayoutManager(this)
        rvDrop!!.setLayoutManager(layoutManager3)
        dropAdapter = PickDropAdapter(
            this,
            dropList!!,
            { dropList -> onClick(dropList) })

        rvDrop!!.setAdapter(dropAdapter)

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            // This will get the radiobutton that has changed in its check state
            val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
            // This puts the value (true/false) into the variable

            if (checkedRadioButton == rbYes) {
                rvpick.visibility = View.VISIBLE
            } else {
                rvpick.visibility = View.GONE
            }
        })

        radioGroup1.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            // This will get the radiobutton that has changed in its check state
            val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
            // This puts the value (true/false) into the variable

            if (checkedRadioButton == rbYes1) {
                rvDrop.visibility = View.VISIBLE
            } else {
                rvDrop.visibility = View.GONE
            }
        })


        btnDone.setOnClickListener {
            submit()
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

        if (!TextUtils.equals(service_ID, "3")) {
//            if (!chkBike.isChecked && !chkMoped.isChecked) {
//                Toast.makeText(this, "Select vehicle type", Toast.LENGTH_LONG).show()
//                return
//            }

            for (i in vehicleSubTypeList!!.indices) {
                if (vehicleSubTypeList!![i].isChecked) {
                    val vehicleType = VehicleType(vehicleSubTypeList!![i].vehicle_subtype_name)
                    vehicleTypeList.add(vehicleType)

                }
            }

            if (vehicleTypeList.isEmpty()){
                Toast.makeText(this, "Select vehicle type", Toast.LENGTH_LONG).show()
                return
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

            if (TextUtils.isEmpty(edtLabourCharges.text.toString().trim())) {
                Toast.makeText(this, "Select labour charges", Toast.LENGTH_LONG).show()
                return
            }

            if (rbYes.isChecked) {
                for (i in pickList.indices) {
                    if (pickList[i].isChecked) {
                        pick_drop_flag = "1"
                        val pickupDetail =
                            PickupDetail(pickList!![i].pickdropTime, pickList!![i].pickdropSlot)
                        pickupDetailListc.add(pickupDetail)
                    }
                }
            }

            if (rbYes1.isChecked) {
                for (i in dropList.indices) {
                    if (dropList[i].isChecked) {
                        val dropDetail =
                            DropDetail(dropList!![i].pickdropTime, dropList!![i].pickdropSlot)
                        dropDetailList.add(dropDetail)
                    }
                }
            }

//            if (chkBike.isChecked) {
//                val vehicleType = VehicleType(chkBike.text.toString().trim())
//                vehicleTypeList.add(vehicleType)
//            }
//
//            if (chkMoped.isChecked) {
//                val vehicleType = VehicleType(chkMoped.text.toString().trim())
//                vehicleTypeList.add(vehicleType)
//            }

            if (rbYes.isChecked) {
                if (pickupDetailListc.isEmpty()) {
                    Toast.makeText(this, "Select Pick Up time", Toast.LENGTH_LONG).show()
                    return
                }
            }

            if (rbYes1.isChecked) {
                if (dropDetailList.isEmpty()) {
                    Toast.makeText(this, "Select Drop time", Toast.LENGTH_LONG).show()
                    return
                }
            }
        } else {
            if (!chkTwoWheeler.isChecked && !chkFourWheeler.isChecked) {
                Toast.makeText(this, "Select Doorstep service", Toast.LENGTH_LONG).show()
                return
            }


            var vehicleTypeList2Wheel: ArrayList<VehicleType> = arrayListOf()
            for (i in vehicleSubTypeList!!.indices) {
                if (vehicleSubTypeList!![i].isChecked) {
                    val vehicleType = VehicleType(vehicleSubTypeList!![i].vehicle_subtype_name)
                    vehicleTypeList.add(vehicleType)
                    vehicleTypeList2Wheel.add(vehicleType)

                }
            }

            var vehicleTypeList4Wheel: ArrayList<VehicleType> = arrayListOf()
            for (i in vehicleSubTypeList1!!.indices) {
                if (vehicleSubTypeList1!![i].isChecked) {
                    val vehicleType = VehicleType(vehicleSubTypeList1!![i].vehicle_subtype_name)
                    vehicleTypeList.add(vehicleType)
                    vehicleTypeList4Wheel.add(vehicleType)

                }
            }

            if (chkTwoWheeler.isChecked) {
//                if (!chkBike.isChecked && !chkMoped.isChecked) {
//                    Toast.makeText(this, "Select two wheeler vehicle type", Toast.LENGTH_LONG)
//                        .show()
//                    return
//                }
                if (vehicleTypeList2Wheel.isEmpty()){
                    Toast.makeText(this, "Select two wheeler vehicle type", Toast.LENGTH_LONG)
                        .show()
                    return
                }
            }
//
            if (chkFourWheeler.isChecked) {
//                if (!chkWithGear.isChecked && !chkWithoutGear.isChecked) {
//                    Toast.makeText(this, "Select four Vehicle type", Toast.LENGTH_LONG).show()
//                    return
//                }
                if (vehicleTypeList4Wheel.isEmpty()){
                    Toast.makeText(this, "Select four wheeler vehicle type", Toast.LENGTH_LONG)
                        .show()
                    return
                }
            }

            var brandList2Wheel: ArrayList<Brand> = arrayListOf()
            if (brandDataList.isNotEmpty()) {
                for (i in brandDataList.indices) {
                    if (brandDataList[i].isChecked) {
                        val brandData = Brand(brandDataList!![i].vehicle_brand_name)
                        brandList2Wheel.add(brandData)
                        brandList.add(brandData)
                    }
                }
            }

            if (chkTwoWheeler.isChecked) {
                if (brandList2Wheel.isEmpty()) {
                    Toast.makeText(this, "Select two brand", Toast.LENGTH_LONG).show()
                    return
                }

            }

            var brandList4Wheel: ArrayList<Brand> = arrayListOf()
            if (brandDataList1.isNotEmpty()) {
                for (i in brandDataList1.indices) {
                    if (brandDataList1[i].isChecked) {
                        val brandData = Brand(brandDataList1!![i].vehicle_brand_name)
                        brandList4Wheel.add(brandData)
                        brandList.add(brandData)
                    }
                }
            }

            if (chkFourWheeler.isChecked) {
                if (brandList4Wheel.isEmpty()) {
                    Toast.makeText(this, "Select four wheel brand", Toast.LENGTH_LONG).show()
                    return
                }
            }

            if (chkTwoWheeler.isChecked) {
                if (TextUtils.isEmpty(edtLabourCharges.text.toString().trim())) {
                    Toast.makeText(this, "Enter labour charges for two wheeler", Toast.LENGTH_LONG)
                        .show()
                    return
                }
            }
            if (chkFourWheeler.isChecked) {
                if (TextUtils.isEmpty(edtLabourCharges1.text.toString().trim())) {
                    Toast.makeText(this, "Enter labour charges for four wheeler", Toast.LENGTH_LONG)
                        .show()
                    return
                }
            }

//            if (chkBike.isChecked) {
//                val vehicleType = VehicleType(chkBike.text.toString().trim())
//                vehicleTypeList.add(vehicleType)
//            }
//
//            if (chkMoped.isChecked) {
//                val vehicleType = VehicleType(chkMoped.text.toString().trim())
//                vehicleTypeList.add(vehicleType)
//            }

//            if (chkWithGear.isChecked) {
//                val vehicleType = VehicleType(chkWithGear.text.toString().trim())
//                vehicleTypeList.add(vehicleType)
//            }
//
//            if (chkWithoutGear.isChecked) {
//                val vehicleType = VehicleType(chkWithoutGear.text.toString().trim())
//                vehicleTypeList.add(vehicleType)
//            }

            if (chkTwoWheeler.isChecked) {
                if (vehicleTypeList2Wheel.isNotEmpty()) {
                    var doorstepServiceType = DoorstepServiceType(
                        edtLabourCharges.text.toString().trim(),
                        chkTwoWheeler.text.toString().trim()
                    )
                    doorstepServiceTypeList.add(doorstepServiceType!!)
                }
            }
            if (chkFourWheeler.isChecked) {
                if (vehicleTypeList4Wheel.isNotEmpty()) {
                    var doorstepServiceType = DoorstepServiceType(
                        edtLabourCharges1.text.toString().trim(),
                        chkFourWheeler.text.toString().trim()
                    )
                    doorstepServiceTypeList.add(doorstepServiceType!!)
                }
            }
        }

        if (TextUtils.isEmpty(edtAreaLimit.text.toString().trim())) {
            Toast.makeText(this, "Enter area limit", Toast.LENGTH_LONG).show()
            return
        }

        if (!TextUtils.equals(service_ID, "3")) {
            val ServiceDetail = ServiceDetail(
                edtAreaLimit.text.toString().trim(),
                brandList,
                doorstepServiceTypeList,
                dropDetailList,
                edtLabourCharges.text.toString(),
                pick_drop_flag,
                pickupDetailListc,
                serviceContactDetailList,
                service_ID,
                sparePartList,
                vehicleTypeList
            )
            serviceDetailList.add(ServiceDetail)

            var allServiceDetail = AllServiceDetail(serviceDetailList)
            allServiceDetailList.add(allServiceDetail)

            startActivity(Intent(this, Package::class.java).putExtra("type", service_ID))
            finish()
        } else {
            val ServiceDetail = ServiceDetail(
                edtAreaLimit.text.toString().trim(),
                brandList,
                doorstepServiceTypeList,
                dropDetailList,
                "0",
                pick_drop_flag,
                pickupDetailListc,
                serviceContactDetailList,
                service_ID,
                sparePartList,
                vehicleTypeList
            )
            serviceDetailList.add(ServiceDetail)

            var allServiceDetail = AllServiceDetail(serviceDetailList)
            allServiceDetailList.add(allServiceDetail)
            startActivity(
                Intent(this, Package::class.java).putExtra("type", service_ID)
                    .putExtra("chkTwo", chkTwoWheeler.isChecked)
                    .putExtra("chkFour", chkFourWheeler.isChecked)
            )
            finish()
        }

    }

    fun onClick(data: VehicleSubTypeData) {
        data.isChecked = !data.isChecked
        VehicleSubTypeAdapter!!.notifyDataSetChanged()
    }

    fun onClick1(data: VehicleSubTypeData) {
        data.isChecked = !data.isChecked
        VehicleSubTypeAdapter1!!.notifyDataSetChanged()
    }

    fun onClick(data: VehicleBrandData) {
        data.isChecked = !data.isChecked
        brandsAdapter!!.notifyDataSetChanged()
    }

    fun onClick1(data: VehicleBrandData) {
        data.isChecked = !data.isChecked
        brandsAdapter1!!.notifyDataSetChanged()
    }

    fun onClick(data: Pickdrp) {
        data.isChecked = !data.isChecked
        pickAdapter!!.notifyDataSetChanged()
    }

    override fun showProgressbar() {

    }

    override fun hideProgressbar() {

    }

    override fun onSuccessVehicleSubType(responseModel: Response<VehicleSubTypeResponse>) {
        if (responseModel.body() != null) {
            if (type == 1) {
                vehicleSubTypeList!!.clear()
                vehicleSubTypeList!!.addAll(responseModel.body()!!.All)
                VehicleSubTypeAdapter!!.notifyDataSetChanged()

                presenter!!.loadVehicleBrandData(
                    sessionManager.getAuthToken(this)!!,
                    vehicleSubTypeList!![0].vehicle_subtype_id.toString()
                )
            }

            if (type == 2) {
                vehicleSubTypeList1!!.clear()
                vehicleSubTypeList1!!.addAll(responseModel.body()!!.All)
                VehicleSubTypeAdapter1!!.notifyDataSetChanged()

                presenter!!.loadVehicleBrandData(
                    sessionManager.getAuthToken(this)!!,
                    vehicleSubTypeList1!![0].vehicle_subtype_id.toString()
                )
            }
        }

    }

    override fun onSuccessVehicleBrand(responseModel: Response<VehicleBrandResponse>) {
        if (responseModel.body() != null) {
            if (type == 1) {
                brandDataList.clear()
                brandDataList.addAll(responseModel.body()!!.All)
                brandsAdapter!!.notifyDataSetChanged()
            }
            if (type == 2) {
                brandDataList1.clear()
                brandDataList1.addAll(responseModel.body()!!.All)
                brandsAdapter1!!.notifyDataSetChanged()
            }
        }

    }

    override fun onError(errorCode: Int) {

    }

    override fun onError(throwable: Throwable) {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onStop()
    }
}