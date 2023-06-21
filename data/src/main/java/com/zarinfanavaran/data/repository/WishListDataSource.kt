package com.zarinfanavaran.data.repository

import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.WishList
import com.zarinfanavaran.domain.util.NetworkResult

interface WishListDataSource {
	suspend fun getWishLists(params: Map<String, Any?>): NetworkResult<MyResponse<List<WishList>, Meta>>
	suspend fun addToWishList(productId: Int): NetworkResult<JsonElement>
	suspend fun deleteFromWishList(productId: Int): NetworkResult<JsonElement>
}