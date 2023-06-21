package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.LoginMobile

data class LoginMobileRemote(
	@SerializedName("mobile_number")
	val mobileNumber: String
) : ResponseObject<LoginMobile> {
	override fun toDomain(): LoginMobile = LoginMobile(mobileNumber)
}