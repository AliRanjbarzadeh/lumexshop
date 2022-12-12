package com.zarinfanavaran.domain.models

import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zarinfanavaran.domain.BR

data class ProductTool(
	@DrawableRes
	var _image: Int,
	val type: String,
	var isChecked: Boolean = false
) : BaseObservable() {

	var image = _image
		set(value) {
			field = value
			_image = value
			notifyPropertyChanged(BR.image)
		}
		@Bindable get() = _image
}