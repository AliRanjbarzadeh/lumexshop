package com.zarinfanavaran.data.api

import com.zarinfanavaran.data.models.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

	@GET("category")
	fun fetchCategoriesAsync(): Deferred<MyResponseRemote<List<CategoryRemote>, MetaRemoot>>

	@GET("category/{category}/info")
	fun fetchCategoryDetailAsync(
		@Path("category")
		categoryId: Int
	): Deferred<MyResponseRemote<CategoryDetailRemote, MetaRemoot>>

	@GET("products")
	fun fetchProductsAsync(
		@QueryMap
		params: Map<String, @JvmSuppressWildcards Any?>
	): Deferred<MyResponseRemote<List<ProductRemote>, MetaRemoot>>

	@GET("products/{id}")
	fun fetchProductDetailAsync(@Path("id") productId: Int): Deferred<MyResponseRemote<ProductDetailRemote, MetaRemoot>>
}