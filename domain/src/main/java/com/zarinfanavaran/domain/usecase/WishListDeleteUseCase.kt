package com.zarinfanavaran.domain.usecase

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.repository.WishListRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishListDeleteUseCase @Inject constructor(private val repository: WishListRepository) {
	suspend operator fun invoke(productId: Int): NetworkResult<JsonElement> = repository.deleteFromWishList(productId)
}