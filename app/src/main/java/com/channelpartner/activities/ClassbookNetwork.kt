package com.channelpartner.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.channelpartner.R
import com.channelpartner.adapter.ViewPagerAdapter
import com.channelpartner.fragment.FragmentClass
import com.channelpartner.fragment.FragmentExpertCareer
import com.channelpartner.fragment.FragmentStudent
import com.channelpartner.fragment.FragmentTeacher
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_classbook_network.*
import kotlinx.android.synthetic.main.toolbar.*


class ClassbookNetwork : AppCompatActivity() {

    var viewpageradapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classbook_network)
        setSupportActionBar(toolBar)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        txtTitle.text = getString(R.string.classbook_network)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        viewpageradapter = ViewPagerAdapter(supportFragmentManager)
        viewpageradapter!!.addFragment(FragmentClass())
        viewpageradapter!!.addFragment(FragmentTeacher())
        viewpageradapter!!.addFragment(FragmentStudent())
        viewpageradapter!!.addFragment(FragmentExpertCareer())
        viewPager.adapter = viewpageradapter
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        viewPager!!.offscreenPageLimit = 4
    }
}
