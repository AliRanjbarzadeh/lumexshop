package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Category

data class CategoryRemote(
	val id: Int,
	val name: String,
	val level: Int,
	val media: MediaRemote?,
	val children: List<CategoryRemote>?
) : ResponseObject<Category> {
	override fun toDomain(): Category = Category(id, name, level, media?.toDomain(), children?.map { it.toDomain() })
}