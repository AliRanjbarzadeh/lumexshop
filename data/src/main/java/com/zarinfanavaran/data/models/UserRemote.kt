package com.zarinfanavaran.data.models

import com.google.gson.annotations.SerializedName
import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.User

data class UserRemote(
	val id: Int,
	@SerializedName("first_name")
	val firstName: String,
	@SerializedName("last_name")
	val lastName: String,
	@SerializedName("full_name")
	val fullName: String,
	@SerializedName("mobile_number")
	val mobileNumber: String,
	@SerializedName("national_code")
	val nationalCode: String,
	@SerializedName("email")
	val email: String,
	@SerializedName("wallet_amount")
	val walletAmount: Int,
	@SerializedName("point_amount")
	val pointAmount: Float,
	@SerializedName("wallet_amount_prettified")
	val walletAmountPrettified: String,
	@SerializedName("point_amount_prettified")
	val pointAmountPrettified: String,
	val status: String,
	@SerializedName("status_info")
	val statusInfo: StatusInfo?,
	val gender: String,
	@SerializedName("gender_info")
	val genderInfo: GenderInfo?,
	@SerializedName("born_at")
	val bornAt: String,
	@SerializedName("jborn_at")
	val jalaliBornAt: String,
	@SerializedName("access_token")
	val accessToken: String?,
	@SerializedName("email_verified_at")
	val emailVerifiedAt: String,
	@SerializedName("jemail_verified_at")
	val jalaliEmailVerifiedAt: String,
	@SerializedName("questions_waiting_to_answer_count")
	val questionsWaitingToAnswerCount: Int = 0,
	@SerializedName("products_waiting_to_comment_count")
	val productsWaitingToCommentCount: Int = 0,
	@SerializedName("in_progress_orders_count")
	val inProgressOrdersCount: Int = 0,
	@SerializedName("sent_progress_orders_count")
	val sentProgressOrdersCount: Int = 0,
	@SerializedName("returned_orders_count")
	val returnedOrdersCount: Int = 0,
	@SerializedName("expired_orders_count")
	val expiredOrdersCount: Int = 0,
	@SerializedName("unread_notifications_count")
	val unreadNotificationsCount: Int = 0,
	val media: MediaRemote?
) : ResponseObject<User> {

	override fun toDomain(): User = User(
		id, firstName, lastName, fullName, mobileNumber, nationalCode, email, walletAmount, pointAmount,
		walletAmountPrettified, pointAmountPrettified, status, statusInfo?.toDomain(), gender, genderInfo?.toDomain(),
		bornAt, jalaliBornAt, accessToken ?: "", emailVerifiedAt, jalaliEmailVerifiedAt,
		questionsWaitingToAnswerCount, productsWaitingToCommentCount, inProgressOrdersCount, sentProgressOrdersCount,
		returnedOrdersCount, expiredOrdersCount, unreadNotificationsCount,
		media?.toDomain()
	)

	data class StatusInfo(
		val name: String,
		val color: String,
	) : ResponseObject<User.StatusInfo> {
		override fun toDomain(): User.StatusInfo = User.StatusInfo(name, color)
	}

	data class GenderInfo(
		val name: String,
	) : ResponseObject<User.GenderInfo> {
		override fun toDomain(): User.GenderInfo = User.GenderInfo(name)
	}
}