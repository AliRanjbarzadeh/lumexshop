package com.zarinfanavaran.data.repository

import com.zarinfanavaran.domain.models.City
import com.zarinfanavaran.domain.models.Media
import com.zarinfanavaran.domain.models.Province
import com.zarinfanavaran.domain.repository.GlobalRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalRepositoryImpl @Inject constructor(private val dataSource: GlobalDataSource) : GlobalRepository {
	override suspend fun uploadFile(file: MultipartBody.Part): NetworkResult<Media> = dataSource.uploadFile(file)
	override suspend fun getProvinces(): NetworkResult<List<Province>> = dataSource.getProvinces()
	override suspend fun getCities(provinceId: Int): NetworkResult<List<City>> = dataSource.getCities(provinceId)
}