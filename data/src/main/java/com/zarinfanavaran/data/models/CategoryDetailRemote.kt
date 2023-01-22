package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.CategoryDetail

data class CategoryDetailRemote(
	val category: CategoryRemote,
	val brands: List<BrandRemote>,
	val colors: List<ColorRemote>,
	@SerializedName("most_sold_products")
	val mostSoldProducts: List<ProductRemote>,
	@SerializedName("most_viewed_products")
	val mostViewedProducts: List<ProductRemote>,
	val filters: List<FilterRemote>,
	@SerializedName("price_range")
	val priceRange: PriceRangeRemote?,
	@SerializedName("sort_options")
	val sortOptions: List<SortOptionRemote>?,
	@SerializedName("boolean_options")
	val booleanOptions: List<BooleanOptionRemote>?,
	@SerializedName("category_ids")
	val categoryIds: List<Int>?,
) : ResponseObject<CategoryDetail> {
	override fun toDomain(): CategoryDetail = CategoryDetail(
		category.toDomain(),
		brands.map { it.toDomain() }.toMutableList(),
		colors.map { it.toDomain() },
		mostSoldProducts.map { it.toDomain() },
		mostViewedProducts.map { it.toDomain() },
		filters.map { it.toDomain() },
		priceRange?.toDomain(),
		sortOptions?.map { it.toDomain() },
		booleanOptions?.map { it.toDomain() },
		categoryIds
	)
}