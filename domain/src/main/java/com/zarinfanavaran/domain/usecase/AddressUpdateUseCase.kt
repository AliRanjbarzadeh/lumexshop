package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.repository.AddressRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressUpdateUseCase @Inject constructor(private val repository: AddressRepository) {
	suspend operator fun invoke(addressId: Int, body: RequestBody): NetworkResult<Address> = repository.updateAddress(addressId, body)
}