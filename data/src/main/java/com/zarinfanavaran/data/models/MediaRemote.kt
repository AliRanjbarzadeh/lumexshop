package com.zarinfanavaran.data.models

import com.zarinfanavaran.data.base.ResponseObject
import com.zarinfanavaran.domain.models.Media

data class MediaRemote(
	val main: Main?,
	val icon: Icon?
) : ResponseObject<Media> {
	data class Main(
		val id: Int,
		val file: String
	) : ResponseObject<Media.Main> {
		override fun toDomain(): Media.Main = Media.Main(id, file)
	}

	data class Icon(
		val id: Int,
		val file: String
	) : ResponseObject<Media.Icon> {
		override fun toDomain(): Media.Icon = Media.Icon(id, file)
	}

	override fun toDomain(): Media = Media(main?.toDomain(), icon?.toDomain())
}