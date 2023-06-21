package com.zarinfanavaran.data.repository

import com.google.gson.Gson
import com.zarinfanavaran.data.api.ApiService
import com.zarinfanavaran.data.exceptions.NetworkExceptionHandler
import com.zarinfanavaran.domain.models.City
import com.zarinfanavaran.domain.models.Media
import com.zarinfanavaran.domain.models.Province
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalRemoteDataSource @Inject constructor(
	private val apiService: ApiService,
	private val apiExceptionHandler: NetworkExceptionHandler,
	private val gson: Gson
) : GlobalDataSource {
	override suspend fun uploadFile(file: MultipartBody.Part): NetworkResult<Media> {
		return try {
			val result = apiService.uploadAsync(file).await()
			NetworkResult.Success(result.data.toDomain())
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun getProvinces(): NetworkResult<List<Province>> {
		return try {
			val result = apiService.fetchProvincesAsync().await()
			NetworkResult.Success(result.data.map { it.toDomain() })
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun getCities(provinceId: Int): NetworkResult<List<City>> {
		return try {
			val result = apiService.fetchCitiesAsync(provinceId).await()
			NetworkResult.Success(result.data.map { it.toDomain() })
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}
}