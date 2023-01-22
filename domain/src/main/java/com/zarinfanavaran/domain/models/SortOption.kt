package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SortOption(
	val key: String,
	val name: String
) : Parcelable