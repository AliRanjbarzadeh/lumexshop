package com.zarinfanavaran.data.repository

import com.zarinfanavaran.domain.models.City
import com.zarinfanavaran.domain.models.Media
import com.zarinfanavaran.domain.models.Province
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.MultipartBody

interface GlobalDataSource {
	suspend fun uploadFile(file: MultipartBody.Part): NetworkResult<Media>
	suspend fun getProvinces(): NetworkResult<List<Province>>
	suspend fun getCities(provinceId: Int): NetworkResult<List<City>>
}