package com.zarinfanavaran.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.Banner
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateHomeBannerItemBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class HomeBannerAdapter(private val recyclerViewTools: RecyclerViewTools) : BaseAdapter<Banner>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Banner> {
		val binding = TemplateHomeBannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Banner>(binding) {
			override fun onBindUI(item: Banner, position: Int) {
				binding.item = item

				//load image
				binding.imgBanner.setImageResource(item.image)

				//click banner
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}

		}
	}
}