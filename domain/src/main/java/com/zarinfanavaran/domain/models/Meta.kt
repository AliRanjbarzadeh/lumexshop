package com.zarinfanavaran.domain.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Ali Ranjbarzadeh on 2023/01/29.
 */
data class Meta(
	val currentPage: Int,
	val from: Int,
	val lastPage: Int,
	val perPage: Int,
	val to: Int,
	val total: Int
)
