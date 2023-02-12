package com.zarinfanavaran.presentation.base

/**
 * Created by Ali Ranjbarzadeh on 2023/02/07.
 */
interface RetryCallback {
	fun onRetry() {}

	fun onCancel() {}
}