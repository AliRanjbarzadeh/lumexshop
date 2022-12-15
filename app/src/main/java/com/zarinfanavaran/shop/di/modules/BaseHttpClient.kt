package com.zarinfanavaran.shop.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.zarinfanavaran.shop.BuildConfig.DEBUG
import com.zarinfanavaran.shop.di.interceptors.ModifyHeadersInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Ali Ranjbarzadeh on 9/29/2022 AD.
 */
class BaseHttpClient @Inject constructor(modifyHeadersInterceptor: ModifyHeadersInterceptor) {
	val okHttpClient = OkHttpClient()
		.newBuilder()
		.connectTimeout(30, TimeUnit.SECONDS)
		.writeTimeout(60, TimeUnit.SECONDS)
		.readTimeout(30, TimeUnit.SECONDS)
		.apply {
			if (DEBUG) {
				addNetworkInterceptor(StethoInterceptor())
			}
		}
		.addNetworkInterceptor(modifyHeadersInterceptor)
		.build()
}