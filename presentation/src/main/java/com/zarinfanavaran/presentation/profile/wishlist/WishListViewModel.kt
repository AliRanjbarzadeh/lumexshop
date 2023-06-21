package com.zarinfanavaran.presentation.profile.wishlist

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.WishList
import com.zarinfanavaran.domain.usecase.WishListDeleteUseCase
import com.zarinfanavaran.domain.usecase.WishListsUseCase
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider,
	private val wishListsUseCase: WishListsUseCase,
	private val wishListDeleteUseCase: WishListDeleteUseCase
) : BaseViewModel(dispatchers) {
	private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	private val _wishLists: MutableLiveData<NetworkResult<MyResponse<List<WishList>, Meta>>> = MutableLiveData()
	private val _deleteWishLis: MutableLiveData<NetworkResult<JsonElement>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
	}

	fun fetchWishLists(params: Map<String, Any?>) {
		execute {
			_isLoading.postValue(true)
			val result = wishListsUseCase(params)
			_wishLists.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun deleteWishList(productId: Int) {
		execute {
			_isLoading.postValue(true)
			val result = wishListDeleteUseCase(productId)
			_deleteWishLis.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}


	fun isLoading(): MutableLiveData<Boolean> = _isLoading
	fun getWishLists(): MutableLiveData<NetworkResult<MyResponse<List<WishList>, Meta>>> = _wishLists
	fun removeWishList(): MutableLiveData<NetworkResult<JsonElement>> = _deleteWishLis
}