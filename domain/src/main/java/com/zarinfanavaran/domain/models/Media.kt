package com.zarinfanavaran.domain.models

data class Media(
	val main: Main?,
	val icon: Icon?,
) {
	data class Main(
		val id: Int,
		val file: String
	)

	data class Icon(
		val id: Int,
		val file: String
	)
}