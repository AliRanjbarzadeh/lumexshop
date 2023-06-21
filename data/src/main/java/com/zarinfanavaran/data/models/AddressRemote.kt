package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Address

data class AddressRemote(
	val id: Int
) : ResponseObject<Address> {
	override fun toDomain(): Address = Address(id)
}
