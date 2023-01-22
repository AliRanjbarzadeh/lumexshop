package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
	val main: MediaChild?,
	val icon: MediaChild?,
	val logo: MediaChild?,
) : Parcelable {
	@Parcelize
	data class MediaChild(
		val id: Int,
		val file: String
	) : Parcelable
}