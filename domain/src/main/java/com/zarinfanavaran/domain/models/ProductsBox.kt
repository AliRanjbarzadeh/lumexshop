package com.zarinfanavaran.domain.models

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class ProductsBox(
	@DrawableRes val icon: Int,
	val title: String,
	val image: Drawable,
	val products: MutableList<Any>
)