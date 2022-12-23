package com.zarinfanavaran.presentation.categories.detail

import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider
): BaseViewModel(dispatchers) {
}