package com.zarinfanavaran.presentation.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zarinfanavaran.presentation.R

/**
 * Created by Ali Ranjbarzadeh on 11/10/2022 AD.
 */

fun BottomNavigationView.setBadge(@IdRes tabResId: Int, badgeValue: ObservableInt) {
	getOrCreateBadge(this, tabResId)?.let { badge ->
		badge.visibility = if (badgeValue.get() > 0) {
			badge.text = badgeValue.get().toString()
			View.VISIBLE
		} else {
			View.GONE
		}
		badgeValue.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
			override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
				badge.visibility = if (badgeValue.get() > 0) {
					badge.text = if (badgeValue.get() > 99) {
						"99"
					} else {
						badgeValue.get().toString()
					}
					View.VISIBLE
				} else {
					View.GONE
				}
			}
		})
	}
}

private fun getOrCreateBadge(bottomMenu: View, @IdRes tabResId: Int): TextView? {
	val parentView = bottomMenu.findViewById<ViewGroup>(tabResId)
	val iconView = parentView.findViewById<FrameLayout>(com.google.android.material.R.id.navigation_bar_item_icon_container)
	return parentView?.let {
		var badge = parentView.findViewById<TextView>(R.id.txtMenuItemBadge)
		if (badge == null) {
			LayoutInflater.from(parentView.context).inflate(R.layout.bottom_nav_badge, parentView, true)
			badge = parentView.findViewById(R.id.txtMenuItemBadge)
		}
		val params = badge.layoutParams as FrameLayout.LayoutParams
		iconView.post {
			params.topMargin = (iconView.measuredHeight / 1.2).toInt()
			params.marginStart = (iconView.measuredWidth / 0.9).toInt()
			badge.layoutParams = params
		}
		badge
	}
}