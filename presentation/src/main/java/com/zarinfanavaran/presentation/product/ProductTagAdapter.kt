package com.zarinfanavaran.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.ListTitle
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateProductTagBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class ProductTagAdapter : BaseAdapter<ListTitle>() {
	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ListTitle> {
		val binding = TemplateProductTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<ListTitle>(binding) {
			override fun onBindUI(item: ListTitle, position: Int) {
				binding.item = item

				if (bindingAdapterPosition == mItems.size - 1) {
					binding.btnTag.icon = null
				}

				binding.executePendingBindings()
			}
		}
	}
}