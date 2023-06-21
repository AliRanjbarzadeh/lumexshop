package com.zarinfanavaran.domain.models

import com.google.gson.JsonElement

/**
 * Created by Ali Ranjbarzadeh on 2023/01/30.
 */
data class MyResponse<T, M>(
	val status: Int?,
	val success: Boolean?,
	val message: String?,
	val `data`: T,
	val errors: JsonElement?,
	val meta: M
)
