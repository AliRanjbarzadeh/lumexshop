package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.repository.CategoryRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(private val repository: CategoryRepository) {
	suspend operator fun invoke(): NetworkResult<List<Category>> = repository.getCategories()
}