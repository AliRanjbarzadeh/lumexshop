package com.zarinfanavaran.shop.di.interceptors

import android.os.Build
import android.util.Log
import com.zarinfanavaran.domain.BuildConfig
import com.zarinfanavaran.domain.extensions.loadFromSp
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by Ali Ranjbarzadeh on 9/29/2022 AD.
 */
class ModifyHeadersInterceptor @Inject constructor() : Interceptor {
	val TAG = "ModifyHeadersLog"
	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request()
		val builder = request.newBuilder()

		try {
			var authorization = loadFromSp(BuildConfig.SESSION_TOKEN, "")
			if (authorization.isEmpty()) {
				authorization = loadFromSp(BuildConfig.REGISTER_TOKEN, BuildConfig.DEFAULT_TOKEN)
			}
			val language = loadFromSp(BuildConfig.SESSION_LANGUAGE, BuildConfig.DEFAULT_LANGUAGE)
			val pushToken = loadFromSp(BuildConfig.SESSION_PUSH_TOKEN, "")
			val cartToken = loadFromSp(BuildConfig.SESSION_CART_TOKEN, "")

//			builder.header("Cache-Control", "no-cache")
			builder.header("Accept", "application/json")

			if (request.method.lowercase().equals("post") && !request.url.toString().contains("upload"))
				builder.header("Content-Type", "application/json")

			builder.header("User-Agent", BuildConfig.USER_AGENT)
			builder.header("AppVersion", BuildConfig.APP_VERSION)
			builder.header("AppLanguage", language)
			builder.header("DeviceVersion", Build.VERSION.RELEASE)
			builder.header("DeviceModel", Build.MODEL)
			builder.header("DeviceManufacture", Build.MANUFACTURER)
			builder.header("DevicePushToken", pushToken)
			builder.header(BuildConfig.AUTHORIZATION, "${BuildConfig.TOKEN_PREFIX}$authorization")
			builder.header(BuildConfig.CART_TOKEN_NAME, cartToken)
		} catch (ex: Exception) {
			ex.printStackTrace()
			Log.e("HeaderLog", "intercept: ${ex.message}")
		}

		return chain.proceed(builder.build())
	}
}