package com.channelpartner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.channelpartner.R
import com.channelpartner.adapter.ViewPagerAdapter
import com.channelpartner.fragment.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_classbook.*

class SubScriptionActivity : AppCompatActivity() {

    var viewpageradapter: ViewPagerAdapter? = null
    var cp_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_scription)
        init()
    }

    fun init() {
        if (intent.extras != null) {
            cp_id = intent.getStringExtra("cp_id")!!
        }
        viewpageradapter = ViewPagerAdapter(supportFragmentManager)
        viewpageradapter!!.addFragment(FragmentClasses())
        viewpageradapter!!.addFragment(FragmentCourses())
        viewpageradapter!!.addFragment(FragmentTeachers())
        viewpageradapter!!.addFragment(FragmentExperts())
        viewPager.adapter = viewpageradapter
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager!!.offscreenPageLimit = 4
    }
}