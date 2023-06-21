package com.zarinfanavaran.domain.repository

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.LoginMobile
import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody

interface UserRepository {
	suspend fun login(body: RequestBody): NetworkResult<LoginMobile>
	suspend fun verify(body: RequestBody): NetworkResult<User>
	suspend fun resend(body: RequestBody): NetworkResult<LoginMobile>
	suspend fun profile(): NetworkResult<User>
	suspend fun logout(body: RequestBody): NetworkResult<JsonElement>
	suspend fun saveAvatar(body: RequestBody): NetworkResult<JsonElement>
	suspend fun saveInfo(body: RequestBody): NetworkResult<JsonElement>
	suspend fun saveBornAt(body: RequestBody): NetworkResult<JsonElement>
	suspend fun saveEmail(body: RequestBody): NetworkResult<JsonElement>
}