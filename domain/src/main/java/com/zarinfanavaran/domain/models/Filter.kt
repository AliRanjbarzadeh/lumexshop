package com.zarinfanavaran.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
	val id: Int,
	val name: String,
	val type: String,
	val hasOption: Int,
	val options: List<Option>?,
	var _selected: Boolean = false
) : BaseObservable(), Parcelable {

	@IgnoredOnParcel
	var isSelected = _selected
		@Bindable get() = _selected
		set(value) {
			field = value
			_selected = value
			notifyPropertyChanged(BR.selected)
		}

	@Parcelize
	data class Option(
		val id: Int,
		val nameFa: String,
		val nameEn: String,
		var _selected: Boolean = false
	) : BaseObservable(), Parcelable {

		@IgnoredOnParcel
		var isSelected = _selected
			@Bindable get() = _selected
			set(value) {
				field = value
				_selected = value
				notifyPropertyChanged(BR.selected)
			}
	}

	override fun toString(): String {
		return "Filter(id=$id, name='$name', isSelected=$isSelected)"
	}
}
