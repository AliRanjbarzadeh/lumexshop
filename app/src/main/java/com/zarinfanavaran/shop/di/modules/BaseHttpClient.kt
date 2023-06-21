package com.zarinfanavaran.shop.di.modules

import android.annotation.SuppressLint
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.zarinfanavaran.shop.BuildConfig.DEBUG
import com.zarinfanavaran.shop.di.interceptors.ModifyHeadersInterceptor
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.*

/**
 * Created by Ali Ranjbarzadeh on 9/29/2022 AD.
 */
@Singleton
class BaseHttpClient @Inject constructor(modifyHeadersInterceptor: ModifyHeadersInterceptor) {
	val okHttpClient = OkHttpClient()
			.newBuilder()
			.connectTimeout(30, TimeUnit.SECONDS)
			.writeTimeout(60, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.addNetworkInterceptor(modifyHeadersInterceptor)
			.apply {
				if (DEBUG) {
					addNetworkInterceptor(StethoInterceptor())
				}

				try {
					val trustAllCerts: Array<TrustManager> = arrayOf(
						@SuppressLint("CustomX509TrustManager")
						object : X509TrustManager {
							@SuppressLint("TrustAllX509TrustManager")
							override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
							}

							@SuppressLint("TrustAllX509TrustManager")
							override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
							}

							override fun getAcceptedIssuers(): Array<X509Certificate> {
								return arrayOf()
							}
						}
					)

					val sslContext = SSLContext.getInstance("SSL")
					sslContext.init(null, trustAllCerts, SecureRandom())

					sslSocketFactory(sslContext.socketFactory, trustAllCerts.first() as X509TrustManager)
					hostnameVerifier { hostname, session -> true }
				} catch (_: Exception) {
				}
			}
			.build()
}