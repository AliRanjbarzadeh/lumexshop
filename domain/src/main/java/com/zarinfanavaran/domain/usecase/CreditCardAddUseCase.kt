package com.zarinfanavaran.domain.usecase

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.CreditCard
import com.zarinfanavaran.domain.repository.CreditCardRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditCardAddUseCase @Inject constructor(private val repository: CreditCardRepository) {
	suspend operator fun invoke(body: RequestBody): NetworkResult<CreditCard> = repository.saveCreditCard(body)
}