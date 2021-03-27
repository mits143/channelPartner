package com.channelpartner.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.channelpartner.R
import com.channelpartner.activities.RenewalDetail
import com.channelpartner.adapter.FAQAdapter
import com.channelpartner.adapter.PackageRequestAdapter
import com.channelpartner.model.response.AllFaq
import com.channelpartner.model.response.Cp
import com.channelpartner.model.response.FAQResponse
import com.channelpartner.model.response.PackageRerquestResponse
import com.channelpartner.presenter.AddServicePresenter
import com.channelpartner.presenter.FAQPresenter
import com.channelpartner.presenter.RenewalPresenter
import com.channelpartner.view.FAQView
import com.channelpartner.view.RenewalView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.fragment_renewal.*
import retrofit2.Response

class FragmentGeneralFAQ : Fragment(), FAQView.MainView {

    var presenter: FAQPresenter? = null
    var adapter: FAQAdapter? = null
    var faqList: ArrayList<AllFaq>? = arrayListOf()
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

        presenter = FAQPresenter(context, this)
        presenter!!.loadData(
            sessionManager.getAuthToken(context)!!)

        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.setLayoutManager(layoutManager)
        adapter = FAQAdapter(context!!, faqList!!,
            { faqList -> onClick(faqList) })

        recyclerView!!.setAdapter(adapter)

        swipeRefresh.setOnRefreshListener {
            presenter!!.loadData(
                sessionManager.getAuthToken(context)!!)
        }
    }

    fun onClick(faqList: AllFaq) {

    }

    override fun showProgressbar() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipeRefresh.isRefreshing = false
    }

    override fun onSuccess(responseModel: Response<FAQResponse>) {
        if (responseModel.body() != null) {
            if (responseModel.body()!!.size > 0) {
                llDirectNoData.visibility = View.GONE
                faqList!!.clear()
                for (i in responseModel.body()!!.indices) {
                    if (TextUtils.equals(responseModel.body()!![i].type, "Class")) {
                        faqList!!.add(responseModel.body()!![i])
                    }
                }
                adapter!!.notifyDataSetChanged()
            } else {
                llDirectNoData.visibility = View.VISIBLE
            }
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
        presenter!!.onStop()
    }
}
