package com.zarinfanavaran.presentation

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.zarinfanavaran.domain.BuildConfig
import com.zarinfanavaran.domain.BuildConfig.SESSION_LOGIN
import com.zarinfanavaran.domain.extensions.changeFont
import com.zarinfanavaran.domain.extensions.loadFromSp
import com.zarinfanavaran.domain.extensions.saveToSp
import com.zarinfanavaran.presentation.base.BaseActivity
import com.zarinfanavaran.presentation.databinding.ActivityMainBinding
import com.zarinfanavaran.presentation.login.LoginMobileFragmentDirections
import com.zarinfanavaran.presentation.util.blur.BlurKit
import com.zarinfanavaran.presentation.util.getCurrentNavHostByGraphId
import com.zarinfanavaran.presentation.util.setBadge
import com.zarinfanavaran.presentation.util.setupWithNavigationController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

	var bottomNavHeight: Int = 0
	lateinit var mNavHostFragment: NavHostFragment
	lateinit var mNavController: NavController
	lateinit var profileNavGraph: NavGraph
	val shopCartBadge = ObservableInt(2)
	private lateinit var controller: LiveData<NavController>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		BlurKit.init(this)

		//change bottom menu font
		val mTypeFace = Typeface.createFromAsset(assets, getString(R.string.font_regular))
		binding.bottomMenu.changeFont(mTypeFace)

		//setup bottom navigation with navController
		setupBottomNavigationBar()

		binding.bottomMenu.post { bottomNavHeight = binding.bottomMenu.measuredHeight }

		binding.middleButton.setOnClickListener {
//			if (binding.bottomMenu.selectedItemId == R.id.sale_nav) {
//				onNavigationItemReselected(binding.bottomMenu.menu.findItem(R.id.sale_nav))
//				return@setOnClickListener
//			}
			binding.bottomMenu.selectedItemId = R.id.sale_nav
		}

		binding.bottomMenu.getCurrentNavHostByGraphId(supportFragmentManager, R.id.profile_nav)?.also {
			profileNavGraph = it.navController.graph

			profileNavGraph.setStartDestination(
				if (!loadFromSp(SESSION_LOGIN, false)) {
					R.id.loginMobileFragment
				} else {
					R.id.profileFragment
				}
			)
		}
	}

	override fun bottomNavigationVisibility(isShow: Boolean) {
		val visibility = if (isShow) {
			View.VISIBLE
		} else {
			View.GONE
		}
		binding.middleButton.visibility = visibility
		binding.middleButtonShadow.visibility = visibility
		binding.bottomMenu.visibility = visibility
	}

	private fun setupBottomNavigationBar() {
		val navGraphIds = listOf(
			R.navigation.home_nav,
			R.navigation.categories_nav,
			R.navigation.sale_nav,
			R.navigation.shopcart_nav,
			R.navigation.profile_nav,
		)

		val navigationCallbacks = listOf(null, null, null, null, null)


		binding.bottomMenu.setupWithNavigationController(
			navGraphIds,
			navigationCallbacks,
			supportFragmentManager,
			R.id.base_nav_host,
			intent
		).observe(this) { navController ->
			mNavController = navController

			if (navController.graph.id == R.id.profile_nav) {
				if (navController.currentDestination?.id == R.id.loginMobileFragment) {
					if (loadFromSp(SESSION_LOGIN, false)) {
						val profileAction = LoginMobileFragmentDirections.actionLoginMobileFragmentToProfileFragment()
						navController.navigate(profileAction)
					}
				}
			}

			if (navController.graph.id == R.id.sale_nav) {
				binding.middleButton.setBackgroundResource(R.drawable.fab_bottom_menu_selected)
			} else {
				binding.middleButton.setBackgroundResource(R.drawable.fab_bottom_menu)
			}
		}

		binding.bottomMenu.setBadge(R.id.shopcart_nav, shopCartBadge)
	}

	override fun login() {
		profileNavGraph.setStartDestination(R.id.profileFragment)
		saveToSp(SESSION_LOGIN, true)
	}

	override fun logout() {
	}
}