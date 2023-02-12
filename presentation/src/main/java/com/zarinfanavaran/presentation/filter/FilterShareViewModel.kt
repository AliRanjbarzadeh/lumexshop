package com.zarinfanavaran.presentation.filter

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.zarinfanavaran.domain.models.Filter
import com.zarinfanavaran.presentation.base.BaseDialogFragmentCallback
import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Ali Ranjbarzadeh on 2023/02/04.
 */
@HiltViewModel
class FilterShareViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {
	var _isLoading = MutableLiveData<Boolean>()
	var filters = listOf<Filter>()
	lateinit var baseDialogFragmentCallback: BaseDialogFragmentCallback
	var filterCount = ObservableField("1,300")

	fun isLoading(): MutableLiveData<Boolean> = _isLoading
}