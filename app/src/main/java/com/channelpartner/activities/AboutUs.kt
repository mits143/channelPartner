package com.channelpartner.activities

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.channelpartner.Constants.Companion.ABOUT_US
import com.channelpartner.Constants.Companion.IMAGE_URL
import com.channelpartner.R
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.toolbar.*


class AboutUs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        setSupportActionBar(toolBar)
        txtTitle.text = getString(R.string.about_us)
        toolBar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        init()
    }

    fun init(){
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(IMAGE_URL +  ABOUT_US);
    }
}
