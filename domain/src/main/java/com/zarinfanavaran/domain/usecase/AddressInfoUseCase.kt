package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.repository.AddressRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressInfoUseCase @Inject constructor(private val repository: AddressRepository) {
	suspend operator fun invoke(addressId: Int): NetworkResult<Address> = repository.getAddress(addressId)
}