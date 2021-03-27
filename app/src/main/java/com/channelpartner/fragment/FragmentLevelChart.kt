package com.channelpartner.fragment

import android.app.Activity
import android.content.Intent
import android.content.res.AssetManager
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.channelpartner.R
import com.channelpartner.activities.OtherServiceDetail
import com.channelpartner.activities.ServiceDetails
import com.channelpartner.adapter.LevelAdapter
import com.channelpartner.adapter.OTGSChannelPartnerAdapter
import com.channelpartner.model.response.All
import com.channelpartner.model.response.LevelResponse
import com.channelpartner.model.response.ServiceMasterLists
import com.channelpartner.presenter.LevelPresenter
import com.channelpartner.view.LevelView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.fragment_level_chart.*
import kotlinx.android.synthetic.main.fragment_level_chart.recyclerView
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import retrofit2.Response
import java.io.InputStream


class FragmentLevelChart : Fragment(), LevelView.MainView {

    var levelPresenter: LevelPresenter? = null
    var levelAdapter: LevelAdapter? = null
    var levelAdapter1: LevelAdapter? = null
    var levelAdapter2: LevelAdapter? = null
    var allList: ArrayList<All>? = arrayListOf()
    var allList1: ArrayList<All>? = arrayListOf()
    var allList2: ArrayList<All>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_level_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        levelPresenter = LevelPresenter(context, this)
        levelPresenter!!.loadData(
            sessionManager.getAuthToken(context)!!
        )
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.setLayoutManager(layoutManager)
        levelAdapter = LevelAdapter(
            context!!,
            allList!!,
            { allList -> onClick(allList) })

        recyclerView!!.setAdapter(levelAdapter)

        val layoutManager1 = LinearLayoutManager(context)
        recyclerView1!!.setLayoutManager(layoutManager1)
        levelAdapter1 = LevelAdapter(
            context!!,
            allList1!!,
            { allList1 -> onClick(allList1) })

        recyclerView1!!.setAdapter(levelAdapter1)

        val layoutManager2 = LinearLayoutManager(context)
        recyclerView2!!.setLayoutManager(layoutManager2)
        levelAdapter2 = LevelAdapter(
            context!!,
            allList2!!,
            { allList2 -> onClick(allList2) })

        recyclerView2!!.setAdapter(levelAdapter2)
    }

    fun onClick(data: All) {
    }

    override fun showProgressbar() {

    }

    override fun hideProgressbar() {

    }

    override fun onSuccess(responseModel: Response<LevelResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.size > 0) {
            allList!!.clear()
            allList1!!.clear()
            allList2!!.clear()
            for (i in responseModel.body()!!.indices) {
                if (TextUtils.equals(responseModel.body()!![i].type, "LevelDiff")) {
                    allList!!.add(responseModel.body()!![i])
                    levelAdapter!!.notifyDataSetChanged()
                }
                if (TextUtils.equals(responseModel.body()!![i].type, "Residual")) {
                    allList1!!.add(responseModel.body()!![i])
                    levelAdapter1!!.notifyDataSetChanged()
                }
                if (TextUtils.equals(responseModel.body()!![i].type, "Royalty")) {
                    allList2!!.add(responseModel.body()!![i])
                    levelAdapter2!!.notifyDataSetChanged()
                }
            }

        }

    }

    override fun onError(errorCode: Int) {
        if (errorCode == 500) {
            Toast.makeText(context, getString(R.string.internal_server_error), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

}
