package com.zarinfanavaran.domain.extensions

import android.graphics.Typeface
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textview.MaterialTextView

/**
 * Created by Ali Ranjbarzadeh on 10/20/2022 AD.
 */

fun BottomNavigationView.changeFont(typeface: Typeface) {
	for (i in 0 until menu.size()) {
		val menuItemView =
			findViewById<View?>(menu.getItem(i).itemId)

		val menuLabelSmall =
			menuItemView?.findViewById<MaterialTextView?>(com.google.android.material.R.id.navigation_bar_item_small_label_view)
		menuLabelSmall?.typeface = typeface

		val menuLabelLarge =
			menuItemView?.findViewById<MaterialTextView?>(com.google.android.material.R.id.navigation_bar_item_large_label_view)
		menuLabelLarge?.typeface = typeface
	}
}