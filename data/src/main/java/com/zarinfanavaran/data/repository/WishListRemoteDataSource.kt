package com.zarinfanavaran.data.repository

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.zarinfanavaran.data.api.ApiService
import com.zarinfanavaran.data.exceptions.NetworkExceptionHandler
import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.WishList
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishListRemoteDataSource @Inject constructor(
	private val apiService: ApiService,
	private val apiExceptionHandler: NetworkExceptionHandler,
	private val gson: Gson
) : WishListDataSource {
	override suspend fun getWishLists(params: Map<String, Any?>): NetworkResult<MyResponse<List<WishList>, Meta>> {
		return try {
			val result = apiService.fetchWishListsAsync(params).await()
			NetworkResult.Success(
				MyResponse(
					result.status, result.success, result.message,
					result.data.map { it.toDomain() },
					result.errors,
					result.meta.toDomain()
				)
			)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun addToWishList(productId: Int): NetworkResult<JsonElement> {
		return try {
			val result = apiService.addToWishListAsync(productId).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun deleteFromWishList(productId: Int): NetworkResult<JsonElement> {
		return try {
			val result = apiService.deleteFromWishListAsync(productId).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}
}