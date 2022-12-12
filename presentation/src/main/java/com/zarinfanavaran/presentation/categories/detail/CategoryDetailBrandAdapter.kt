package com.zarinfanavaran.presentation.categories.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.zarinfanavaran.domain.models.Brand
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateBrandItemBinding
import com.zarinfanavaran.presentation.databinding.TemplateMoreBinding
import com.zarinfanavaran.presentation.databinding.TemplateWarningBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class CategoryDetailBrandAdapter(private val recyclerViewTools: RecyclerViewTools) : BaseAdapter<Any>() {

	override fun getItemViewType(position: Int): Int {
		return when (mItems[position]) {
			is Brand -> R.layout.template_brand_item

			is Category -> R.layout.template_more

			else -> R.layout.template_warning
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Any> {
		val binding = when (viewType) {
			R.layout.template_brand_item -> TemplateBrandItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			R.layout.template_more -> TemplateMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			else -> TemplateWarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		}

		return object : BaseHolder<Any>(binding) {
			override fun onBindUI(item: Any, position: Int) {

				when (item) {
					is Brand -> handleBrand(item, binding as TemplateBrandItemBinding)

					is Category -> handleMore(item, binding as TemplateMoreBinding)
				}

				//set background
				binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorEF))

				binding.executePendingBindings()
			}

			private fun handleBrand(brand: Brand, binding: TemplateBrandItemBinding) {
				binding.item = brand

				//load image
				binding.imgBrand.setImageResource(brand.image)

				//click brand
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, brand) }
			}

			private fun handleMore(category: Category, binding: TemplateMoreBinding) {
				//click more
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, category) }
			}
		}
	}
}