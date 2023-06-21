package com.zarinfanavaran.data.repository

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.zarinfanavaran.data.api.ApiService
import com.zarinfanavaran.data.exceptions.NetworkExceptionHandler
import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRemoteDataSource @Inject constructor(
	private val apiService: ApiService,
	private val apiExceptionHandler: NetworkExceptionHandler,
	private val gson: Gson
) : AddressDataSource {
	override suspend fun getAddresses(): NetworkResult<List<Address>> {
		return try {
			val result = apiService.fetchAddressesAsync().await()
			NetworkResult.Success(result.data.map { it.toDomain() })
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun getAddress(addressId: Int): NetworkResult<Address> {
		return try {
			val result = apiService.fetchAddressAsync(addressId).await()
			NetworkResult.Success(result.data.toDomain())
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun addAddress(body: RequestBody): NetworkResult<Address> {
		return try {
			val result = apiService.addAddressAsync(body).await()
			NetworkResult.Success(result.data.toDomain())
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun updateAddress(addressId: Int, body: RequestBody): NetworkResult<Address> {
		return try {
			val result = apiService.updateAddressAsync(addressId, body).await()
			NetworkResult.Success(result.data.toDomain())
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun deleteAddress(addressId: Int): NetworkResult<JsonElement> {
		return try {
			val result = apiService.deleteAddressAsync(addressId).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}
}