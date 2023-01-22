package com.zarinfanavaran.domain.repository

import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.models.CategoryDetail
import com.zarinfanavaran.domain.util.NetworkResult

interface CategoryRepository {
	suspend fun getCategories(): NetworkResult<List<Category>>

	suspend fun getDetail(categoryId: Int): NetworkResult<CategoryDetail>
}