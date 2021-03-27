package com.channelpartner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.channelpartner.R
import com.channelpartner.adapter.ViewPagerAdapter
import com.channelpartner.fragment.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_faq.*
import kotlinx.android.synthetic.main.toolbar.*

class FAQ : AppCompatActivity() {

    var viewpageradapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.faq)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        viewpageradapter = ViewPagerAdapter(supportFragmentManager)
        viewpageradapter!!.addFragment(FragmentGeneralFAQ())
        viewpageradapter!!.addFragment(FragmentCPFAQ())
        viewpageradapter!!.addFragment(FragmentSPFAQ())
        viewpageradapter!!.addFragment(FragmentClassFAQ())
        viewpageradapter!!.addFragment(FragmentTeacherFAQ())
        viewpageradapter!!.addFragment(FragmentStudentFAQ())
        viewpageradapter!!.addFragment(FragmentCEFAQ())
        viewPager.adapter = viewpageradapter
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager!!.offscreenPageLimit = 7
    }
}
