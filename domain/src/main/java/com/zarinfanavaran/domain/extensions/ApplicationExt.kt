package com.zarinfanavaran.domain.extensions

import android.app.Application
import android.os.Build
import java.util.*

@Suppress("DEPRECATION")
@SuppressWarnings("DEPRECATION")
fun Application.setLanguage(language: String) {
	val locale = Locale(language)
	Locale.setDefault(locale)

	val resources = applicationContext.resources
	val configuration = resources.configuration

	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		configuration.setLocale(locale)
	else
		configuration.locale = locale

	configuration.setLayoutDirection(locale)
	resources.updateConfiguration(configuration, applicationContext.resources.displayMetrics)

	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		this.applicationContext.createConfigurationContext(configuration)
}