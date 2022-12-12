package com.zarinfanavaran.domain.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zarinfanavaran.domain.BR

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class ProfileMenuItem(
	val title: String,
	@DrawableRes val icon: Int,
	@ColorRes val color: Int,
	val type: String,
	var _badgeCount: Int = 2
) : BaseObservable() {

	var badgeCount = _badgeCount
		set(value) {
			field = value
			_badgeCount = value
			notifyPropertyChanged(BR.badgeCount)
			notifyPropertyChanged(BR.badgeCountString)
		}
		@Bindable get() = _badgeCount

	val badgeCountString: String
		@Bindable get() = _badgeCount.toString()
}
