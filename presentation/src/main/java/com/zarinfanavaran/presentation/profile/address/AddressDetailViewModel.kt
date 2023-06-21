package com.zarinfanavaran.presentation.profile.address

import androidx.lifecycle.MutableLiveData
import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.usecase.AddressAddUseCase
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddressDetailViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider,
	private val addressAddUseCase: AddressAddUseCase,
) : BaseViewModel(dispatchers) {
	private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	private val _addressAdd: MutableLiveData<NetworkResult<Address>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
	}

	fun saveAddress(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = addressAddUseCase(body)
			_addressAdd.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun isLoading(): MutableLiveData<Boolean> = _isLoading
	fun getAddress(): MutableLiveData<NetworkResult<Address>> = _addressAdd
}