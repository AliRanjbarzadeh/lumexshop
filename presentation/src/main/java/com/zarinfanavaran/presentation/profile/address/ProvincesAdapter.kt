package com.zarinfanavaran.presentation.profile.address

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.Province
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateProvinceBinding

class ProvincesAdapter : BaseAdapter<Province>() {
	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Province> {
		val binding = TemplateProvinceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Province>(binding) {
			override fun onBindUI(item: Province, position: Int) {
				binding.item = item

				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}

		}
	}
}