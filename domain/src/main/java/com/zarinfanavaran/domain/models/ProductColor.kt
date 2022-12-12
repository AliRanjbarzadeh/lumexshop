package com.zarinfanavaran.domain.models

import android.graphics.Color
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zarinfanavaran.domain.BR

data class ProductColor(
	val _colorValue: String,
	var _isChecked: Boolean = false
) : BaseObservable() {

	val colorValue = Color.parseColor(_colorValue)

	var isChecked = _isChecked
		set(value) {
			field = value
			_isChecked = value
			notifyPropertyChanged(BR.checked)
		}
		@Bindable get() = _isChecked
}