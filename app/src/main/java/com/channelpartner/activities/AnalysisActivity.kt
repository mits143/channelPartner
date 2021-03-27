package com.channelpartner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.channelpartner.R
import com.channelpartner.adapter.ViewPagerAdapter
import com.channelpartner.fragment.FragmentCpList
import com.channelpartner.fragment.FragmentGarageList
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_analysis.*
import kotlinx.android.synthetic.main.activity_analysis.tabLayout
import kotlinx.android.synthetic.main.activity_analysis.viewPager
import kotlinx.android.synthetic.main.activity_classbook_network.*
import kotlinx.android.synthetic.main.toolbar.*

class AnalysisActivity : AppCompatActivity() {

    var cp_id = ""
    var viewpageradapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.analysis_info)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init() {
        if (intent.extras != null)
            cp_id = intent.getStringExtra("cp_id")
        viewpageradapter = ViewPagerAdapter(supportFragmentManager)
        viewpageradapter!!.addFragment(FragmentGarageList())
        viewpageradapter!!.addFragment(FragmentCpList())
        viewPager.adapter = viewpageradapter
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager!!.offscreenPageLimit = 2
    }
}
