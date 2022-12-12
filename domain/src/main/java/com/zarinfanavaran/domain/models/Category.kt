package com.zarinfanavaran.domain.models

import androidx.annotation.DrawableRes

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class Category(
	val title: String,
	@DrawableRes val icon: Int,
	@DrawableRes val image: Int,
) {
	var isHasMore = false

	var subItems = mutableListOf<Category>()
}
