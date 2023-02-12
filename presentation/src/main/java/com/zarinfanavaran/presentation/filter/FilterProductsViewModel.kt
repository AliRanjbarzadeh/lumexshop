package com.zarinfanavaran.presentation.filter

import androidx.lifecycle.MutableLiveData
import com.zarinfanavaran.domain.models.Filter
import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.usecase.GetProductsUseCase
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterProductsViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider,
	private val getProductsUseCase: GetProductsUseCase
) : BaseViewModel(dispatchers) {
	private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	private val _data: MutableLiveData<NetworkResult<MyResponse<List<Product>, Meta>>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
	}

	fun fetchProducts(params: Map<String, Any?>) {
		execute {
			_isLoading.postValue(true)
			val result = getProductsUseCase(params)
			_data.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun getProducts(): MutableLiveData<NetworkResult<MyResponse<List<Product>, Meta>>> = _data

	fun isLoading(): MutableLiveData<Boolean> = _isLoading
}