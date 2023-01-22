package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Color(
	val id: Int,
	val nameFa: String,
	val nameEn: String,
	val media: Media?
): Parcelable