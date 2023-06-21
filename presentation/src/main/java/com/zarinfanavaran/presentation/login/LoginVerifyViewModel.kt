package com.zarinfanavaran.presentation.login

import androidx.lifecycle.MutableLiveData
import com.zarinfanavaran.domain.models.LoginMobile
import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.domain.usecase.AuthResendUseCase
import com.zarinfanavaran.domain.usecase.AuthVerifyUseCase
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LoginVerifyViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider,
	private val authVerifyUseCase: AuthVerifyUseCase,
	private val authResendUseCase: AuthResendUseCase
) : BaseViewModel(dispatchers) {
	private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	private val _user: MutableLiveData<NetworkResult<User>> = MutableLiveData()
	private val _resend: MutableLiveData<NetworkResult<LoginMobile>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
	}

	fun verify(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = authVerifyUseCase(body)
			_user.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun resend(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = authResendUseCase(body)
			_resend.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun isLoading(): MutableLiveData<Boolean> = _isLoading

	fun getVerify(): MutableLiveData<NetworkResult<User>> = _user

	fun getResend(): MutableLiveData<NetworkResult<LoginMobile>> = _resend
}