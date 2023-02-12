package com.zarinfanavaran.domain.models

data class ProductDetail(
	val id: Int,
	val nameFa: String,
	val nameEn: String,
	val summary: String,
	val clubIsActive: Int,
	val pointAmount: Int,
	val visitCount: Int,
	val commentCount: Int,
	val questionCount: Int,
	val rateAvg: Float,
	val suggestedCount: Int,
	val wishlistable: Int,
	val commentable: Int,
	val inStockNotificationable: Int,
	val inUserWishlist: Int,
	val items: List<Item>,
	val media: Media?,
	val mainAttributes: List<MainAttribute>,
	val attributes: List<MainAttribute>,
) {
	data class Item(
		val id: Int,
		val isMain: Int,
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
		val color: Color?,
		val guarantee: Guarantee?,
		val media: Media?,
	) {
		data class Guarantee(
			val id: Int,
			val name: String
		)
	}

	data class MainAttribute(
		val id: Int,
		val summaryFa: String,
		val summaryEn: String,
		val content: String,
		val isDefault: Int,
		val isMain: Int,
		val attribute: Attribute,
		val options: List<Option>,
	) {
		data class Attribute(
			val id: Int,
			val name: String,
			val type: String,
			val media: Media?,
		)

		data class Option(
			val id: Int,
			val nameFa: String,
			val nameEn: String,
		)
	}
}