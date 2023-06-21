package com.zarinfanavaran.domain.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zarinfanavaran.domain.BR

/**
 * Created by Ali Ranjbarzadeh on 11/12/2022 AD.
 */
data class User(
	val id: Int = 0,
	private var _firstName: String = "",
	private var _lastName: String = "",
	private var _fullName: String = "",
	private var _mobileNumber: String = "",
	private var _nationalCode: String = "",
	private var _email: String = "",
	private var _walletAmount: Int = 0,
	private var _pointAmount: Float = 0f,
	private var _walletAmountPrettified: String = "",
	private var _pointAmountPrettified: String = "",
	var status: String = "",
	var statusInfo: StatusInfo? = null,
	private var _gender: String = "",
	private var _genderInfo: GenderInfo? = null,
	private var _bornAt: String = "",
	private var _jalaliBornAt: String = "",
	var accessToken: String = "",
	var _emailVerifiedAt: String = "",
	val jalaliEmailVerifiedAt: String = "",
	val questionsWaitingToAnswerCount: Int = 0,
	val productsWaitingToCommentCount: Int = 0,
	val inProgressOrdersCount: Int = 0,
	val sentProgressOrdersCount: Int = 0,
	val returnedOrdersCount: Int = 0,
	val expiredOrdersCount: Int = 0,
	val unreadNotificationsCount: Int = 0,
	var media: Media? = null,
) : BaseObservable() {
	data class StatusInfo(
		val name: String = "",
		val color: String = "",
	) {
		override fun toString(): String {
			return "StatusInfo(name='$name', color='$color')"
		}
	}

	data class GenderInfo(
		val name: String = "",
	) {
		override fun toString(): String {
			return "GenderInfo(name='$name')"
		}
	}

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

	var fullName = _fullName
		set(value) {
			field = value
			_fullName = value
			notifyPropertyChanged(BR.fullName)
		}
		@Bindable get() = _fullName

	var mobileNumber = _mobileNumber
		set(value) {
			field = value
			_mobileNumber = value
			notifyPropertyChanged(BR.mobileNumber)
		}
		@Bindable get() = _mobileNumber

	var nationalCode = _nationalCode
		set(value) {
			field = value
			_nationalCode = value
			notifyPropertyChanged(BR.nationalCode)
		}
		@Bindable get() = _nationalCode

	var email = _email
		set(value) {
			field = value
			_email = value
			notifyPropertyChanged(BR.email)
		}
		@Bindable get() = _email

	var emailVerifiedAt = _emailVerifiedAt
		set(value) {
			field = value
			_emailVerifiedAt = value
			notifyPropertyChanged(BR.emailConfirmed)
		}

	var walletAmount = _walletAmount
		set(value) {
			field = value
			_walletAmount = value
			notifyPropertyChanged(BR.walletAmount)
		}
		@Bindable get() = _walletAmount

	var pointAmount = _pointAmount
		set(value) {
			field = value
			_pointAmount = value
			notifyPropertyChanged(BR.pointAmount)
		}
		@Bindable get() = _pointAmount

	var walletAmountPrettified = _walletAmountPrettified
		set(value) {
			field = value
			_walletAmountPrettified = value
			notifyPropertyChanged(BR.walletAmountPrettified)
		}
		@Bindable get() = _walletAmountPrettified

	var pointAmountPrettified = _pointAmountPrettified
		set(value) {
			field = value
			_pointAmountPrettified = value
			notifyPropertyChanged(BR.pointAmountPrettified)
		}
		@Bindable get() = _pointAmountPrettified

	var gender = _gender
		set(value) {
			field = value
			_gender = value
			notifyPropertyChanged(BR.gender)
		}
		@Bindable get() = _gender

	var genderInfo = _genderInfo
		set(value) {
			field = value
			_genderInfo = value
			notifyPropertyChanged(BR.genderInfo)
		}
		@Bindable get() = _genderInfo

	var bornAt = _bornAt
		set(value) {
			field = value
			_bornAt = value
			notifyPropertyChanged(BR.bornAt)
		}
		@Bindable get() = _bornAt

	var jalaliBornAt = _jalaliBornAt
		set(value) {
			field = value
			_jalaliBornAt = value
			notifyPropertyChanged(BR.jalaliBornAt)
		}
		@Bindable get() = _jalaliBornAt

	@Bindable
	fun isEmailConfirmed() = emailVerifiedAt.isNotEmpty()

	override fun toString(): String {
		return "User(id=$id, _firstName='$_firstName', _lastName='$_lastName', _fullName='$_fullName', _mobileNumber='$_mobileNumber', _nationalCode='$_nationalCode', _email='$_email', _walletAmount=$_walletAmount, _pointAmount=$_pointAmount, _walletAmountPrettified='$_walletAmountPrettified', _pointAmountPrettified='$_pointAmountPrettified', status='$status', statusInfo=$statusInfo, _gender='$_gender', _genderInfo=$_genderInfo, _bornAt='$_bornAt', _jalaliBornAt='$_jalaliBornAt', accessToken='$accessToken', emailVerifiedAt='$emailVerifiedAt', jalaliEmailVerifiedAt='$jalaliEmailVerifiedAt', media=$media)"
	}
}