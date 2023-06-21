package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.CreditCard

data class CreditCardRemote(
	val id: Int
): ResponseObject<CreditCard> {
	override fun toDomain(): CreditCard = CreditCard(id)
}