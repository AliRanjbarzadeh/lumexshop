package com.zarinfanavaran.data.repository

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.repository.AddressRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepositoryImpl @Inject constructor(private val dataSource: AddressDataSource) : AddressRepository {
	override suspend fun getAddresses(): NetworkResult<List<Address>> = dataSource.getAddresses()
	override suspend fun getAddress(addressId: Int): NetworkResult<Address> = dataSource.getAddress(addressId)
	override suspend fun addAddress(body: RequestBody): NetworkResult<Address> = dataSource.addAddress(body)
	override suspend fun updateAddress(addressId: Int, body: RequestBody): NetworkResult<Address> = dataSource.updateAddress(addressId, body)
	override suspend fun deleteAddress(addressId: Int): NetworkResult<JsonElement> = dataSource.deleteAddress(addressId)
}