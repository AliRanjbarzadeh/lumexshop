package com.zarinfanavaran.presentation

import com.zarinfanavaran.presentation.base.BaseViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Ali Ranjbarzadeh on 11/10/2022 AD.
 */

@HiltViewModel
class ShareViewModel @Inject constructor(
	private val dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {
}