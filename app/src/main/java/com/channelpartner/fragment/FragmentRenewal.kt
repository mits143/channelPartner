package com.channelpartner.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.channelpartner.R
import com.channelpartner.activities.RenewalDetail
import com.channelpartner.adapter.PackageRequestAdapter
import com.channelpartner.model.response.Cp
import com.channelpartner.model.response.PackageRerquestResponse
import com.channelpartner.presenter.RenewalPresenter
import com.channelpartner.view.RenewalView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.fragment_renewal.*
import retrofit2.Response

class FragmentRenewal : Fragment(), RenewalView.MainView {

    var renewalpresenter: RenewalPresenter? = null
    var packagerequestadapter: PackageRequestAdapter? = null
    var cpList: ArrayList<Cp>? = arrayListOf()
    var page: Int? = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_renewal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renewalpresenter = RenewalPresenter(context, this)

        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.setLayoutManager(layoutManager)
        packagerequestadapter = PackageRequestAdapter(context!!, cpList!!,
            { cpList -> onClick(cpList) })

        recyclerView!!.setAdapter(packagerequestadapter)

//        renewalpresenter!!.loadData(
//            sessionManager.getUser_ID(context)!!.toInt(),
//            sessionManager.getUserType(context)!!.toInt()
//        )

        swipeRefresh.setOnRefreshListener {
            renewalpresenter!!.loadData(
                sessionManager.getAuthToken(context)!!,
                sessionManager.getUser_ID(context)!!.toInt(),
                sessionManager.getUserType(context)!!.toInt()
            )
        }
    }

    fun onClick(cpList: Cp) {
        startActivity(
            Intent(context, RenewalDetail::class.java)
                .putExtra("id", cpList.history_id)
                .putExtra("type", 1)
        )
    }

    override fun showProgressbar() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipeRefresh.isRefreshing = false
    }

    override fun onSuccess(responseModel: Response<PackageRerquestResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.cp.size > 0) {
            llDirectNoData.visibility = View.GONE
            cpList!!.clear()
            cpList!!.addAll(responseModel.body()!!.cp)
            packagerequestadapter!!.notifyDataSetChanged()
        } else {
            cpList!!.clear()
            llDirectNoData.visibility = View.VISIBLE
        }
    }

    override fun onError(errorCode: Int) {
        llDirectNoData.visibility = View.VISIBLE
        if (errorCode == 500) {
            Toast.makeText(context, getString(R.string.internal_server_error), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(throwable: Throwable) {
        llDirectNoData.visibility = View.VISIBLE
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        renewalpresenter!!.onStop()
    }

    override fun onResume() {
        super.onResume()
//        renewalpresenter!!.loadData(
//            sessionManager.getAuthToken(context)!!,
//            sessionManager.getUser_ID(context)!!.toInt(),
//            sessionManager.getUserType(context)!!.toInt()
//        )
    }
}
