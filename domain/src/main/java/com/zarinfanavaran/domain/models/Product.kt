package com.zarinfanavaran.domain.models

import androidx.annotation.DrawableRes
import com.zarinfanavaran.domain.extensions.priceFormat

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class Product(
	val title: String,
	val price: Int,
	val discount: Int,
	@DrawableRes val image: Int,
) {

	var timerTime: Long = 0

	fun getFormatRealPrice(): String {
		return price.priceFormat()
	}

	fun getFormatPrice(): String {
		return (price - discount).priceFormat()
	}

	fun getDiscountPercent(): String {
		return "${((discount * 100) / price)}%";
	}

	override fun toString(): String {
		return "Product(title='$title', price=$price, discount=$discount, image=$image, timerTime=$timerTime)"
	}
}
