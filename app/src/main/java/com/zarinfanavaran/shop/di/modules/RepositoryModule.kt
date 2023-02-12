package com.zarinfanavaran.shop.di.modules

import com.zarinfanavaran.data.repository.*
import com.zarinfanavaran.domain.repository.CategoryRepository
import com.zarinfanavaran.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
	@Provides
	fun providesCategoryRepository(remoteDataSource: CategoryRemoteDataSource): CategoryRepository = CategoryRepositoryImpl(remoteDataSource)

	@Provides
	fun providesProductRepository(remoteDataSource: ProductRemoteDataSource): ProductRepository = ProductRepositoryImpl(remoteDataSource)
}