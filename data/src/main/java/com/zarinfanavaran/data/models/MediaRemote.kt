package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Media

data class MediaRemote(
	val media: MediaChild?,
	val main: MediaChild?,
	val icon: MediaChild?,
	val logo: MediaChild?,
	val avatar: MediaChild?,
) : ResponseObject<Media> {
	data class MediaChild(
		val id: Int,
		val file: String
	) : ResponseObject<Media.MediaChild> {
		override fun toDomain(): Media.MediaChild = Media.MediaChild(id, file)
	}

	override fun toDomain(): Media = Media(media?.toDomain(), main?.toDomain(), icon?.toDomain(), logo?.toDomain(), avatar?.toDomain())
}