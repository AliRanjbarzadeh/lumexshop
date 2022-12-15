package com.zarinfanavaran.domain.repository

import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.util.NetworkResult

interface CategoryRepository {
	suspend fun getCategories(): NetworkResult<List<Category>>
}