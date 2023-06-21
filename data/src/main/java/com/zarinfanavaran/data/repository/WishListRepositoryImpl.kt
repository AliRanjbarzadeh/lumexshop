package com.zarinfanavaran.data.repository

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.WishList
import com.zarinfanavaran.domain.repository.WishListRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishListRepositoryImpl @Inject constructor(private val dataSource: WishListDataSource) : WishListRepository {
	override suspend fun getWishLists(params: Map<String, Any?>): NetworkResult<MyResponse<List<WishList>, Meta>> = dataSource.getWishLists(params)
	override suspend fun addToWishList(productId: Int): NetworkResult<JsonElement> = dataSource.addToWishList(productId)
	override suspend fun deleteFromWishList(productId: Int): NetworkResult<JsonElement> = dataSource.deleteFromWishList(productId)
}