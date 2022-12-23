package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName

data class BrandRemote(
	val id: Int,
	@SerializedName("name_fa")
	val nameFa: String,
	@SerializedName("name_en")
	val nameEn: String,
	val description: String,
	val mediaRemote: MediaRemote?
)