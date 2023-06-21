package com.zarinfanavaran.domain.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
	val id: Int = 0,
	private var _lat: Double = 0.0,
	private var _lng: Double = 0.0,
	var provinceId: Int = 0,
	var cityId: Int = 0,
	private var _address: String = "",
	private var _postalCode: String = "",
	private var _plaque: String = "",
	private var _mobile: String = "",
	private var _province: String = "",
	private var _city: String = "",
) : BaseObservable(), Parcelable {

	@IgnoredOnParcel
	var lat = _lat
		@Bindable get() {
			return _lat;
		}
		set(value) {
			field = value
			_lat = value
			notifyPropertyChanged(BR.lat)
		}

	@IgnoredOnParcel
	var lng = _lng
		@Bindable get() {
			return _lng;
		}
		set(value) {
			field = value
			_lng = value
			notifyPropertyChanged(BR.lng)
		}

	@IgnoredOnParcel
	var address = _address
		@Bindable get() = _address
		set(value) {
			field = value
			_address = value
			notifyPropertyChanged(BR.address)
		}

	@IgnoredOnParcel
	var province = _province
		@Bindable get() = _province
		set(value) {
			field = value
			_province = value
			notifyPropertyChanged(BR.province)
		}

	@IgnoredOnParcel
	var city = _city
		@Bindable get() = _city
		set(value) {
			field = value
			_city = value
			notifyPropertyChanged(BR.city)
		}

	@IgnoredOnParcel
	var postalCode = _postalCode
		@Bindable get() = _postalCode
		set(value) {
			field = value
			_postalCode = value
			notifyPropertyChanged(BR.postalCode)
		}

	@IgnoredOnParcel
	var plaque = _plaque
		@Bindable get() = _plaque
		set(value) {
			field = value
			_plaque = value
			notifyPropertyChanged(BR.plaque)
		}

	@IgnoredOnParcel
	var mobile = _mobile
		@Bindable get() = _mobile
		set(value) {
			field = value
			_mobile = value
			notifyPropertyChanged(BR.mobile)
		}

	override fun toString(): String {
		return "Address(lat=$lat, lng=$lng, address='$address', province='$province', city='$city', postalCode='$postalCode', plaque='$plaque', mobile='$mobile')"
	}


}
