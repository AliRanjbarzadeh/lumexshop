package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.repository.AddressRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressAddUseCase @Inject constructor(private val repository: AddressRepository) {
	suspend operator fun invoke(body: RequestBody): NetworkResult<Address> = repository.addAddress(body)
}