package com.zarinfanavaran.presentation.login

import androidx.lifecycle.MutableLiveData
import com.zarinfanavaran.domain.models.LoginMobile
import com.zarinfanavaran.domain.usecase.AuthLoginUseCase
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LoginMobileViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider,
	private val authLoginUseCase: AuthLoginUseCase
) : BaseViewModel(dispatchers) {
	private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	private val _login: MutableLiveData<NetworkResult<LoginMobile>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
	}

	fun login(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = authLoginUseCase(body)
			_login.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun isLoading(): MutableLiveData<Boolean> = _isLoading

	fun getLogin(): MutableLiveData<NetworkResult<LoginMobile>> = _login

}