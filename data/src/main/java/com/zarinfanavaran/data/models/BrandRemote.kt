package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Brand

data class BrandRemote(
	val id: Int,
	@SerializedName("name_fa")
	val nameFa: String,
	@SerializedName("name_en")
	val nameEn: String,
	val description: String,
	val mediaRemote: MediaRemote?
) : ResponseObject<Brand> {
	override fun toDomain(): Brand = Brand(id, nameFa, nameEn, description, mediaRemote?.toDomain())
}