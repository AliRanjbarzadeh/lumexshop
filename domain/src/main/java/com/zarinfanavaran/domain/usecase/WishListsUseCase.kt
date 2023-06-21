package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.WishList
import com.zarinfanavaran.domain.repository.WishListRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishListsUseCase @Inject constructor(private val repository: WishListRepository) {
	suspend operator fun invoke(params: Map<String, Any?>): NetworkResult<MyResponse<List<WishList>, Meta>> = repository.getWishLists(params)
}