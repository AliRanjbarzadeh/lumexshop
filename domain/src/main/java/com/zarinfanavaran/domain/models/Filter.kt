package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
	val id: Int,
	val name: String,
	val type: String,
	val hasOption: Int,
	val options: List<Option>?
) : Parcelable {

	@Parcelize
	data class Option(
		val id: Int,
		val nameFa: String,
		val nameEn: String
	) : Parcelable
}
