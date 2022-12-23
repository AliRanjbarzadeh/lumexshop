package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Color

data class ColorRemote(
	val id: Int,
	@SerializedName("name_fa")
	val nameFa: String,
	@SerializedName("name_en")
	val nameEn: String,
	val media: MediaRemote?
) : ResponseObject<Color> {
	override fun toDomain(): Color = Color(id, nameFa, nameEn, media?.toDomain())

}