package com.zarinfanavaran.presentation.categories.detail

import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.BrandsBox
import com.zarinfanavaran.domain.models.CategoriesBox
import com.zarinfanavaran.domain.models.ProductsBox
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateBrandsBinding
import com.zarinfanavaran.presentation.databinding.TemplateCategoryDetailCategoriesBinding
import com.zarinfanavaran.presentation.databinding.TemplateProductsBinding
import com.zarinfanavaran.presentation.databinding.TemplateWarningBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class CategoryDetailAdapter : BaseAdapter<Any>() {

	lateinit var recyclerViewTools: RecyclerViewTools
	lateinit var glide: RequestManager

	private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
	private val mAdapters = SparseArray<Any>()
	private val scrollStates = SparseArray<Parcelable?>()

	override fun onViewRecycled(holder: BaseHolder<Any>) {
		super.onViewRecycled(holder)

		val mRecyclerView = holder.itemView.findViewById<RecyclerView>(R.id.rvItems)
		scrollStates.append(holder.bindingAdapterPosition, mRecyclerView?.layoutManager?.onSaveInstanceState())
	}

	override fun getItemViewType(position: Int): Int {
		return when (mItems[position]) {
			is CategoriesBox -> R.layout.template_category_detail_categories

			is BrandsBox -> R.layout.template_brands

			is ProductsBox -> R.layout.template_products

			else -> 0
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Any> {

		val binding = when (viewType) {
			R.layout.template_category_detail_categories -> {
				TemplateCategoryDetailCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			}

			R.layout.template_brands -> {
				TemplateBrandsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			}

			R.layout.template_products -> {
				TemplateProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			}

			else -> {
				TemplateWarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			}
		}

		return object : BaseHolder<Any>(binding) {
			override fun onBindUI(item: Any, position: Int) {
				when (item) {
					is CategoriesBox -> handleCategories(item, binding as TemplateCategoryDetailCategoriesBinding)

					is BrandsBox -> handleBrands(item, binding as TemplateBrandsBinding)

					is ProductsBox -> handleProducts(item, binding as TemplateProductsBinding)
				}
				binding.executePendingBindings()
			}

			private fun handleCategories(categoriesBox: CategoriesBox, binding: TemplateCategoryDetailCategoriesBinding) {
				if (mAdapters[bindingAdapterPosition] == null) {
					val categoryDetailCategoryAdapter = CategoryDetailCategoryAdapter(recyclerViewTools, glide)
					categoryDetailCategoryAdapter.mItems.addAll(categoriesBox.categories)
					mAdapters.append(bindingAdapterPosition, categoryDetailCategoryAdapter)

					binding.rvItems.setHasFixedSize(false)
					binding.rvItems.setRecycledViewPool(viewPool)
					binding.rvItems.layoutManager = GridLayoutManager(binding.root.context, 2)
				}

				scrollStates[bindingAdapterPosition]?.let {
					binding.rvItems.layoutManager?.onRestoreInstanceState(it)
				}
				binding.rvItems.adapter = mAdapters[bindingAdapterPosition] as CategoryDetailCategoryAdapter
			}

			private fun handleBrands(brandsBox: BrandsBox, binding: TemplateBrandsBinding) {
				binding.item = brandsBox

				//set icon
				binding.imgIcon.setImageResource(brandsBox.icon)

				if (mAdapters[bindingAdapterPosition] == null) {
					val categoryDetailBrandAdapter = CategoryDetailBrandAdapter(recyclerViewTools, glide)
					categoryDetailBrandAdapter.mItems.addAll(brandsBox.items)
					mAdapters.append(bindingAdapterPosition, categoryDetailBrandAdapter)

					binding.rvItems.setHasFixedSize(true)
					binding.rvItems.setRecycledViewPool(viewPool)
					binding.rvItems.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
				}

				scrollStates[bindingAdapterPosition]?.let {
					binding.rvItems.layoutManager?.onRestoreInstanceState(it)
				}
				binding.rvItems.adapter = mAdapters[bindingAdapterPosition] as CategoryDetailBrandAdapter
			}

			private fun handleProducts(productsBox: ProductsBox, binding: TemplateProductsBinding) {
				binding.item = productsBox

				binding.imgIcon.setImageResource(productsBox.icon)
				binding.imgBackground.setImageDrawable(productsBox.image)

				if (mAdapters[bindingAdapterPosition] == null) {
					val categoryDetailProductAdapter = CategoryDetailProductAdapter(recyclerViewTools, glide)
					categoryDetailProductAdapter.mItems.addAll(productsBox.products)
					mAdapters.append(bindingAdapterPosition, categoryDetailProductAdapter)

					binding.rvItems.setHasFixedSize(true)
					binding.rvItems.setRecycledViewPool(viewPool)
					binding.rvItems.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
				}

				scrollStates[bindingAdapterPosition]?.let {
					binding.rvItems.layoutManager?.onRestoreInstanceState(it)
				}
				binding.rvItems.adapter = mAdapters[bindingAdapterPosition] as CategoryDetailProductAdapter
			}
		}
	}
}