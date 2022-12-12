package com.zarinfanavaran.domain.models

import androidx.annotation.DrawableRes

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class Slide(
	@DrawableRes var image: Int,
	var color: String
)