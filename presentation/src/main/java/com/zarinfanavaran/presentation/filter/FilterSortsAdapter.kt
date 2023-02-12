package com.zarinfanavaran.presentation.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.SortOption
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateSortOptionBinding

class FilterSortsAdapter : BaseAdapter<SortOption>() {

	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<SortOption> {
		val binding = TemplateSortOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<SortOption>(binding) {
			override fun onBindUI(item: SortOption, position: Int) {
				binding.item = item

				//on sort selected
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}
		}
	}
}