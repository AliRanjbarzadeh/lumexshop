package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.City

data class CityRemote(
	val id: Int,
	val name: String
) : ResponseObject<City> {
	override fun toDomain(): City = City(id, name)
}
