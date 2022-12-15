package com.zarinfanavaran.shop.di.modules

import com.zarinfanavaran.data.repository.CategoryRemoteDataSource
import com.zarinfanavaran.data.repository.CategoryRepositoryImpl
import com.zarinfanavaran.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
	@Provides
	fun providesCategoryRepository(remoteDataSource: CategoryRemoteDataSource): CategoryRepository = CategoryRepositoryImpl(remoteDataSource)
}