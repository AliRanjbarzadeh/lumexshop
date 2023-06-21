package com.zarinfanavaran.presentation.profile.address

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.City
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateCityBinding

class CitiesAdapter : BaseAdapter<City>() {
	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<City> {
		val binding = TemplateCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<City>(binding) {
			override fun onBindUI(item: City, position: Int) {
				binding.item = item

				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}

		}
	}
}