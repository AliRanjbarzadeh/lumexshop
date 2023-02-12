package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Meta

/**
 * Created by Ali Ranjbarzadeh on 2023/01/29.
 */
data class MetaRemoot(
	@SerializedName("current_page")
	val currentPage: Int,
	val from: Int,
	@SerializedName("last_page")
	val lastPage: Int,
	@SerializedName("per_page")
	val perPage: Int,
	val to: Int,
	val total: Int,
) : ResponseObject<Meta> {
	override fun toDomain(): Meta = Meta(currentPage, from, lastPage, perPage, to, total)
}