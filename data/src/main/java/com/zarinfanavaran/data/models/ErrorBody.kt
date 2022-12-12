package com.zarinfanavaran.data.models

import android.annotation.SuppressLint
import android.os.Parcelable
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.ErrorMessage
import com.zarinfanavaran.domain.util.HttpErrors
import kotlinx.parcelize.Parcelize

/**
 * Created by Ali Ranjbarzadeh on 9/29/2022 AD.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ErrorBody(
	val message: String?,
	val status: Int?,
) : Parcelable, ResponseObject<ErrorMessage> {
	override fun toDomain(): ErrorMessage {
		return ErrorMessage(
			message = message,
			status = when (status) {
				401 -> HttpErrors.Unauthorized
				403 -> HttpErrors.Forbidden
				400 -> HttpErrors.BadRequest
				500 -> HttpErrors.ServerError
				409 -> HttpErrors.Conflict
				else -> HttpErrors.NotDefined
			}
		)
	}
}