package com.zarinfanavaran.data.models

import com.google.gson.JsonElement

data class MyResponse <T>(
	val status: Int,
	val success: Boolean,
	val message: String,
	val `data`: T,
	val errors: JsonElement
)