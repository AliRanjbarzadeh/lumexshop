package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.LoginMobile
import com.zarinfanavaran.domain.repository.UserRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthResendUseCase @Inject constructor(private val repository: UserRepository) {
	suspend operator fun invoke(body: RequestBody): NetworkResult<LoginMobile> = repository.resend(body)
}