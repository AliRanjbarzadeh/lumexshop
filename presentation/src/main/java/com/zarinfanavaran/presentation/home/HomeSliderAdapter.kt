package com.zarinfanavaran.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.Slide
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.base.BaseSlideAdapter
import com.zarinfanavaran.presentation.databinding.TemplateHomeSliderItemBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class HomeSliderAdapter(private val recyclerViewTools: RecyclerViewTools) : BaseSlideAdapter<Slide>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Slide> {
		val binding = TemplateHomeSliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Slide>(binding) {
			override fun onBindUI(item: Slide, position: Int) {
				//set image
				binding.imgSlide.setImageResource(item.image)

				binding.executePendingBindings()
			}
		}
	}
}