package com.zarinfanavaran.presentation.shopcart

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.ListTitle
import com.zarinfanavaran.domain.models.ProductShopCart
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateListTitleBinding
import com.zarinfanavaran.presentation.databinding.TemplateShopcartItemBinding
import com.zarinfanavaran.presentation.databinding.TemplateWarningBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class ShopCartProductAdapter(private val recyclerViewTools: RecyclerViewTools) : BaseAdapter<Any>() {

	override fun getItemViewType(position: Int): Int {
		return when (mItems[position]) {
			is ProductShopCart -> R.layout.template_shopcart_item

			is ListTitle -> R.layout.template_list_title

			else -> R.layout.template_warning
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Any> {
		val binding = when (viewType) {
			R.layout.template_shopcart_item -> TemplateShopcartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			R.layout.template_list_title -> TemplateListTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

			else -> TemplateWarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		}

		return object : BaseHolder<Any>(binding) {
			override fun onBindUI(item: Any, position: Int) {

				when (item) {
					is ProductShopCart -> handleProduct(item, binding as TemplateShopcartItemBinding)

					is ListTitle -> handleTitle(item, binding as TemplateListTitleBinding)
				}

				binding.executePendingBindings()
			}

			private fun handleTitle(listTitle: ListTitle, binding: TemplateListTitleBinding) {
				binding.item = listTitle
			}

			private fun handleProduct(productShopCart: ProductShopCart, binding: TemplateShopcartItemBinding) {
				binding.item = productShopCart

				//load image
				binding.imgProduct.setImageResource(productShopCart.image)

				//click product
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, productShopCart) }

				//set product color
				val colorDrawable = ColorDrawable(Color.parseColor(productShopCart.colorValue))
				binding.imgColor.setImageDrawable(colorDrawable)

				//clicks
				binding.btnIncrease.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, productShopCart) }
				binding.btnDecrease.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, productShopCart) }

			}
		}
	}
}