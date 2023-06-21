package com.zarinfanavaran.data.models

import com.google.gson.JsonElement
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.MyResponse

data class MyResponseRemote<T, M>(
	val status: Int?,
	val success: Boolean?,
	val message: String?,
	val `data`: T,
	val errors: JsonElement?,
	val meta: M
) : ResponseObject<MyResponse<T, M>> {
	override fun toDomain(): MyResponse<T, M> = MyResponse(status, success, message, `data`, errors, meta)
}