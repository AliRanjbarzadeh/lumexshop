package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.WishList

data class WishListRemote(
	val id: Int,
	val product: ProductRemote
) : ResponseObject<WishList> {
	override fun toDomain(): WishList = WishList(id, product.toDomain())
}
