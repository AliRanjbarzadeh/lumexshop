package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Province(
	val id: Int,
	val name: String
) : Parcelable
