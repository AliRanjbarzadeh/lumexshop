package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
@Parcelize
data class Category(
	val id: Int,
	val name: String,
	val level: Int,
	val media: Media?,
	val children: MutableList<Category>?
) : Parcelable {
	@IgnoredOnParcel
	var isHasMore = false
}
