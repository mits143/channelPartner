package com.channelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.channelpartner.R
import com.channelpartner.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_classbook_network.*

class FragmentPackageRequest : Fragment() {

    var viewpageradapter: ViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_package_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        viewpageradapter = ViewPagerAdapter(childFragmentManager)
        viewpageradapter!!.addFragment(FragmentRenewal())
        viewpageradapter!!.addFragment(FragmentAddService())
        viewpageradapter!!.addFragment(FragmentHistory())
        viewPager.adapter = viewpageradapter
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager!!.offscreenPageLimit = 3
    }
}
