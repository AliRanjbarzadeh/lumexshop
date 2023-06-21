package com.zarinfanavaran.domain.models

import android.os.Parcelable
import com.zarinfanavaran.domain.extensions.priceFormat
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
@Parcelize
data class Product(
	val id: Int,
	var nameFa: String = "",
	val nameEn: String = "",
	val summary: String = "",
	val clubIsActive: Int = 0,
	val pointAmount: Int = 0,
	val visitCount: Int = 0,
	val commentCount: Int = 0,
	val rateAvg: Float = 0f,
	val wishListable: Int = 0,
	val commentable: Int = 0,
	val inStockNotificationable: Int = 0,
	val isSpecial: Int = 0,
	val stock: Int = 0,
	val inStock: Int = 0,
	val maxCartQty: Int = 0,
	val hasDiscount: Int = 0,
	val discountType: String = "",
	var price: Int = 0,
	val discountAmount: Int = 0,
	val discounted: Int = 0,
	val discountInPercent: Float = 0f,
	val discountInAmount: Int = 0,
	val pricePrettified: String = "",
	val discountedPrettified: String = "",
	val discountInAmountPrettified: String = "",
	val media: Media? = null,
	val items: List<Item>? = null
) : Parcelable {

	@Parcelize
	data class Item(
		val id: Int,
		val color: Color?
	) : Parcelable

	@IgnoredOnParcel
	val priceFormat = price.priceFormat()

	@IgnoredOnParcel
	val salePriceFormat = discounted.priceFormat()

	@IgnoredOnParcel
	val discountPercentString = "$discountInPercent%"

	@IgnoredOnParcel
	val rate: String = rateAvg.toString()
}
