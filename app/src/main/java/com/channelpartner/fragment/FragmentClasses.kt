package com.channelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.channelpartner.R
import com.channelpartner.activities.SubScriptionActivity
import com.channelpartner.adapter.SubscriptionClassesAdapter
import com.channelpartner.model.response.Classe
import com.channelpartner.model.response.SubscrpitionResponse
import com.channelpartner.presenter.SubscriptionPresenter
import com.channelpartner.view.SubscripitionView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.fragment_classes.*
import retrofit2.Response

class FragmentClasses : Fragment(), SubscripitionView.MainView {

    var presenter: SubscriptionPresenter? = null
    var adapter: SubscriptionClassesAdapter? = null
    var dataList: ArrayList<Classe>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SubscriptionPresenter(context, this)
        

        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.setLayoutManager(layoutManager)
        adapter = SubscriptionClassesAdapter(
            context!!,
            dataList!!,
            { classList -> onClick(classList) })

        recyclerView!!.setAdapter(adapter)

        presenter!!.loadData(
            sessionManager.getAuthToken(context)!!,
            (activity as SubScriptionActivity).cp_id.toInt(),
            sessionManager.getUser_ID(context)!!.toInt(),
            sessionManager.getUserType(context)!!.toInt()
        )

        swipeRefresh.setOnRefreshListener {
            presenter!!.loadData(
                sessionManager.getAuthToken(context)!!,
                (activity as SubScriptionActivity).cp_id.toInt(),
                sessionManager.getUser_ID(context)!!.toInt(),
                sessionManager.getUserType(context)!!.toInt()
            )
        }
    }

    fun onClick(data: Classe) {

    }

    override fun showProgressbar() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipeRefresh.isRefreshing = false
    }

    override fun onSuccess(responseModel: Response<SubscrpitionResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.classes.isNotEmpty()) {
            dataList!!.clear()
            dataList!!.addAll(responseModel.body()!!.classes)
            adapter!!.notifyDataSetChanged()
        } else {
            dataList!!.clear()
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
        presenter!!.onStop()
    }
}