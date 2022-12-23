package com.zarinfanavaran.domain.models

data class Media(
	val main: MediaChild?,
	val icon: MediaChild?,
	val logo: MediaChild?,
) {
	data class MediaChild(
		val id: Int,
		val file: String
	)
}