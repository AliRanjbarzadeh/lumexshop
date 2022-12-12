package com.zarinfanavaran.presentation

import android.content.Intent
import android.os.Bundle
import com.zarinfanavaran.domain.BuildConfig.USER
import com.zarinfanavaran.domain.extensions.loadFromSp
import com.zarinfanavaran.domain.extensions.saveToSp
import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.presentation.base.BaseActivity
import com.zarinfanavaran.presentation.base.BaseObject
import com.zarinfanavaran.presentation.databinding.ActivityIntroBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ali Ranjbarzadeh on 9/29/2022 AD.
 */
@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		BaseObject.user = loadFromSp(USER, User())

		//go to main activity if already seen
		if (loadFromSp("splash", false)) {
			Intent(this, MainActivity::class.java).apply {
				startActivity(this)
				finish()
			}
		}

		//save already seen splash in session
		saveToSp("splash", true)
	}
}