package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.PriceRange

data class PriceRangeRemote(
	val min: Int,
	val max: Int,
) : ResponseObject<PriceRange> {
	override fun toDomain(): PriceRange = PriceRange(min, max)
}