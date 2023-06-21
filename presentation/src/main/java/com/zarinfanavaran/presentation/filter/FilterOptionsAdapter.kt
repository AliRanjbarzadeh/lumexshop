package com.zarinfanavaran.presentation.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.Filter
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateFilterOptionBinding

class FilterOptionsAdapter(private val parentPosition: Int) : BaseAdapter<Filter.Option>() {

	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Filter.Option> {
		val binding = TemplateFilterOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Filter.Option>(binding) {
			override fun onBindUI(item: Filter.Option, position: Int) {
				binding.item = item

				binding.root.setOnClickListener { recyclerViewTools.onItemSelect(bindingAdapterPosition, it, item, parentPosition) }

				binding.executePendingBindings()
			}
		}
	}
}