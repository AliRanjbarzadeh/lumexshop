package com.zarinfanavaran.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zarinfanavaran.domain.BR
import kotlinx.parcelize.Parcelize

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */

@Parcelize
data class Receiver(
	var _firstName: String,
	var _lastName: String,
	var _mobile: String,
	var _personalCode: String,
) : BaseObservable(), Parcelable {

	var firstName: String = _firstName
		@Bindable get() = _firstName
		set(value) {
			field = value
			_firstName = value
			notifyPropertyChanged(BR.firstName)
			notifyPropertyChanged(BR.fullName)
		}

	var lastName: String = _lastName
		@Bindable get() = _lastName
		set(value) {
			field = value
			_lastName = value
			notifyPropertyChanged(BR.lastName)
			notifyPropertyChanged(BR.fullName)
		}

	val fullName: String
		@Bindable get() = "$_firstName $_lastName"

	var mobile: String = _mobile
		@Bindable get() = _mobile
		set(value) {
			field = value
			_mobile = value
			notifyPropertyChanged(BR.mobile)
		}

	var personalCode: String = _personalCode
		@Bindable get() = _personalCode
		set(value) {
			field = value
			_personalCode = value
			notifyPropertyChanged(BR.personalCode)
		}
}
