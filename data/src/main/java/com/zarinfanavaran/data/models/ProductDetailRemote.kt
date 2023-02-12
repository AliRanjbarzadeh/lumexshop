package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.ProductDetail

data class ProductDetailRemote(
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
	@SerializedName("question_count")
	val questionCount: Int,
	@SerializedName("rate_avg")
	val rateAvg: Float,
	@SerializedName("suggested_count")
	val suggestedCount: Int,
	@SerializedName("wishlistable")
	val wishlistable: Int,
	@SerializedName("commentable")
	val commentable: Int,
	@SerializedName("in_stock_notificationable")
	val inStockNotificationable: Int,
	@SerializedName("in_user_wishlist")
	val inUserWishlist: Int,
	val items: List<Item>,
	val media: MediaRemote?,
	@SerializedName("main_attributes")
	val mainAttributes: List<MainAttribute>,
	val attributes: List<MainAttribute>,
) : ResponseObject<ProductDetail> {
	override fun toDomain(): ProductDetail = ProductDetail(
		id, nameFa, nameEn, summary, clubIsActive, pointAmount,
		visitCount, commentCount, questionCount, rateAvg, suggestedCount,
		wishlistable, commentable, inStockNotificationable,
		inUserWishlist, items.map { it.toDomain() }, media?.toDomain(),
		mainAttributes.map { it.toDomain() }, attributes.map { it.toDomain() }
	)

	data class Item(
		val id: Int,
		@SerializedName("is_main")
		val isMain: Int,
		@SerializedName("is_special")
		val isSpecial: Int,
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
		val color: ColorRemote?,
		val guarantee: Guarantee?,
		val media: MediaRemote?,
	) : ResponseObject<ProductDetail.Item> {
		override fun toDomain(): ProductDetail.Item = ProductDetail.Item(
			id, isMain, isSpecial, stock, inStock, maxCartQty, hasDiscount, discountType,
			price, discountAmount, discounted, discountInPercent,
			discountInAmount, pricePrettified, discountedPrettified,
			discountInAmountPrettified, color?.toDomain(), guarantee?.toDomain(), media?.toDomain()
		)

		data class Guarantee(
			val id: Int,
			val name: String
		) : ResponseObject<ProductDetail.Item.Guarantee> {
			override fun toDomain(): ProductDetail.Item.Guarantee = ProductDetail.Item.Guarantee(id, name)
		}
	}

	data class MainAttribute(
		val id: Int,
		@SerializedName("summary_fa")
		val summaryFa: String,
		@SerializedName("summary_en")
		val summaryEn: String,
		val content: String,
		@SerializedName("is_default")
		val isDefault: Int,
		@SerializedName("is_main")
		val isMain: Int,
		val attribute: Attribute,
		val options: List<Option>,
	) : ResponseObject<ProductDetail.MainAttribute> {

		override fun toDomain(): ProductDetail.MainAttribute = ProductDetail.MainAttribute(
			id, summaryFa, summaryEn, content, isDefault,
			isMain, attribute.toDomain(), options.map { it.toDomain() }
		)

		data class Attribute(
			val id: Int,
			val name: String,
			val type: String,
			val media: MediaRemote?,
		) : ResponseObject<ProductDetail.MainAttribute.Attribute> {
			override fun toDomain(): ProductDetail.MainAttribute.Attribute = ProductDetail.MainAttribute.Attribute(
				id, name, type, media?.toDomain()
			)
		}

		data class Option(
			val id: Int,
			@SerializedName("name_fa")
			val nameFa: String,
			@SerializedName("name_en")
			val nameEn: String,
		) : ResponseObject<ProductDetail.MainAttribute.Option> {
			override fun toDomain(): ProductDetail.MainAttribute.Option = ProductDetail.MainAttribute.Option(
				id, nameFa, nameEn
			)
		}
	}
}