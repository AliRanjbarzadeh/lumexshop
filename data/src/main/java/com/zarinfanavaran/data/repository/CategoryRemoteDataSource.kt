package com.zarinfanavaran.data.repository

import android.util.Log
import com.google.gson.Gson
import com.zarinfanavaran.data.api.ApiService
import com.zarinfanavaran.data.exceptions.NetworkExceptionHandler
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRemoteDataSource @Inject constructor(
	private val apiService: ApiService,
	private val apiExceptionHandler: NetworkExceptionHandler,
	private val gson: Gson
) : CategoryDataSource {
	override suspend fun getCategories(): NetworkResult<List<Category>> {
		return try {
			val result = apiService.fetchCategoriesAsync().await()
			NetworkResult.Success(result.data.map { it.toDomain() })
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}
}