package com.zarinfanavaran.presentation.categories

import androidx.lifecycle.MutableLiveData
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.usecase.GetCategoriesUseCase
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider, private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel(dispatchers) {
	private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	private val _categories: MutableLiveData<NetworkResult<List<Category>>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
	}

	fun fetchCategories() {
		execute {
			_isLoading.postValue(true)
			val result = getCategoriesUseCase()
			_categories.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun getCategories(): MutableLiveData<NetworkResult<List<Category>>> = _categories
	fun isLoading(): MutableLiveData<Boolean> = _isLoading
}