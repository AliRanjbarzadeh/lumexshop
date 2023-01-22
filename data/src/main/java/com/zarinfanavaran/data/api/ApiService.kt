package com.zarinfanavaran.data.api

import com.zarinfanavaran.data.models.CategoryDetailRemote
import com.zarinfanavaran.data.models.CategoryRemote
import com.zarinfanavaran.data.models.MyResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

	@GET("category")
	fun fetchCategoriesAsync(): Deferred<MyResponse<List<CategoryRemote>>>

	@GET("category/{category}/info")
	fun fetchCategoryDetailAsync(@Path("category") categoryId: Int): Deferred<MyResponse<CategoryDetailRemote>>
}