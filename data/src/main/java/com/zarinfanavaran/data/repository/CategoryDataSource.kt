package com.zarinfanavaran.data.repository

import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.models.CategoryDetail
import com.zarinfanavaran.domain.util.NetworkResult

interface CategoryDataSource {
	suspend fun getCategories(): NetworkResult<List<Category>>
	suspend fun getDetail(categoryId: Int): NetworkResult<CategoryDetail>
}