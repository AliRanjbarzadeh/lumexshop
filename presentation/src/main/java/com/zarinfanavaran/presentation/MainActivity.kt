package com.zarinfanavaran.presentation

import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.ObservableInt
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.zarinfanavaran.domain.BuildConfig.SESSION_LOGIN
import com.zarinfanavaran.domain.extensions.changeFont
import com.zarinfanavaran.domain.extensions.loadFromSp
import com.zarinfanavaran.domain.extensions.saveToSp
import com.zarinfanavaran.presentation.base.BaseActivity
import com.zarinfanavaran.presentation.databinding.ActivityMainBinding
import com.zarinfanavaran.presentation.util.blur.BlurKit
import com.zarinfanavaran.presentation.util.setBadge
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), NavigationBarView.OnItemReselectedListener, NavController.OnDestinationChangedListener {

	var bottomNavHeight: Int = 0
	lateinit var mNavHostFragment: NavHostFragment
	lateinit var mNavController: NavController
	lateinit var profileNavGraph: NavGraph
	val shopCartBadge = ObservableInt(2)

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
			if (binding.bottomMenu.selectedItemId == R.id.sale_nav) {
				onNavigationItemReselected(binding.bottomMenu.menu.findItem(R.id.sale_nav))
				return@setOnClickListener
			}
			binding.bottomMenu.selectedItemId = R.id.sale_nav
		}

		profileNavGraph = mNavController.graph.findNode(R.id.profile_nav) as NavGraph
		//set profile nav destination depend on user login
		profileNavGraph.setStartDestination(
			if (!loadFromSp(SESSION_LOGIN, false)) {
				R.id.loginMobileFragment
			} else {
				R.id.profileFragment
			}
		)

//		window.statusBarColor = ContextCompat.getColor(this, R.color.white)
//		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
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

	override fun onNavigationItemReselected(item: MenuItem) {
		val selectedNavGraph = mNavController.graph.findNode(item.itemId) as NavGraph
		mNavController.popBackStack(selectedNavGraph.startDestinationId, false)
	}

	private fun setupBottomNavigationBar() {
		mNavHostFragment = supportFragmentManager.findFragmentById(R.id.base_nav_host) as NavHostFragment
		mNavController = mNavHostFragment.navController

		binding.bottomMenu.setupWithNavController(mNavController)
		binding.bottomMenu.setOnItemReselectedListener(this)
		mNavController.addOnDestinationChangedListener(this)

		binding.bottomMenu.setBadge(R.id.shopcart_nav, shopCartBadge)
	}

	override fun login() {
		profileNavGraph.setStartDestination(R.id.profileFragment)
		saveToSp(SESSION_LOGIN, true)
	}

	override fun logout() {
	}

	override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
		if (destination.parent?.id == R.id.sale_nav) {
			binding.middleButton.setBackgroundResource(R.drawable.fab_bottom_menu_selected)
		} else {
			binding.middleButton.setBackgroundResource(R.drawable.fab_bottom_menu)
		}
	}
}