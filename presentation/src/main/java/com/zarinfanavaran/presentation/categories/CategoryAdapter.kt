package com.zarinfanavaran.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateCategoryItemBinding
import com.zarinfanavaran.presentation.databinding.TemplateMoreBinding
import com.zarinfanavaran.presentation.databinding.TemplateWarningBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class CategoryAdapter(private val recyclerViewTools: RecyclerViewTools) : BaseAdapter<Category>() {

	lateinit var glide: RequestManager

	override fun getItemViewType(position: Int): Int {
		val category = mItems[position];
		return when (category.isHasMore) {
			false -> R.layout.template_category_item

			true -> R.layout.template_more
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Category> {
		val binding = when (viewType) {
			R.layout.template_category_item -> TemplateCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			R.layout.template_more -> TemplateMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			else -> TemplateWarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		}

		return object : BaseHolder<Category>(binding) {
			override fun onBindUI(item: Category, position: Int) {

				if (!item.isHasMore) {
					handleCategory(item, binding as TemplateCategoryItemBinding)
				} else {
					binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }
				}

				binding.executePendingBindings()
			}

			private fun handleCategory(category: Category, binding: TemplateCategoryItemBinding) {
				binding.item = category

				//set image
				category.media?.main?.also {
					glide.load(it.file).into(binding.imgCategory)
				}

				//handle click
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, category) }
			}
		}
	}
}