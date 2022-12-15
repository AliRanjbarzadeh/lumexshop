package com.zarinfanavaran.shop.application

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.orhanobut.hawk.Hawk
import com.zarinfanavaran.domain.BuildConfig
import com.zarinfanavaran.domain.extensions.loadFromSp
import com.zarinfanavaran.domain.extensions.setLanguage
import com.zarinfanavaran.shop.R
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

@HiltAndroidApp
class App : MultiDexApplication() {
	override fun onCreate() {
		super.onCreate()
		initStetho()
		initHawk()
		initLanguage()
		initFont()
	}

	private fun initStetho() {
		Stetho.initializeWithDefaults(this)
	}

	private fun initHawk() {
		Hawk.init(this).build()
	}


	private fun initLanguage() {
		loadFromSp(BuildConfig.SESSION_LANGUAGE, BuildConfig.DEFAULT_LANGUAGE).also {
			setLanguage(it)
		}
	}

	private fun initFont() {
		ViewPump.init(
			ViewPump.builder()
				.addInterceptor(
					CalligraphyInterceptor(
						CalligraphyConfig.Builder()
							.setDefaultFontPath(getString(R.string.font_regular))
							.setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
							.build()
					)
				)
				.build()
		)
	}
}