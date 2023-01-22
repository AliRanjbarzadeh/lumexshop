package com.zarinfanavaran.presentation.categories.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateCategoryDetailCategoryItemBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class CategoryDetailCategoryAdapter(private val recyclerViewTools: RecyclerViewTools, private val glide: RequestManager) : BaseAdapter<Category>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Category> {
		val binding = TemplateCategoryDetailCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Category>(binding) {
			override fun onBindUI(item: Category, position: Int) {
				binding.item = item

				//load image
				item.media?.main?.also {
					glide.load(it.file).into(binding.imgCategory)
				}

				//handle click
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}
		}
	}
}