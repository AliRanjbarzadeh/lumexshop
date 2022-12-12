package com.zarinfanavaran.presentation.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateMoreBinding
import com.zarinfanavaran.presentation.databinding.TemplateProductItemBinding
import com.zarinfanavaran.presentation.databinding.TemplateWarningBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class HomeProductAdapter(private val recyclerViewTools: RecyclerViewTools) : BaseAdapter<Any>() {

	override fun getItemViewType(position: Int): Int {
		return when (mItems[position]) {
			is Product -> R.layout.template_product_item

			is Category -> R.layout.template_more

			else -> R.layout.template_warning
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Any> {
		val binding = when (viewType) {
			R.layout.template_product_item -> TemplateProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			R.layout.template_more -> TemplateMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			else -> TemplateWarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		}

		return object : BaseHolder<Any>(binding) {
			override fun onBindUI(item: Any, position: Int) {

				when (item) {
					is Product -> handleProduct(item, binding as TemplateProductItemBinding)

					is Category -> handleMore(item, binding as TemplateMoreBinding)
				}

				binding.executePendingBindings()
			}

			private fun handleProduct(product: Product, binding: TemplateProductItemBinding) {
				binding.item = product

				//load image
				binding.imgProduct.setImageResource(product.image)

				binding.txtRealPrice.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG

				//click product
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, product) }
			}

			private fun handleMore(category: Category, binding: TemplateMoreBinding) {
				//click more
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, category) }
			}
		}
	}
}