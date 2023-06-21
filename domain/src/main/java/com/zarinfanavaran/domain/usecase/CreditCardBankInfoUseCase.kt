package com.zarinfanavaran.domain.usecase

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.repository.CreditCardRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditCardBankInfoUseCase @Inject constructor(private val repository: CreditCardRepository) {
	suspend operator fun invoke(body: RequestBody): NetworkResult<JsonElement> = repository.getBankInfo(body)
}