package com.channelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.channelpartner.R
import com.channelpartner.adapter.NotificationAdapter
import com.channelpartner.model.response.Notification
import com.channelpartner.model.response.NotificationResponse
import com.channelpartner.presenter.NotificationPresenter
import com.channelpartner.view.NotificationView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.fragment_renewal.*
import retrofit2.Response

class FragmentNotificationTeacher : Fragment(), NotificationView.MainView {

    var notificationpresenter: NotificationPresenter? = null
    var notificationadapter: NotificationAdapter? = null
    var notificationList: ArrayList<Notification>? = arrayListOf()
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
        init()
    }

    fun init() {
        notificationpresenter = NotificationPresenter(context, this)

        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.setLayoutManager(layoutManager)
        notificationadapter = NotificationAdapter(
            context!!,
            notificationList!!,
            { notificationList -> onClick(notificationList) })

        recyclerView!!.setAdapter(notificationadapter)

        notificationpresenter!!.loadData1(
            sessionManager.getAuthToken(context)!!,
            "",
            "6",
            page!!.toString(),
            sessionManager.getUser_ID(context)!!.toInt(),
            sessionManager.getUserType(context)!!.toInt()
        )

        swipeRefresh.setOnRefreshListener {
            notificationpresenter!!.loadData1(
                sessionManager.getAuthToken(context)!!,
                "",
                "6",
                page!!.toString(),
                sessionManager.getUser_ID(context)!!.toInt(),
                sessionManager.getUserType(context)!!.toInt()
            )
        }

    }

    fun onClick(notification: Notification) {

    }

    override fun showProgressbar() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipeRefresh.isRefreshing = false
    }

    override fun onSuccess(responseModel: Response<NotificationResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.Notification.size > 0) {
            notificationList!!.clear()
            notificationList!!.addAll(responseModel.body()!!.Notification)
            notificationadapter!!.notifyDataSetChanged()
        } else {
            notificationList!!.clear()
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

    override fun onDestroyView() {
        super.onDestroyView()
        notificationpresenter!!.onStop()
    }
}
