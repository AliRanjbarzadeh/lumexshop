package com.zarinfanavaran.data.repository

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.zarinfanavaran.data.api.ApiService
import com.zarinfanavaran.data.exceptions.NetworkExceptionHandler
import com.zarinfanavaran.domain.models.CreditCard
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditCardRemoteDataSource @Inject constructor(
	private val apiService: ApiService,
	private val apiExceptionHandler: NetworkExceptionHandler,
	private val gson: Gson
) : CreditCardDataSource {
	override suspend fun getCreditCards(): NetworkResult<List<CreditCard>> {
		return try {
			val result = apiService.fetchCreditCardsAsync().await()
			NetworkResult.Success(result.data.map { it.toDomain() })
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun saveCreditCard(body: RequestBody): NetworkResult<CreditCard> {
		return try {
			val result = apiService.saveCreditCardAsync(body).await()
			NetworkResult.Success(result.data.toDomain())
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun deleteCreditCard(id: Int): NetworkResult<JsonElement> {
		return try {
			val result = apiService.deleteCreditCardAsync(id).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun getBankInfo(body: RequestBody): NetworkResult<JsonElement> {
		return try {
			val result = apiService.getBankInfoAsync(body).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}
}