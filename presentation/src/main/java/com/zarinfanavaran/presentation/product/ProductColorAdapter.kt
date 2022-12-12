package com.zarinfanavaran.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.ProductColor
import com.zarinfanavaran.domain.models.ProductTool
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateProductColorBinding
import com.zarinfanavaran.presentation.databinding.TemplateProductToolBinding

class ProductColorAdapter : BaseAdapter<ProductColor>() {

	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ProductColor> {
		val binding = TemplateProductColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<ProductColor>(binding) {
			override fun onBindUI(item: ProductColor, position: Int) {
				binding.item = item

				//click
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}

		}
	}
}