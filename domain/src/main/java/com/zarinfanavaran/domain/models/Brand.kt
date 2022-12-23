package com.zarinfanavaran.domain.models

import androidx.annotation.DrawableRes

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class Brand(
	val id: Int,
	val nameFa: String,
	val nameEn: String,
	val description: String,
	val media: Media?
)
