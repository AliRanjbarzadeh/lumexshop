package com.zarinfanavaran.shop.di.modules

import android.app.Application
import android.content.res.Resources
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zarinfanavaran.data.exceptions.NetworkExceptionHandler
import com.zarinfanavaran.domain.extensions.loadFromSp
import com.zarinfanavaran.presentation.util.DispatchersProvider
import com.zarinfanavaran.presentation.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Ali Ranjbarzadeh on 9/29/2022 AD.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun providesResources(@ApplicationContext application: Application): Resources = application.resources

	@Provides
	@Singleton
	fun providesGson(): Gson = GsonBuilder().create()

	@Provides
	@Singleton
	fun providesOkHttpClient(baseHttpClient: BaseHttpClient): OkHttpClient = baseHttpClient.okHttpClient

	@Provides
	@Singleton
	fun providesRetrofit(baseRetrofit: BaseRetrofit): Retrofit = baseRetrofit.retrofit

	@Provides
	@Singleton
	fun providesDispatcher(dispatcherProvider: DispatchersProviderImpl): DispatchersProvider = dispatcherProvider.dispatcher

	@Provides
	@Singleton
	fun providesApiExceptionHandler(gson: Gson): NetworkExceptionHandler = NetworkExceptionHandler(gson)

	@Provides
	@Singleton
	fun providesGlide(@ApplicationContext application: Application): RequestManager = Glide.with(application)
}