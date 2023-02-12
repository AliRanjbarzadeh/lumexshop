package com.zarinfanavaran.data.repository

import com.google.gson.Gson
import com.zarinfanavaran.data.api.ApiService
import com.zarinfanavaran.data.exceptions.NetworkExceptionHandler
import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSource @Inject constructor(
	private val apiService: ApiService,
	private val apiExceptionHandler: NetworkExceptionHandler,
	private val gson: Gson
) : ProductDataSource {
	override suspend fun getProducts(params: Map<String, Any?>): NetworkResult<MyResponse<List<Product>, Meta>> {
		return try {
			val result = apiService.fetchProductsAsync(params).await()
			NetworkResult.Success(MyResponse(result.data.map { it.toDomain() }, result.meta.toDomain()))
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}
}