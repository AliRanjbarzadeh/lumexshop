package com.zarinfanavaran.presentation.profile

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.domain.usecase.GetProfileUseCase
import com.zarinfanavaran.domain.usecase.ProfileLogoutUseCase
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider,
	private val getProfileUseCase: GetProfileUseCase,
	private val profileLogoutUseCase: ProfileLogoutUseCase,
) : BaseViewModel(dispatchers) {
	private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	private val _user: MutableLiveData<NetworkResult<User>> = MutableLiveData()
	private val _logout: MutableLiveData<NetworkResult<JsonElement>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
//		profile()
	}

	fun profile() {
		execute {
			_isLoading.postValue(true)
			val result = getProfileUseCase()
			_user.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun logout(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = profileLogoutUseCase(body)
			_logout.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun isLoading(): MutableLiveData<Boolean> = _isLoading

	fun getProfile(): MutableLiveData<NetworkResult<User>> = _user

	fun getLogout(): MutableLiveData<NetworkResult<JsonElement>> = _logout

}