package com.zarinfanavaran.data.repository

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody

interface AddressDataSource {
	suspend fun getAddresses(): NetworkResult<List<Address>>
	suspend fun getAddress(addressId: Int): NetworkResult<Address>
	suspend fun addAddress(body: RequestBody): NetworkResult<Address>
	suspend fun updateAddress(addressId: Int, body: RequestBody): NetworkResult<Address>
	suspend fun deleteAddress(addressId: Int): NetworkResult<JsonElement>
}