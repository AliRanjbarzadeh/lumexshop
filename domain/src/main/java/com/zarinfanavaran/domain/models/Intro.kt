package com.zarinfanavaran.domain.models

import androidx.annotation.DrawableRes

/**
 * Created by Ali Ranjbarzadeh on 10/15/2022 AD.
 */
data class Intro(
	var title: String,
	var description: String,
	var btnText: String,
	@DrawableRes var image: Int
)