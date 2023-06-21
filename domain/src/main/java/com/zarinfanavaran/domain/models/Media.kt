package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
	val media: MediaChild? = null,
	val main: MediaChild? = null,
	val icon: MediaChild? = null,
	val logo: MediaChild? = null,
	var avatar: MediaChild? = null,
) : Parcelable {
	@Parcelize
	data class MediaChild(
		val id: Int,
		val file: String
	) : Parcelable
}