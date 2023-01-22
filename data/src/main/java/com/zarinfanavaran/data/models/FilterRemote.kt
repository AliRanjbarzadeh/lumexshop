package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Filter

data class FilterRemote(
	val id: Int,
	val name: String,
	val type: String,
	@SerializedName("has_options")
	val hasOption: Int,
	val options: List<Option>?
) : ResponseObject<Filter> {
	data class Option(
		val id: Int,
		@SerializedName("name_fa")
		val nameFa: String,
		@SerializedName("name_en")
		val nameEn: String
	) : ResponseObject<Filter.Option> {
		override fun toDomain(): Filter.Option = Filter.Option(id, nameFa, nameEn)
	}

	override fun toDomain(): Filter =
		Filter(id, name, type, hasOption, options?.map { it.toDomain() })
}