package com.zarinfanavaran.shop.di.modules

import com.zarinfanavaran.data.repository.*
import com.zarinfanavaran.domain.repository.*
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

	@Provides
	fun providesUserRepository(remoteDataSource: UserRemoteDataSource): UserRepository = UserRepositoryImpl(remoteDataSource)

	@Provides
	fun providesGlobalRepository(remoteDataSource: GlobalRemoteDataSource): GlobalRepository = GlobalRepositoryImpl(remoteDataSource)

	@Provides
	fun providesAddressRepository(remoteDataSource: AddressRemoteDataSource): AddressRepository = AddressRepositoryImpl(remoteDataSource)

	@Provides
	fun providesCreditCardRepository(remoteDataSource: CreditCardRemoteDataSource): CreditCardRepository = CreditCardRepositoryImpl(remoteDataSource)

	@Provides
	fun providesWishListsRepository(remoteDataSource: WishListRemoteDataSource): WishListRepository = WishListRepositoryImpl(remoteDataSource)
}