package com.channelpartner.presenter


import android.content.Context
import android.widget.Toast
import com.channelpartner.R
import com.channelpartner.model.request.AutoHub.AllServiceDetail
import com.channelpartner.model.request.AutoHub.GarageInformation
import com.channelpartner.model.request.AutoHub.PaymentMode
import com.channelpartner.model.request.AutoHub.WorkingHour
import com.channelpartner.network.ApiClient
import com.channelpartner.utils.NetWorkConection
import com.channelpartner.view.DetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException


class DetailPresenter : DetailView.MainPresenter {

    var context: Context? = null
    var mainView: DetailView.MainView? = null

    @NonNull
    var disposable: Disposable? = null

    constructor(context: Context?, mainView: DetailView.MainView?) {
        this.context = context
        this.mainView = mainView
    }

    override fun loadData(
        token: String,
        all_service_details: List<AllServiceDetail>,
        business_code: String,
        channelpartner_id: String,
        garage_information: GarageInformation,
        gst: String,
        internet_handling_cost: String,
        package_details: List<com.channelpartner.model.request.AutoHub.PackageDetail>,
        payment_mode: List<PaymentMode>,
        total: String,
        working_hours: ArrayList<WorkingHour>
    ) {
        mainView!!.showProgressbar()

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postRegServiceProvider(
                    token,
                    all_service_details,
                    business_code,
                    channelpartner_id,
                    garage_information,
                    gst,
                    internet_handling_cost,
                    package_details,
                    payment_mode,
                    total,
                    working_hours
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar()
                    val responseCode = listResponse.code()
                    when (responseCode) {
                         200, 201, 202, 204 -> {
                            mainView!!.onSuccess(1, listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar()
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar()
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun commission(
        token: String,
        date_of_joining: String,
        name: String,
        paid_amount: String,
        parent_id: Int,
        shop_type: String,
        user_id: Int,
        user_type: Int
    ) {
        mainView!!.showProgressbar()

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postCommission(
                    token,
                    date_of_joining,
                    name,
                    paid_amount,
                    parent_id,
                    shop_type,
                    user_id,
                    user_type
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar()
                    val responseCode = listResponse.code()
                    when (responseCode) {
                         200, 201, 202, 204 -> {
                            mainView!!.onSuccess(2, listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar()
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar()
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun SPImageUploads(
        token: String,
        profile: MultipartBody.Part,
        user_id: RequestBody,
        user_type: RequestBody,
        pan: MultipartBody.Part,
        aadhar_card: MultipartBody.Part,
        driving_license: MultipartBody.Part,
        passport: MultipartBody.Part,
        voter_id: MultipartBody.Part,
        electricity_bill: MultipartBody.Part,
        shop_license: MultipartBody.Part,
        images: ArrayList<MultipartBody.Part>
    ) {
        mainView!!.showProgressbar()

        if (NetWorkConection.isNEtworkConnected(context!!)) {

            disposable = ApiClient.instance
                .postSPImages(
                    token,
                    profile,
                    user_id,
                    user_type,
                    pan,
                    aadhar_card,
                    driving_license,
                    passport,
                    voter_id,
                    electricity_bill,
                    shop_license,
                    images
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar()
                    val responseCode = listResponse.code()
                    when (responseCode) {
                         200, 201, 202, 204 -> {
                            mainView!!.onSuccess(3, listResponse)
                        }
                        400, 401, 500 -> {
                            mainView!!.onError(responseCode)
                        }
                    }
                }, { error ->
                    mainView!!.hideProgressbar()
                    if (error is HttpException) {
                        val response = (error as HttpException).response()
                        when (response!!.code()) {
                            //Responce Code
                        }
                    } else {
                        //Handle Other Errors
                    }
                    mainView!!.onError(error)
                })
        } else {
            mainView!!.hideProgressbar()
            Toast.makeText(
                context!!,
                context!!.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun onStop() {
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}