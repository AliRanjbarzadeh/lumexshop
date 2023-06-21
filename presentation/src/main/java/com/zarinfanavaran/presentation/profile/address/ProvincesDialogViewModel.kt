package com.zarinfanavaran.presentation.profile.address

import androidx.lifecycle.MutableLiveData
import com.zarinfanavaran.domain.models.Province
import com.zarinfanavaran.domain.usecase.GetProvincesUseCase
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProvincesDialogViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider,
	private val getProvincesUseCase: GetProvincesUseCase,
) : BaseViewModel(dispatchers) {
	private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	private val _provinces: MutableLiveData<NetworkResult<List<Province>>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
		provinces()
	}

	fun provinces() {
		execute {
			_isLoading.postValue(true)
			val result = getProvincesUseCase()
			_provinces.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun isLoading(): MutableLiveData<Boolean> = _isLoading
	fun getProvinces(): MutableLiveData<NetworkResult<List<Province>>> = _provinces
}