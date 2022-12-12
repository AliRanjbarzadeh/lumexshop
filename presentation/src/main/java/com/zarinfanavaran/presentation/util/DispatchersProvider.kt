package com.zarinfanavaran.presentation.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ali Ranjbarzadeh on 9/29/2022 AD.
 */
interface DispatchersProvider {
	fun getIO(): CoroutineDispatcher
	fun getMain(): CoroutineDispatcher
	fun getDefault(): CoroutineDispatcher
}