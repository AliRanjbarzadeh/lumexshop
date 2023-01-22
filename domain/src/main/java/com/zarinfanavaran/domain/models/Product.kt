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
	val nameFa: String,
	val nameEn: String,
	val summary: String,
	val clubIsActive: Int,
	val pointAmount: Int,
	val visitCount: Int,
	val commentCount: Int,
	val rateAvg: Float,
	val wishListable: Int,
	val commentable: Int,
	val inStockNotificationable: Int,
	val isSpecial: Int,
	val stock: Int,
	val inStock: Int,
	val maxCartQty: Int,
	val hasDiscount: Int,
	val discountType: String,
	val price: Int,
	val discountAmount: Int,
	val discounted: Int,
	val discountInPercent: Float,
	val discountInAmount: Int,
	val pricePrettified: String,
	val discountedPrettified: String,
	val discountInAmountPrettified: String,
	val media: Media?,
) : Parcelable {

	@IgnoredOnParcel
	val priceFormat = price.priceFormat()

	@IgnoredOnParcel
	val salePriceFormat = discounted.priceFormat()

	@IgnoredOnParcel
	val discountPercentString = "$discountInPercent%"
}
