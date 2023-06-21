package com.zarinfanavaran.data.api

import com.google.gson.JsonElement
import com.zarinfanavaran.data.models.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

	/*=============login==============*/
	@POST("auth/mobile/check")
	fun authLoginAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<LoginMobileRemote, MetaRemoot>>

	@POST("auth/otp/check")
	fun authVerifyAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<UserRemote, MetaRemoot>>

	@POST("auth/otp/resend")
	fun authResendAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<LoginMobileRemote, MetaRemoot>>

	/*=============category==============*/
	@GET("category")
	fun fetchCategoriesAsync(): Deferred<MyResponseRemote<List<CategoryRemote>, MetaRemoot>>

	@GET("category/{category}/info")
	fun fetchCategoryDetailAsync(
		@Path("category")
		categoryId: Int
	): Deferred<MyResponseRemote<CategoryDetailRemote, MetaRemoot>>

	/*=============product==============*/
	@GET("products")
	fun fetchProductsAsync(
		@QueryMap
		params: Map<String, @JvmSuppressWildcards Any?>
	): Deferred<MyResponseRemote<List<ProductRemote>, MetaRemoot>>

	@GET("products/{id}")
	fun fetchProductDetailAsync(
		@Path("id")
		productId: Int
	): Deferred<MyResponseRemote<ProductDetailRemote, MetaRemoot>>

	/*=============profile==============*/
	@GET("profile")
	fun getProfileAsync(): Deferred<MyResponseRemote<UserRemote, MetaRemoot>>

	@POST("profile/logout")
	fun logoutAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	@PATCH("profile/avatar")
	fun saveAvatarAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	@PATCH("profile/info")
	fun saveInfoAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	@PATCH("profile/born-at")
	fun saveBornAtAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	@PATCH("profile/email/send")
	fun saveEmailAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	/*=============credit card==============*/
	@GET("profile/credit-cards")
	fun fetchCreditCardsAsync(): Deferred<MyResponseRemote<List<CreditCardRemote>, MetaRemoot>>

	@POST("profile/credit-cards")
	fun saveCreditCardAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<CreditCardRemote, MetaRemoot>>

	@DELETE("profile/credit-cards/{id}")
	fun deleteCreditCardAsync(
		@Path("id")
		id: Int
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	@POST("profile/credit-cards/iban/info")
	fun getBankInfoAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	/*=============address==============*/
	@GET("profile/addresses")
	fun fetchAddressesAsync(): Deferred<MyResponseRemote<List<AddressRemote>, MetaRemoot>>

	@GET("profile/addresses/{id}")
	fun fetchAddressAsync(
		@Path("id")
		addressId: Int
	): Deferred<MyResponseRemote<AddressRemote, MetaRemoot>>

	@POST("profile/addresses")
	fun addAddressAsync(
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<AddressRemote, MetaRemoot>>

	@PUT("profile/addresses/{id}")
	fun updateAddressAsync(
		@Path("id")
		addressId: Int,
		@Body
		body: RequestBody
	): Deferred<MyResponseRemote<AddressRemote, MetaRemoot>>

	@DELETE("profile/addresses/{id}")
	fun deleteAddressAsync(
		@Path("id")
		addressId: Int
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	/*=============wishlist==============*/
	@GET("profile/wishlists")
	fun fetchWishListsAsync(
		@QueryMap
		params: Map<String, @JvmSuppressWildcards Any?>
	): Deferred<MyResponseRemote<List<WishListRemote>, MetaRemoot>>

	@POST("profile/wishlists/{product}/add")
	fun addToWishListAsync(
		@Path("product")
		productId: Int
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	@DELETE("profile/wishlists/{product}/remove")
	fun deleteFromWishListAsync(
		@Path("product")
		productId: Int
	): Deferred<MyResponseRemote<JsonElement, MetaRemoot>>

	/*=============global==============*/
	@Multipart
	@POST("files/upload")
	fun uploadAsync(
		@Part
		file: MultipartBody.Part
	): Deferred<MyResponseRemote<MediaRemote, MetaRemoot>>

	@GET("data/provinces")
	fun fetchProvincesAsync(): Deferred<MyResponseRemote<List<ProvinceRemote>, MetaRemoot>>

	@GET("data/cities/{provinceId}")
	fun fetchCitiesAsync(
		@Path("provinceId")
		provinceId: Int
	): Deferred<MyResponseRemote<List<CityRemote>, MetaRemoot>>
}