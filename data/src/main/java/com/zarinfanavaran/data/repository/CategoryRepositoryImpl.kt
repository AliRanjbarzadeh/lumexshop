package com.zarinfanavaran.data.repository

import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.models.CategoryDetail
import com.zarinfanavaran.domain.repository.CategoryRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(private val dataSource: CategoryDataSource) :
	CategoryRepository {
	override suspend fun getCategories(): NetworkResult<List<Category>> = dataSource.getCategories()

	override suspend fun getDetail(categoryId: Int): NetworkResult<CategoryDetail> =
		dataSource.getDetail(categoryId)

}