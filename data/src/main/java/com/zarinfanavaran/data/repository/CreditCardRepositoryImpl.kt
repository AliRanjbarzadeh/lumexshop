package com.zarinfanavaran.data.repository

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.CreditCard
import com.zarinfanavaran.domain.repository.CreditCardRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditCardRepositoryImpl @Inject constructor(private val dataSource: CreditCardDataSource) : CreditCardRepository {
	override suspend fun getCreditCards(): NetworkResult<List<CreditCard>> = dataSource.getCreditCards()
	override suspend fun saveCreditCard(body: RequestBody): NetworkResult<CreditCard> = dataSource.saveCreditCard(body)
	override suspend fun deleteCreditCard(id: Int): NetworkResult<JsonElement> = dataSource.deleteCreditCard(id)
	override suspend fun getBankInfo(body: RequestBody): NetworkResult<JsonElement> = dataSource.getBankInfo(body)
}