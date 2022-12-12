package com.zarinfanavaran.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.zarinfanavaran.domain.models.ProfileMenuItem
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateProfileMenuItemBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class ProfileMenuAdapter : BaseAdapter<ProfileMenuItem>() {

	lateinit var recyclerViewTools: RecyclerViewTools

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ProfileMenuItem> {
		val binding = TemplateProfileMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<ProfileMenuItem>(binding) {
			override fun onBindUI(item: ProfileMenuItem, position: Int) {
				binding.item = item

				binding.btnTitle.icon = ContextCompat.getDrawable(binding.root.context, item.icon)
				binding.btnTitle.iconTint = ContextCompat.getColorStateList(binding.root.context, item.color)
				binding.btnTitle.setTextColor(ContextCompat.getColor(binding.root.context, item.color))

				//clicks
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}
		}
	}
}