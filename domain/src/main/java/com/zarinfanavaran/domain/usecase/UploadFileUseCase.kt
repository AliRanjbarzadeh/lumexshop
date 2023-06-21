package com.zarinfanavaran.domain.usecase

import com.zarinfanavaran.domain.models.Media
import com.zarinfanavaran.domain.repository.GlobalRepository
import com.zarinfanavaran.domain.util.NetworkResult
import okhttp3.MultipartBody
import retrofit2.http.Part
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadFileUseCase @Inject constructor(private val repository: GlobalRepository) {
	suspend operator fun invoke(
		@Part
		file: MultipartBody.Part
	): NetworkResult<Media> = repository.uploadFile(file)
}