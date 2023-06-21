package com.zarinfanavaran.domain.usecase

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.CreditCard
import com.zarinfanavaran.domain.repository.CreditCardRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditCardListUseCase @Inject constructor(private val repository: CreditCardRepository) {
	suspend operator fun invoke(): NetworkResult<List<CreditCard>> = repository.getCreditCards()
}