package com.channelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.channelpartner.R
import com.channelpartner.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_classbook.*

class FragmentClassbook : Fragment() {

    var viewpageradapter: ViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_classbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        viewpageradapter = ViewPagerAdapter(childFragmentManager)
        viewpageradapter!!.addFragment(FragmentNotificationClass())
        viewpageradapter!!.addFragment(FragmentNotificationTeacher())
        viewpageradapter!!.addFragment(FragmentNotificationStudent())
        viewpageradapter!!.addFragment(FragmentNotificationExpertCareer())
        viewPager.adapter = viewpageradapter
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager!!.offscreenPageLimit = 4
    }
}
