package com.zarinfanavaran.shop.di.modules

import com.zarinfanavaran.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * Created by Ali Ranjbarzadeh on 9/29/2022 AD.
 */

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
	@Provides
	fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}