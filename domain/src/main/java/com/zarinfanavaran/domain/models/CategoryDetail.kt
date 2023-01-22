package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDetail(
	val category: Category,
	val brands: MutableList<Brand>,
	val colors: List<Color>,
	val mostSoldProducts: List<Product>,
	val mostViewedProducts: List<Product>,
	val filters: List<Filter>,
	val priceRange: PriceRange?,
	val sortOptions: List<SortOption>?,
	val booleanOptions: List<BooleanOption>?,
	val categoryIds: List<Int>?,
) : Parcelable