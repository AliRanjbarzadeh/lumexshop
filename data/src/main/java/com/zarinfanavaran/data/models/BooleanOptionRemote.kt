package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.BooleanOption

data class BooleanOptionRemote(
	val key: String,
	val name: String
) : ResponseObject<BooleanOption> {
	override fun toDomain(): BooleanOption = BooleanOption(key, name)
}
