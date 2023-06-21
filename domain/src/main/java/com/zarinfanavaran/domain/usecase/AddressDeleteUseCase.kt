package com.zarinfanavaran.domain.usecase

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.repository.AddressRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDeleteUseCase @Inject constructor(private val repository: AddressRepository) {
	suspend operator fun invoke(addressId: Int): NetworkResult<JsonElement> = repository.deleteAddress(addressId)
}