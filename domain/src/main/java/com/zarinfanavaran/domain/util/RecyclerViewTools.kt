package com.zarinfanavaran.domain.util

import android.view.View

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
interface RecyclerViewTools {
	fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int = -1) {}
	fun <T> onItemSelect(position: Int, view: View, item: T, parentPosition: Int = -1) {}
	fun <T> onDeleteClick(position: Int, view: View, item: T, parentPosition: Int = -1) {}
	fun <T> onFilter(position: Int, item: T, search: String, parentPosition: Int = -1) {}
}