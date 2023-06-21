package com.zarinfanavaran.domain.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class CreditCard(
	val id: Int,
	var title: String = "شماره شبا",
	var isConfirmed: Boolean = false,
	var _shabaNumber: String = "25-0120-0200-0000-9281-9131-29",
) : BaseObservable() {
	val shabaFormat: String
		@Bindable get() {
			return "IR" + _shabaNumber
		}

	val shabaParted: Array<String> = shabaFormat.replace("IR", "").split("-").toTypedArray()
}
