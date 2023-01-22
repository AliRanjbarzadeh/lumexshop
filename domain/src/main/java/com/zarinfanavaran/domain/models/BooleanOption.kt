package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BooleanOption(
	val key: String,
	val name: String
) : Parcelable