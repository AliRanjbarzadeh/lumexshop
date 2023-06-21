package com.zarinfanavaran.data.repository

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.zarinfanavaran.data.api.ApiService
import com.zarinfanavaran.data.exceptions.NetworkExceptionHandler
import com.zarinfanavaran.domain.models.LoginMobile
import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
	private val apiService: ApiService,
	private val apiExceptionHandler: NetworkExceptionHandler,
	private val gson: Gson
) : UserDataSource {
	override suspend fun login(body: RequestBody): NetworkResult<LoginMobile> {
		return try {
			val result = apiService.authLoginAsync(body).await()
			NetworkResult.Success(result.data.toDomain())
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun verify(body: RequestBody): NetworkResult<User> {
		return try {
			val result = apiService.authVerifyAsync(body).await()
			NetworkResult.Success(result.data.toDomain())
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun resend(body: RequestBody): NetworkResult<LoginMobile> {
		return try {
			val result = apiService.authResendAsync(body).await()
			NetworkResult.Success(result.data.toDomain())
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun profile(): NetworkResult<User> {
		return try {
			val result = apiService.getProfileAsync().await()
			NetworkResult.Success(result.data.toDomain())
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun logout(body: RequestBody): NetworkResult<JsonElement> {
		return try {
			val result = apiService.logoutAsync(body).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun saveAvatar(body: RequestBody): NetworkResult<JsonElement> {
		return try {
			val result = apiService.saveAvatarAsync(body).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun saveInfo(body: RequestBody): NetworkResult<JsonElement> {
		return try {
			val result = apiService.saveInfoAsync(body).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun saveBornAt(body: RequestBody): NetworkResult<JsonElement> {
		return try {
			val result = apiService.saveBornAtAsync(body).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}

	override suspend fun saveEmail(body: RequestBody): NetworkResult<JsonElement> {
		return try {
			val result = apiService.saveEmailAsync(body).await()
			NetworkResult.Success(result.data)
		} catch (e: Exception) {
			NetworkResult.Error(apiExceptionHandler.traceErrorException(e))
		}
	}
}