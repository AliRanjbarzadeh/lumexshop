package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Province

data class ProvinceRemote(
	val id: Int,
	val name: String
) : ResponseObject<Province> {
	override fun toDomain(): Province = Province(id, name)
}