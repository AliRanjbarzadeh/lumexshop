package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.City
import com.zarinfanavaran.domain.repository.GlobalRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCitiesUseCase @Inject constructor(private val repository: GlobalRepository) {
	suspend operator fun invoke(provinceId: Int): NetworkResult<List<City>> = repository.getCities(provinceId)
}