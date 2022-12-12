package com.zarinfanavaran.presentation.shopcart.address

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.Receiver
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateReceiverItemBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class ShopCartReceiverAdapter : BaseAdapter<Receiver>() {
	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Receiver> {
		val binding = TemplateReceiverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Receiver>(binding) {
			override fun onBindUI(item: Receiver, position: Int) {
				binding.item = item

				//clicks
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }
				binding.btnDelete.setOnClickListener { recyclerViewTools.onDeleteClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}
		}
	}
}