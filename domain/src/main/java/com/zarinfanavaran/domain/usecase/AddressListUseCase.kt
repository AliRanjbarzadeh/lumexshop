package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.repository.AddressRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressListUseCase @Inject constructor(private val repository: AddressRepository) {
	suspend operator fun invoke(): NetworkResult<List<Address>> = repository.getAddresses()
}