package com.zarinfanavaran.presentation.categories.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.RequestManager
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
class CategoryDetailBrandAdapter(private val recyclerViewTools: RecyclerViewTools, private val glide: RequestManager) : BaseAdapter<Brand>() {

	override fun getItemViewType(position: Int): Int {
		val brand = mItems[position]
		return when {
			brand.id > 0 -> R.layout.template_brand_item

			brand.id == 0 -> R.layout.template_more

			else -> R.layout.template_warning
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Brand> {
		val binding = when (viewType) {
			R.layout.template_brand_item -> TemplateBrandItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			R.layout.template_more -> TemplateMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			else -> TemplateWarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		}

		return object : BaseHolder<Brand>(binding) {
			override fun onBindUI(item: Brand, position: Int) {

				when {
					item.id > 0 -> handleBrand(item, binding as TemplateBrandItemBinding)

					item.id == 0 -> handleMore(item, binding as TemplateMoreBinding)
				}

				//set background
				binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorEF))

				binding.executePendingBindings()
			}

			private fun handleBrand(brand: Brand, binding: TemplateBrandItemBinding) {
				binding.item = brand

				//load image
				brand.media?.main?.also {
					glide.load(it.file).into(binding.imgBrand)
				}

				//click brand
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, brand) }
			}

			private fun handleMore(brand: Brand, binding: TemplateMoreBinding) {
				//click more
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, brand) }
			}
		}
	}
}