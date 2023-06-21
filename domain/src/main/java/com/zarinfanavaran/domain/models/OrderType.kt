package com.zarinfanavaran.domain.models

import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zarinfanavaran.domain.BR

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class OrderType(
	val title: String,
	@DrawableRes val image: Int,
	var _badgeCount: Int = 0
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
