package com.zarinfanavaran.presentation.profile.info

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.CreditCard
import com.zarinfanavaran.domain.models.Media
import com.zarinfanavaran.domain.usecase.*
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class EditInfoViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider,
	private val uploadFileUseCase: UploadFileUseCase,
	private val profileAvatarUseCase: ProfileAvatarUseCase,
	private val profileInfoUseCase: ProfileInfoUseCase,
	private val profileBornAtUseCase: ProfileBornAtUseCase,
	private val profileEmailUseCase: ProfileEmailUseCase,
	private val creditCardListUseCase: CreditCardListUseCase,
	private val creditCardAddUseCase: CreditCardAddUseCase,
	private val creditCardDeleteUseCase: CreditCardDeleteUseCase,
	private val creditCardBankInfoUseCase: CreditCardBankInfoUseCase,
) : BaseViewModel(dispatchers) {
	private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	private val _uploadFile: MutableLiveData<NetworkResult<Media>> = MutableLiveData()
	private val _avatar: MutableLiveData<NetworkResult<JsonElement>> = MutableLiveData()
	private val _info: MutableLiveData<NetworkResult<JsonElement>> = MutableLiveData()
	private val _bornAt: MutableLiveData<NetworkResult<JsonElement>> = MutableLiveData()
	private val _email: MutableLiveData<NetworkResult<JsonElement>> = MutableLiveData()
	private val _creditCards: MutableLiveData<NetworkResult<List<CreditCard>>> = MutableLiveData()
	private val _creditCardAdd: MutableLiveData<NetworkResult<CreditCard>> = MutableLiveData()
	private val _creditCardDelete: MutableLiveData<NetworkResult<JsonElement>> = MutableLiveData()
	private val _creditCardBankInfo: MutableLiveData<NetworkResult<JsonElement>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
	}

	fun uploadFile(multiPartBody: MultipartBody.Part) {
		execute {
			_isLoading.postValue(true)

			val result = uploadFileUseCase(multiPartBody)
			_uploadFile.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun saveAvatar(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = profileAvatarUseCase(body)
			_avatar.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun saveInfo(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = profileInfoUseCase(body)
			_info.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun saveBornAt(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = profileBornAtUseCase(body)
			_bornAt.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun saveEmail(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = profileEmailUseCase(body)
			_email.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun creditCards() {
		execute {
			_isLoading.postValue(true)
			val result = creditCardListUseCase()
			_creditCards.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun saveCreditCard(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = creditCardAddUseCase(body)
			_creditCardAdd.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun deleteCreditCard(id: Int) {
		execute {
			_isLoading.postValue(true)
			val result = creditCardDeleteUseCase(id)
			_creditCardDelete.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun bankInfo(body: RequestBody) {
		execute {
			_isLoading.postValue(true)
			val result = creditCardBankInfoUseCase(body)
			_creditCardBankInfo.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun isLoading(): MutableLiveData<Boolean> = _isLoading

	fun getUploadFile(): MutableLiveData<NetworkResult<Media>> = _uploadFile
	fun getAvatar(): MutableLiveData<NetworkResult<JsonElement>> = _avatar
	fun getInfo(): MutableLiveData<NetworkResult<JsonElement>> = _info
	fun getBornAt(): MutableLiveData<NetworkResult<JsonElement>> = _bornAt
	fun getEmail(): MutableLiveData<NetworkResult<JsonElement>> = _email
	fun getCreditCards(): MutableLiveData<NetworkResult<List<CreditCard>>> = _creditCards
	fun creditCardAdd(): MutableLiveData<NetworkResult<CreditCard>> = _creditCardAdd
	fun creditCardDelete(): MutableLiveData<NetworkResult<JsonElement>> = _creditCardDelete
	fun creditCardBankInfo(): MutableLiveData<NetworkResult<JsonElement>> = _creditCardBankInfo
}