package com.zarinfanavaran.presentation.categories.detail

import androidx.lifecycle.MutableLiveData
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.models.CategoryDetail
import com.zarinfanavaran.domain.usecase.GetCategoryDetailUseCase
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider, private val getCategoryDetailUseCase: GetCategoryDetailUseCase
): BaseViewModel(dispatchers) {
	private val _isLoading : MutableLiveData<Boolean> = MutableLiveData()
	private val _categoryDetail: MutableLiveData<NetworkResult<CategoryDetail>> = MutableLiveData()

	init {
		_isLoading.postValue(false)
	}

	fun fetchDetail(categoryId: Int) {
		execute {
			_isLoading.postValue(true)
			val result = getCategoryDetailUseCase(categoryId)
			_categoryDetail.postValue(result)
			if ((result is NetworkResult.Success) || (result is NetworkResult.Error)) {
				_isLoading.postValue(false)
			}
		}
	}

	fun getCategoryDetail(): MutableLiveData<NetworkResult<CategoryDetail>> = _categoryDetail
	fun isLoading(): MutableLiveData<Boolean> = _isLoading
}