package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.SortOption

data class SortOptionRemote(
	val key: String,
	val name: String
) : ResponseObject<SortOption> {
	override fun toDomain(): SortOption = SortOption(key, name)
}
