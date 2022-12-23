package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.CategoryDetail

data class CategoryDetailRemote(
	val category: CategoryRemote,
	val brands: List<BrandRemote>
) : ResponseObject<CategoryDetail> {
	override fun toDomain(): CategoryDetail {
		TODO("Not yet implemented")
	}
}