package com.zarinfanavaran.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.Color
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateColorBinding

/**
 * Created by Ali Ranjbarzadeh on 2023/01/29.
 */
class ProductColorsAdapter : BaseAdapter<Color>() {

	lateinit var glide: RequestManager

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Color> {
		val binding = TemplateColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return object : BaseHolder<Color>(binding) {
			override fun onBindUI(item: Color, position: Int) {

				//set icon
				item.media?.icon?.also {
					glide.load(it.file).into(binding.imgColor)
				}

				binding.executePendingBindings()
			}
		}
	}
}