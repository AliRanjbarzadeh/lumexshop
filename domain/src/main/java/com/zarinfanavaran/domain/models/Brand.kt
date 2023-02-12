package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
@Parcelize
data class Brand(
	val id: Int,
	val nameFa: String,
	val nameEn: String,
	val description: String,
	val media: Media?,
	var _selected: Boolean = false
) : Parcelable {

	@IgnoredOnParcel
	var isSelected = _selected
		get() = _selected
		set(value) {
			field = value
			_selected = value
		}
}
