package com.zarinfanavaran.presentation.profile.info

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.BankAccount
import com.zarinfanavaran.domain.models.CreditCard
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateBankAccountItemBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class ProfileBankAccountAdapter : BaseAdapter<CreditCard>() {

	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<CreditCard> {
		val binding = TemplateBankAccountItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<CreditCard>(binding) {
			override fun onBindUI(item: CreditCard, position: Int) {
				binding.item = item

				//clicks
				binding.btnEdit.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}
		}
	}
}