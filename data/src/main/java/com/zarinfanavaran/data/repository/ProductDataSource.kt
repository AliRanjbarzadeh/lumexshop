package com.zarinfanavaran.data.repository

import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.util.NetworkResult

interface ProductDataSource {
	suspend fun getProducts(params: Map<String, Any?>): NetworkResult<MyResponse<List<Product>, Meta>>
}