package com.zarinfanavaran.data.repository

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.LoginMobile
import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.domain.repository.UserRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val dataSource: UserDataSource) : UserRepository {
	override suspend fun login(body: RequestBody): NetworkResult<LoginMobile> = dataSource.login(body)
	override suspend fun verify(body: RequestBody): NetworkResult<User> = dataSource.verify(body)
	override suspend fun resend(body: RequestBody): NetworkResult<LoginMobile> = dataSource.resend(body)
	override suspend fun profile(): NetworkResult<User> = dataSource.profile()
	override suspend fun logout(body: RequestBody): NetworkResult<JsonElement> = dataSource.logout(body)
	override suspend fun saveAvatar(body: RequestBody): NetworkResult<JsonElement> = dataSource.saveAvatar(body)
	override suspend fun saveInfo(body: RequestBody): NetworkResult<JsonElement> = dataSource.saveInfo(body)
	override suspend fun saveBornAt(body: RequestBody): NetworkResult<JsonElement> = dataSource.saveBornAt(body)
	override suspend fun saveEmail(body: RequestBody): NetworkResult<JsonElement> = dataSource.saveEmail(body)
}