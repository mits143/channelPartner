package com.channelpartner.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.channelpartner.R
import com.channelpartner.adapter.OTGSChannelPartnerAdapter
import com.channelpartner.adapter.SpinGenerationAdapter
import com.channelpartner.adapter.SpinLevelAdapter
import com.channelpartner.model.response.*
import com.channelpartner.presenter.OTGSChannelPartnerPresenter
import com.channelpartner.view.OTGSChannelPartnerView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_otgs_channel_partner.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class OTGSChannelPartner : AppCompatActivity(), OTGSChannelPartnerView.MainView,
    SearchView.OnQueryTextListener {

    var otgschannelpartnerpresenter: OTGSChannelPartnerPresenter? = null
    var otgschannelpartneradapter: OTGSChannelPartnerAdapter? = null

    //    var spinGenerationAdapter: SpinGenerationAdapter? = null
//    var spinLevelAdapter: SpinLevelAdapter? = null
    var cpdetailList: ArrayList<CpDetail>? = arrayListOf()

    var generationList: ArrayList<AllState>? = arrayListOf()
    var levelList: ArrayList<AllXXX>? = arrayListOf()

    var page: Int? = 1
    var generation_id = 0
    var level_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otgs_channel_partner)
        setSupportActionBar(toolBar)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        txtTitle.text = getString(R.string.channel_partner)
        init()
    }

    fun init() {
        otgschannelpartnerpresenter = OTGSChannelPartnerPresenter(this, this)

        llFilter.visibility = View.VISIBLE
        rlDirect.visibility = View.VISIBLE
        rlInDirect.visibility = View.GONE
        llDirect_Indirect.visibility = View.GONE
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(layoutManager)
        otgschannelpartneradapter = OTGSChannelPartnerAdapter(
            this,
            cpdetailList!!,
            { cpdetailList -> onClick(cpdetailList) })

        recyclerView!!.setAdapter(otgschannelpartneradapter)

        otgschannelpartnerpresenter!!.loadGeneration(
            sessionManager.getAuthToken(this)!!
        )
        otgschannelpartnerpresenter!!.loadLevel(
            sessionManager.getAuthToken(this)!!
        )

        otgschannelpartnerpresenter!!.loadData(
            sessionManager.getAuthToken(this)!!,
            "",
            level_id,
            generation_id
        )

        swipeRefresh.setOnRefreshListener {
            searchView.setQuery("", false);
            searchView.clearFocus()
            otgschannelpartnerpresenter!!.loadData(
                sessionManager.getAuthToken(this)!!,
                "",
                level_id,
                generation_id
            )
        }
        searchView.setOnQueryTextListener(this)

        spinGeneration?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                generation_id = generationList!!.get(position).id
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
//                otgschannelpartnerpresenter!!.loadData(
//                    sessionManager.getAuthToken(this@OTGSChannelPartner)!!,
//                    page!!,
//                    "",
//                    sessionManager.getUser_ID(this@OTGSChannelPartner)!!.toInt(),
//                    sessionManager.getUserType(this@OTGSChannelPartner)!!.toInt(),
//                    "",
//                    generation_id,
//                    level_id
//                )
            }

        }

    }

    fun onClick(cpDetail: CpDetail) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("cp_id", cpDetail.id)
                .putExtra("class_name", "OTGSChannelPartner")
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
                SpinGenerationAdapter(this, generationList!!)
            spinGeneration.adapter = adapter
        }
    }

    override fun onSuccessLevel(responseModel: Response<LevelResponseX>) {
        if (responseModel.body() != null && responseModel.body()!!.size > 0) {
            levelList!!.clear()
            levelList!!.addAll(responseModel.body()!!)
            val adapter = SpinLevelAdapter(this, levelList!!)
            spinLevel.adapter = adapter
        }

    }

    override fun onSuccess(responseModel: Response<OTGSChannelPartnerResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.isNotEmpty()) {
            llDirectNoData.visibility = View.GONE
            cpdetailList!!.clear()
            cpdetailList!!.addAll(responseModel.body()!!)
        } else {
            cpdetailList!!.clear()
            llDirectNoData.visibility = View.VISIBLE
        }
        otgschannelpartneradapter!!.notifyDataSetChanged()
    }

    override fun onError(errorCode: Int) {
        cpdetailList!!.clear()
        otgschannelpartneradapter!!.notifyDataSetChanged()
        llDirectNoData.visibility = View.VISIBLE
        if (errorCode == 500) {
            Toast.makeText(this, getString(R.string.internal_server_error), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(throwable: Throwable) {
        cpdetailList!!.clear()
        otgschannelpartneradapter!!.notifyDataSetChanged()
        llDirectNoData.visibility = View.VISIBLE
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        otgschannelpartnerpresenter!!.onStop()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        otgschannelpartnerpresenter!!.loadData(
            sessionManager.getAuthToken(this)!!,
            query!!,
            level_id,
            generation_id
        )
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}
