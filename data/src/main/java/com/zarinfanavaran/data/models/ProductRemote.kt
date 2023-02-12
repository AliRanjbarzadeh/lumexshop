package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Product

data class ProductRemote(
	val id: Int,
	@SerializedName("name_fa")
	val nameFa: String,
	@SerializedName("name_en")
	val nameEn: String,
	val summary: String,
	@SerializedName("club_is_active")
	val clubIsActive: Int,
	@SerializedName("point_amount")
	val pointAmount: Int,
	@SerializedName("visit_count")
	val visitCount: Int,
	@SerializedName("comment_count")
	val commentCount: Int,
	@SerializedName("rate_avg")
	val rateAvg: Float,
	@SerializedName("wishlistable")
	val wishListable: Int,
	@SerializedName("commentable")
	val commentable: Int,
	@SerializedName("in_stock_notificationable")
	val inStockNotificationable: Int,
	@SerializedName("is_special")
	val isSpecial: Int,
	@SerializedName("stock")
	val stock: Int,
	@SerializedName("in_stock")
	val inStock: Int,
	@SerializedName("max_cart_qty")
	val maxCartQty: Int,
	@SerializedName("has_discount")
	val hasDiscount: Int,
	@SerializedName("discount_type")
	val discountType: String,
	val price: Int,
	@SerializedName("discount_amount")
	val discountAmount: Int,
	val discounted: Int,
	@SerializedName("discount_in_percent")
	val discountInPercent: Float,
	@SerializedName("discount_in_amount")
	val discountInAmount: Int,
	@SerializedName("price_prettified")
	val pricePrettified: String,
	@SerializedName("discounted_prettified")
	val discountedPrettified: String,
	@SerializedName("discount_in_amount_prettified")
	val discountInAmountPrettified: String,
	val media: MediaRemote?,
	val items: List<Item>?
) : ResponseObject<Product> {
	override fun toDomain(): Product = Product(
		id,
		nameFa,
		nameEn,
		summary,
		clubIsActive,
		pointAmount,
		visitCount,
		commentCount,
		rateAvg,
		wishListable,
		commentable,
		inStockNotificationable,
		isSpecial,
		stock,
		inStock,
		maxCartQty,
		hasDiscount,
		discountType,
		price,
		discountAmount,
		discounted,
		discountInPercent,
		discountInAmount,
		pricePrettified,
		discountedPrettified,
		discountInAmountPrettified,
		media?.toDomain(),
		items?.map { it.toDomain() }
	)

	data class Item(
		val id: Int,
		val color: ColorRemote?
	) : ResponseObject<Product.Item> {
		override fun toDomain(): Product.Item = Product.Item(id, color?.toDomain())
	}
}
