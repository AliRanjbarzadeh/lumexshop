package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.domain.repository.UserRepository
import com.zarinfanavaran.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProfileUseCase @Inject constructor(private val repository: UserRepository) {
	suspend operator fun invoke(): NetworkResult<User> = repository.profile()
}