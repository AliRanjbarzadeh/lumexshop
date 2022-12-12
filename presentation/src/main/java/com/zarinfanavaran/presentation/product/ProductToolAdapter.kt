package com.zarinfanavaran.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.ProductTool
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateProductToolBinding

class ProductToolAdapter : BaseAdapter<ProductTool>() {

	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ProductTool> {
		val binding = TemplateProductToolBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<ProductTool>(binding) {
			override fun onBindUI(item: ProductTool, position: Int) {
				binding.item = item

				//click
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}

		}
	}
}