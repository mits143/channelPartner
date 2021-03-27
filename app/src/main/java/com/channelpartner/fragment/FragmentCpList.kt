package com.channelpartner.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager

import com.channelpartner.R
import com.channelpartner.activities.AnalysisActivity
import com.channelpartner.activities.DetailActivity
import com.channelpartner.adapter.OTGSChannelPartnerAdapter
import com.channelpartner.adapter.SpinGenerationAdapter
import com.channelpartner.adapter.SpinLevelAdapter
import com.channelpartner.model.response.*
import com.channelpartner.presenter.FragmentCPListPresenter
import com.channelpartner.view.OTGSCPListView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_otgs_channel_partner.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class FragmentCpList : Fragment(), OTGSCPListView.MainView, SearchView.OnQueryTextListener {

    var fragmentCPListPresenter: FragmentCPListPresenter? = null
    var otgschannelpartnerDirectadapter: OTGSChannelPartnerAdapter? = null
    var otgschannelpartnerIndirectadapter: OTGSChannelPartnerAdapter? = null
    var cpdetailDirectList: ArrayList<CpDetail>? = arrayListOf()
    var cpdetailInDirectList: ArrayList<CpDetail>? = arrayListOf()
    var page: Int? = 1
    var generation_id = 0
    var level_id = 0

    var generationList: ArrayList<AllState>? = arrayListOf()
    var levelList: ArrayList<AllXXX>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_otgs_channel_partner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCPListPresenter = FragmentCPListPresenter(context, this)

        llFilter.visibility = View.VISIBLE

        searchView.setOnQueryTextListener(this)
        appBar.visibility = View.GONE
        llDirect_Indirect.visibility = View.VISIBLE
        cvDirect.setOnClickListener(View.OnClickListener {
            cvDirect.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtDirect.setTextColor(resources.getColor(R.color.colorWhite))
            cvInDirect.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtInDirect.setTextColor(resources.getColor(R.color.colorBlack))
            rlDirect.visibility = View.VISIBLE
            rlInDirect.visibility = View.GONE
        })
        cvInDirect.setOnClickListener(View.OnClickListener {
            cvDirect.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtDirect.setTextColor(resources.getColor(R.color.colorBlack))
            cvInDirect.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtInDirect.setTextColor(resources.getColor(R.color.colorWhite))
            rlDirect.visibility = View.GONE
            rlInDirect.visibility = View.VISIBLE
        })

        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        otgschannelpartnerDirectadapter =
            OTGSChannelPartnerAdapter(
                context!!,
                cpdetailDirectList!!,
                { cpdetailList -> onClick(cpdetailList) })

        recyclerView!!.adapter = otgschannelpartnerDirectadapter

        val layoutManager1 = LinearLayoutManager(context)
        recyclerView1!!.layoutManager = layoutManager1
        otgschannelpartnerIndirectadapter =
            OTGSChannelPartnerAdapter(
                context!!,
                cpdetailInDirectList!!,
                { cpdetailList -> onClick(cpdetailList) })

        recyclerView1!!.adapter = otgschannelpartnerIndirectadapter

        fragmentCPListPresenter!!.loadDirectData(
            sessionManager.getAuthToken(context)!!,
            page!!,
            (activity as AnalysisActivity).cp_id,
            sessionManager.getUser_ID(context)!!.toInt(),
            sessionManager.getUserType(context)!!.toInt(),
            "",
            generation_id,
            level_id
        )
        fragmentCPListPresenter!!.loadIndirectData(
            sessionManager.getAuthToken(context)!!,
            page!!,
            (activity as AnalysisActivity).cp_id,
            sessionManager.getUser_ID(context)!!.toInt(),
            sessionManager.getUserType(context)!!.toInt(),
            "",
            generation_id,
            level_id
        )

        swipeRefresh.setOnRefreshListener {
            searchView.setQuery("", false);
            searchView.clearFocus();
            fragmentCPListPresenter!!.loadDirectData(
                sessionManager.getAuthToken(context)!!,
                page!!,
                (activity as AnalysisActivity).cp_id,
                sessionManager.getUser_ID(context)!!.toInt(),
                sessionManager.getUserType(context)!!.toInt(),
                "",
                generation_id,
                level_id
            )
            fragmentCPListPresenter!!.loadIndirectData(
                sessionManager.getAuthToken(context)!!,
                page!!,
                (activity as AnalysisActivity).cp_id,
                sessionManager.getUser_ID(context)!!.toInt(),
                sessionManager.getUserType(context)!!.toInt(),
                "",
                generation_id,
                level_id
            )
        }

        fragmentCPListPresenter!!.loadGeneration(
            sessionManager.getAuthToken(context)!!
        )
        fragmentCPListPresenter!!.loadLevel(
            sessionManager.getAuthToken(context)!!
        )

        spinGeneration?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                generation_id = generationList!![position].id
            }

        }

        spinLevel?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                level_id = levelList!!.get(position).id
            }

        }
    }

    private fun onClick(cpDetail: CpDetail) {
        context!!.startActivity(
            Intent(context!!, DetailActivity::class.java).putExtra(
                "cp_id",
                cpDetail.id
            )
                .putExtra("class_name", "FragmentCpList")
        )
    }

    override fun showProgressbar() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipeRefresh.isRefreshing = false
    }

    override fun onSuccessGeneration(responseModel: Response<GenerationResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.size > 0) {
            generationList!!.clear()
            generationList!!.addAll(responseModel.body()!!)
            val adapter =
                SpinGenerationAdapter(context!!, generationList!!)
            spinGeneration.adapter = adapter
        }
    }

    override fun onSuccessLevel(responseModel: Response<LevelResponseX>) {
        if (responseModel.body() != null && responseModel.body()!!.size > 0) {
            levelList!!.clear()
            levelList!!.addAll(responseModel.body()!!)
            val adapter = SpinLevelAdapter(context!!, levelList!!)
            spinLevel.adapter = adapter
        }

    }

    override fun onSuccess(value: Int, responseModel: Response<OTGSChannelPartnerResponse>) {
        if (value == 1) {
            if (responseModel.body() != null && responseModel.body()!!.isNotEmpty()) {
                llDirectNoData.visibility = View.GONE
                cpdetailDirectList!!.clear()
                cpdetailDirectList!!.addAll(responseModel.body()!!)
//                txtDirect.text = "Direct: " + responseModel.body()!!.total_count
            } else {
                cpdetailDirectList!!.clear()
                llDirectNoData.visibility = View.VISIBLE
            }
        } else {
            if (responseModel.body() != null && responseModel.body()!!.size > 0) {
                llIndirectNoData.visibility = View.GONE
                cpdetailInDirectList!!.clear()
                cpdetailInDirectList!!.addAll(responseModel.body()!!)
                txtInDirect.text = "InDirect: " + responseModel.body()!!
            } else {
                cpdetailInDirectList!!.clear()
                llIndirectNoData.visibility = View.VISIBLE
            }
        }
        otgschannelpartnerDirectadapter!!.notifyDataSetChanged()
        otgschannelpartnerIndirectadapter!!.notifyDataSetChanged()
    }

    override fun onError(value: Int, errorCode: Int) {
        if (value == 1) {
            cpdetailDirectList!!.clear()
            otgschannelpartnerDirectadapter!!.notifyDataSetChanged()
            llDirectNoData.visibility = View.VISIBLE
        } else {
            cpdetailInDirectList!!.clear()
            otgschannelpartnerIndirectadapter!!.notifyDataSetChanged()
            llIndirectNoData.visibility = View.VISIBLE
        }
        if (errorCode == 500) {
            Toast.makeText(context, getString(R.string.internal_server_error), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(value: Int, throwable: Throwable) {
        if (value == 1) {
            cpdetailDirectList!!.clear()
            otgschannelpartnerDirectadapter!!.notifyDataSetChanged()
            llDirectNoData.visibility = View.VISIBLE
        } else {
            cpdetailInDirectList!!.clear()
            otgschannelpartnerIndirectadapter!!.notifyDataSetChanged()
            llIndirectNoData.visibility = View.VISIBLE
        }
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        fragmentCPListPresenter!!.loadDirectData(
            sessionManager.getAuthToken(context)!!,
            page!!,
            sessionManager.getUser_ID(context)!!,
            sessionManager.getUser_ID(context)!!.toInt(),
            sessionManager.getUserType(context)!!.toInt(),
            query!!,
            generation_id,
            level_id
        )
        fragmentCPListPresenter!!.loadIndirectData(
            sessionManager.getAuthToken(context)!!,
            page!!,
            sessionManager.getUser_ID(context)!!,
            sessionManager.getUser_ID(context)!!.toInt(),
            sessionManager.getUserType(context)!!.toInt(),
            query!!,
            generation_id,
            level_id
        )
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentCPListPresenter!!.onStop()
    }
}
