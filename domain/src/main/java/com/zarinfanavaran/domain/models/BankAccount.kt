package com.zarinfanavaran.domain.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zarinfanavaran.domain.BR
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
data class BankAccount(
	val id: Int = 0,
	val title: String,
	var _isConfirmed: Boolean,
	var _shabaNumber: String,
) : BaseObservable() {

	var isConfirmed = _isConfirmed
		set(value) {
			field = value
			_isConfirmed = value
			notifyPropertyChanged(BR.confirmed)
		}
		@Bindable get() = _isConfirmed

	var shabaNumber = _shabaNumber
		set(value) {
			field = value
			_shabaNumber = value
			notifyPropertyChanged(BR.shabaNumber)
			notifyPropertyChanged(BR.shabaFormat)
		}
		@Bindable get() = _shabaNumber

	val shabaFormat: String
		@Bindable get() {
			val stringBuilder = StringBuilder(_shabaNumber)
				.insert(2, "-")
				.insert(7, "-")
				.insert(12, "-")
				.insert(17, "-")
				.insert(22, "-")
				.insert(27, "-")

			return "IR" + stringBuilder.toString()
		}

	val shabaParted: Array<String> = shabaFormat.replace("IR", "").split("-").toTypedArray()

	override fun toString(): String {
		return "BankAccount(id=$id, title='$title', _isConfirmed=$_isConfirmed, _shabaNumber=$_shabaNumber)"
	}
}
