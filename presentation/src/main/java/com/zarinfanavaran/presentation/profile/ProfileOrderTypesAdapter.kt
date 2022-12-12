package com.zarinfanavaran.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.OrderType
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateOrderTypeItemBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class ProfileOrderTypesAdapter : BaseAdapter<OrderType>() {

	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<OrderType> {
		val binding = TemplateOrderTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<OrderType>(binding) {
			override fun onBindUI(item: OrderType, position: Int) {
				binding.item = item

				//set image
				binding.imgOrderType.setImageResource(item.image)

				//clicks
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}
		}
	}
}