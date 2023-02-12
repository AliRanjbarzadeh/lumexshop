package com.zarinfanavaran.domain.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SortOption(
	val key: String,
	val name: String,
	private var _selected: Boolean = false,
) : Parcelable {

	@IgnoredOnParcel
	var isSelected = _selected
		get() = _selected
		set(value) {
			field = value
			_selected = value
		}
}