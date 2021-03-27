package com.channelpartner

import android.app.Application
import androidx.multidex.MultiDex
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}