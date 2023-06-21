package com.zarinfanavaran.domain.repository

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.CreditCard
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody

interface CreditCardRepository {
	suspend fun getCreditCards(): NetworkResult<List<CreditCard>>
	suspend fun saveCreditCard(body: RequestBody): NetworkResult<CreditCard>
	suspend fun deleteCreditCard(id: Int): NetworkResult<JsonElement>
	suspend fun getBankInfo(body: RequestBody): NetworkResult<JsonElement>
}