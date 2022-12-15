package com.zarinfanavaran.presentation.categories

import android.util.Log
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
	private val _categories: MutableLiveData<NetworkResult<List<Category>>> = MutableLiveData()

	init {
		fetchCategories()
	}

	fun fetchCategories() {
		execute {
			_categories.postValue(NetworkResult.Loading(true))
			val result = getCategoriesUseCase()
			when (result) {
				is NetworkResult.Success -> _categories.postValue(result)

				is NetworkResult.Error -> _categories.postValue(result)

				else -> {}
			}
		}
	}

	fun getCategories(): MutableLiveData<NetworkResult<List<Category>>> = _categories
}