package com.zarinfanavaran.presentation.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.Intro
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateIntroBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class IntroAdapter : BaseAdapter<Intro>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Intro> {
		val binding = TemplateIntroBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Intro>(binding) {
			override fun onBindUI(item: Intro, position: Int) {
				//bind item
				binding.item = item

				//set image
				binding.imgIntro.setImageResource(item.image)

				binding.executePendingBindings()
			}
		}
	}
}