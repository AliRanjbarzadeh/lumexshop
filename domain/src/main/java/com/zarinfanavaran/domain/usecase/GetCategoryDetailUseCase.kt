package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.CategoryDetail
import com.zarinfanavaran.domain.repository.CategoryRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoryDetailUseCase @Inject constructor(private val repository: CategoryRepository) {
	suspend operator fun invoke(categoryId: Int): NetworkResult<CategoryDetail> = repository.getDetail(categoryId)
}