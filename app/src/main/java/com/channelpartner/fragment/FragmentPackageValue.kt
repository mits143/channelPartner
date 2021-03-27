package com.channelpartner.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager

import com.channelpartner.R
import com.channelpartner.adapter.PackageValueAdapter
import com.channelpartner.adapter.PackageValueClassbookAdapter
import com.channelpartner.model.response.AllX
import com.channelpartner.model.response.AllXXXXX
import com.channelpartner.model.response.ClassbookResponse
import com.channelpartner.model.response.PackageValueResponse
import com.channelpartner.presenter.PackageValuePresenter
import com.channelpartner.view.PackageValueView
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.fragment_package_value.*
import retrofit2.Response

class FragmentPackageValue : Fragment(), PackageValueView.MainView {

    var presenter: PackageValuePresenter? = null
    var adapter: PackageValueAdapter? = null
    var adapter1: PackageValueAdapter? = null
    var adapter2: PackageValueAdapter? = null
    var adapter3: PackageValueAdapter? = null
    var adapter4: PackageValueAdapter? = null

    var adapter5: PackageValueClassbookAdapter? = null
    var adapter6: PackageValueClassbookAdapter? = null

    var allList: ArrayList<AllX>? = arrayListOf()
    var allList1: ArrayList<AllX>? = arrayListOf()
    var allList2: ArrayList<AllX>? = arrayListOf()
    var allList3: ArrayList<AllX>? = arrayListOf()
    var allList4: ArrayList<AllX>? = arrayListOf()

    var primaryList: ArrayList<AllXXXXX>? = arrayListOf()
    var secondaryList: ArrayList<AllXXXXX>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_package_value, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cvAutoHub.setOnClickListener(View.OnClickListener {
            cvAutoHub.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtAutoHub.setTextColor(resources.getColor(R.color.colorWhite))
            cvClassbook.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtClassbook.setTextColor(resources.getColor(R.color.colorBlack))
            llAutohub.visibility = View.VISIBLE
            llClassBook.visibility = View.GONE
        })
        cvClassbook.setOnClickListener(View.OnClickListener {
            cvAutoHub.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
            txtAutoHub.setTextColor(resources.getColor(R.color.colorBlack))
            cvClassbook.setCardBackgroundColor(resources.getColor(R.color.colorButton))
            txtClassbook.setTextColor(resources.getColor(R.color.colorWhite))
            llAutohub.visibility = View.GONE
            llClassBook.visibility = View.VISIBLE
        })

        presenter = PackageValuePresenter(context, this)
        presenter!!.loadData(
            sessionManager.getAuthToken(context)!!)
        presenter!!.loadDataClassbook(
            sessionManager.getAuthToken(context)!!)

        val layoutManager = GridLayoutManager(context, 3)
        rvTwoWheeler!!.setLayoutManager(layoutManager)
        adapter = PackageValueAdapter(context!!, allList!!, { allList -> onClick(allList) })
        rvTwoWheeler!!.setAdapter(adapter)

        val layoutManager1 = GridLayoutManager(context, 3)
        rvFourWheeler!!.setLayoutManager(layoutManager1)
        adapter1 = PackageValueAdapter(context!!, allList1!!, { allList1 -> onClick(allList1) })
        rvFourWheeler!!.setAdapter(adapter1)

        val layoutManager2 = GridLayoutManager(context, 3)
        rvDoorStepTwoWheeler!!.setLayoutManager(layoutManager2)
        adapter2 = PackageValueAdapter(context!!, allList2!!, { allList2 -> onClick(allList2) })
        rvDoorStepTwoWheeler!!.setAdapter(adapter2)

        val layoutManager3 = GridLayoutManager(context, 3)
        rvDoorStepFourWheeler!!.setLayoutManager(layoutManager3)
        adapter3 = PackageValueAdapter(context!!, allList3!!, { allList3 -> onClick(allList3) })
        rvDoorStepFourWheeler!!.setAdapter(adapter3)

        val layoutManager4 = GridLayoutManager(context, 3)
        rvSpareParts!!.setLayoutManager(layoutManager4)
        adapter4 = PackageValueAdapter(context!!, allList4!!, { allList4 -> onClick(allList4) })
        rvSpareParts!!.setAdapter(adapter4)

        val layoutManager5 = GridLayoutManager(context, 3)
        rvPrimary!!.setLayoutManager(layoutManager5)
        adapter5 = PackageValueClassbookAdapter(context!!, primaryList!!, { primaryList -> onClick(primaryList) })
        rvPrimary!!.setAdapter(adapter5)

        val layoutManager6 = GridLayoutManager(context, 3)
        rvSecondary!!.setLayoutManager(layoutManager6)
        adapter6 = PackageValueClassbookAdapter(context!!, secondaryList!!, { secondaryList -> onClick(secondaryList) })
        rvSecondary!!.setAdapter(adapter6)

    }

    fun onClick(data: AllX) {

    }

    fun onClick(data: AllXXXXX) {

    }

    override fun showProgressbar() {

    }

    override fun hideProgressbar() {

    }

    override fun onSuccess(responseModel: Response<PackageValueResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.All.size > 0) {
            allList!!.clear()
            allList1!!.clear()
            allList2!!.clear()
            allList3!!.clear()
            allList4!!.clear()
            for (i in responseModel.body()!!.All.indices) {
                if (TextUtils.equals(responseModel.body()!!.All[i].service_name, "Two Wheeler"))
                    allList!!.add(responseModel.body()!!.All[i])
                if (TextUtils.equals(responseModel.body()!!.All[i].service_name, "Four Wheeler"))
                    allList1!!.add(responseModel.body()!!.All[i])
                if (TextUtils.equals(
                        responseModel.body()!!.All[i].service_name,
                        "Two wheeler Door Step"
                    )
                )
                    allList2!!.add(responseModel.body()!!.All[i])
                if (TextUtils.equals(
                        responseModel.body()!!.All[i].service_name,
                        "Four wheeler Door Step"
                    )
                )
                    allList3!!.add(responseModel.body()!!.All[i])

                if (!TextUtils.equals(responseModel.body()!!.All[i].service_name, "Two Wheeler") &&
                    !TextUtils.equals(responseModel.body()!!.All[i].service_name, "Four Wheeler") &&
                    !TextUtils.equals(
                        responseModel.body()!!.All[i].service_name,
                        "Two wheeler Door Step"
                    ) &&
                    !TextUtils.equals(
                        responseModel.body()!!.All[i].service_name,
                        "Four wheeler Door Step"
                    )
                )
                    allList4!!.add(responseModel.body()!!.All[i])

                adapter!!.notifyDataSetChanged()
                adapter1!!.notifyDataSetChanged()
                adapter2!!.notifyDataSetChanged()
                adapter3!!.notifyDataSetChanged()
                adapter4!!.notifyDataSetChanged()

            }
        }
    }

    override fun onSuccessClassbook(responseModel: Response<ClassbookResponse>) {
        if (responseModel.body() != null && responseModel.body()!!.All.size > 0) {
            primaryList!!.clear()
            secondaryList!!.clear()
            for (i in responseModel.body()!!.All.indices) {
                if (TextUtils.equals(responseModel.body()!!.All[i].class_type, "Primary"))
                    primaryList!!.add(responseModel.body()!!.All[i])
                if (TextUtils.equals(responseModel.body()!!.All[i]. class_type, "Secondary"))
                    secondaryList!!.add(responseModel.body()!!.All[i])

                adapter5!!.notifyDataSetChanged()
                adapter6!!.notifyDataSetChanged()

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
