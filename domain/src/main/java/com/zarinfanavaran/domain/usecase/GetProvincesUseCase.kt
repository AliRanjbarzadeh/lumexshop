package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.Province
import com.zarinfanavaran.domain.repository.GlobalRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProvincesUseCase @Inject constructor(private val repository: GlobalRepository) {
	suspend operator fun invoke(): NetworkResult<List<Province>> = repository.getProvinces()
}