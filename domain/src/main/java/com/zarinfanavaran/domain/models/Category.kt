package com.zarinfanavaran.domain.models

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class Category(
	val id: Int,
	val name: String,
	val level: Int,
	val media: Media?,
	val children: List<Category>?
) {
	var isHasMore = false
}
