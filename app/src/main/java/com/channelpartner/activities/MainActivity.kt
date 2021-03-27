package com.channelpartner.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.channelpartner.R
import com.channelpartner.adapter.ViewPagerAdapter
import com.channelpartner.fragment.*
import com.channelpartner.presenter.MainActivityPresnter
import com.channelpartner.utils.CustomDialog
import com.channelpartner.utils.EditTextDrawableSize
import com.channelpartner.utils.TextViewDrawableSize
import com.channelpartner.view.MainView
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), CustomDialog.listener {

    var viewpageradapter: ViewPagerAdapter? = null
    var customDialog: CustomDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        init()
    }

    fun init() {
        txtTitle.text = getString(R.string.home)

        if (sessionManager.getis_first_atempt(this) != 0) {
            customDialog = CustomDialog(this@MainActivity, this)
            customDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            customDialog!!.show()
        }
        viewpageradapter = ViewPagerAdapter(supportFragmentManager)
        viewpageradapter!!.addFragment(FragmentPackageRequest())
        viewpageradapter!!.addFragment(FragmentLevelChart())
        viewpageradapter!!.addFragment(FragmentHome())
        viewpageradapter!!.addFragment(FragmentPackageValue())
        viewpageradapter!!.addFragment(FragmentSetting())
        viewPager.adapter = viewpageradapter

        bottomNavBar.add(MeowBottomNavigation.Model(0, R.drawable.ic_packagerequest))
        bottomNavBar.add(MeowBottomNavigation.Model(1, R.drawable.ic_chart))
        bottomNavBar.add(MeowBottomNavigation.Model(2, R.drawable.ic_home))
        bottomNavBar.add(MeowBottomNavigation.Model(3, R.drawable.ic_packagevalue))
        bottomNavBar.add(MeowBottomNavigation.Model(4, R.drawable.ic_account))
        bottomNavBar.show(2)

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                bottomNavBar.show(position)
                when (position) {
                    0 -> txtTitle.text = getString(R.string.package_request1)
                    1 -> txtTitle.text = getString(R.string.level_chart)
                    2 -> txtTitle.text = getString(R.string.home)
                    3 -> txtTitle.text = getString(R.string.package_value)
                    4 -> txtTitle.text = getString(R.string.account)
                }
            }

        })

        bottomNavBar.setOnShowListener {
            // YOUR CODES
            when (it.id) {
                0 -> viewPager.currentItem = 0
                1 -> viewPager.currentItem = 1
                2 -> viewPager.currentItem = 2
                3 -> viewPager.currentItem = 3
                4 -> viewPager.currentItem = 4
                else -> ""
            }
        }

        bottomNavBar.setOnClickMenuListener {
            // YOUR CODES
            when (it.id) {
                0 -> viewPager.currentItem = 0
                1 -> viewPager.currentItem = 1
                2 -> viewPager.currentItem = 2
                3 -> viewPager.currentItem = 3
                4 -> viewPager.currentItem = 4
                else -> ""
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)

        val item = menu.findItem(R.id.action_edit)
        item.isVisible = false
        val item1 = menu.findItem(R.id.action_notification)
        item1.isVisible = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notification -> {
                startActivity(Intent(this, Notification::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog() {
        // custom dialog
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_custom)
        dialog.setCancelable(false)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val txtButton = dialog.findViewById(R.id.txtButton) as TextViewDrawableSize

        txtButton.setOnClickListener {
            startActivity(Intent(this, ChangePassword::class.java))
        }

        dialog.show()
    }

    override fun success() {
        Toast.makeText(this, "Number verified successfully", Toast.LENGTH_SHORT)
            .show()
        customDialog!!.dismiss()

        showDialog()
    }
}
