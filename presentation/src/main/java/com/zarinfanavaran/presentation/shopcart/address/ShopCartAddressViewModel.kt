package com.zarinfanavaran.presentation.shopcart.address

import androidx.databinding.ObservableBoolean
import com.zarinfanavaran.domain.models.Receiver
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Ali Ranjbarzadeh on 11/7/2022 AD.
 */

@HiltViewModel
class ShopCartAddressViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {
	val isIAmReceiver = ObservableBoolean(false)

	var receiver = Receiver("", "", "", "")

	fun clearReceiver(){
		receiver.apply {
			firstName = ""
			lastName = ""
			mobile = ""
			personalCode = ""
		}
	}
}