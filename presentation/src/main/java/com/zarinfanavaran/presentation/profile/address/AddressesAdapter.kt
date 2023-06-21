package com.zarinfanavaran.presentation.profile.address

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateAddressBinding

class AddressesAdapter : BaseAdapter<Address>() {

	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Address> {
		val binding = TemplateAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return object : BaseHolder<Address>(binding) {
			override fun onBindUI(item: Address, position: Int) {
				binding.item = item

				binding.imgMore.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}
		}
	}
}