package com.zarinfanavaran.domain.models

/**
 * Created by Ali Ranjbarzadeh on 2023/01/30.
 */
data class MyResponse<T, M>(
	val `data`: T,
	val meta: M
)
