package com.zarinfanavaran.data.repository

import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.repository.ProductRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(private val dataSource: ProductDataSource) : ProductRepository {
	override suspend fun getProducts(params: Map<String, Any?>): NetworkResult<MyResponse<List<Product>, Meta>> = dataSource.getProducts(params)
}