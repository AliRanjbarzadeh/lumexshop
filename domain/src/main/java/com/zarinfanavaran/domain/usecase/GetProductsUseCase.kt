package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.repository.ProductRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductsUseCase @Inject constructor(private val repository: ProductRepository) {
	suspend operator fun invoke(params: Map<String, Any?>): NetworkResult<MyResponse<List<Product>, Meta>> = repository.getProducts(params)
}