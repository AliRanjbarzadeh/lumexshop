package com.zarinfanavaran.domain.models

import androidx.annotation.DrawableRes

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class BrandsBox(
	@DrawableRes val icon: Int,
	val title: String,
	val items: MutableList<Any>
)