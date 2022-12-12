package com.zarinfanavaran.domain.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zarinfanavaran.domain.BR

/**
 * Created by Ali Ranjbarzadeh on 11/12/2022 AD.
 */
data class User(
	val id: Int = 0,
	var _firstName: String = "",
	var _lastName: String = "",
	var _personalCode: String = "",
	var _email: String = "",
	var _birthDate: String = "-------",
	var _mobile: String = "",
	var _emailConfirmed: Boolean = false,
) : BaseObservable() {

	var firstName = _firstName
		set(value) {
			field = value
			_firstName = value
			notifyPropertyChanged(BR.firstName)
			notifyPropertyChanged(BR.fullName)
		}
		@Bindable get() = _firstName

	var lastName = _lastName
		set(value) {
			field = value
			_lastName = value
			notifyPropertyChanged(BR.lastName)
			notifyPropertyChanged(BR.fullName)
		}
		@Bindable get() = _lastName

	val fullName: String
		@Bindable get() = "$_firstName $_lastName"

	var personalCode = _personalCode
		set(value) {
			field = value
			_personalCode = value
			notifyPropertyChanged(BR.personalCode)
		}
		@Bindable get() = _personalCode

	var email = _email
		set(value) {
			field = value
			_email = value
			notifyPropertyChanged(BR.email)
		}
		@Bindable get() = _email

	var birthDate = _birthDate
		set(value) {
			field = value
			_birthDate = value
			notifyPropertyChanged(BR.birthDate)
		}
		@Bindable get() = _birthDate

	var mobile = _mobile
		set(value) {
			field = value
			_mobile = value
			notifyPropertyChanged(BR.mobile)
			notifyPropertyChanged(BR.mobileFormat)
		}
		@Bindable get() = _mobile

	val mobileFormat: String
		@Bindable get() = "($_mobile)"

	var emailConfirmed = _emailConfirmed
		set(value) {
			field = value
			_emailConfirmed = value
			notifyPropertyChanged(BR.emailConfirmed)
		}
		@Bindable get() = _emailConfirmed

	override fun toString(): String {
		return "User(id=$id, _firstName='$_firstName', _lastName='$_lastName', _personalCode='$_personalCode', _email='$_email', _birthDate='$_birthDate', _mobile='$_mobile')"
	}


}