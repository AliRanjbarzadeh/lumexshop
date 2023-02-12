package com.zarinfanavaran.presentation.filter

import android.graphics.Paint
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.databinding.LoadmoreVerticalBinding
import com.zarinfanavaran.presentation.databinding.TemplateFilterProductItemBinding

/**
 * Created by Ali Ranjbarzadeh on 2023/01/29.
 */
class FilterProductsAdapter : BaseAdapter<Product>() {

	lateinit var recyclerViewTools: RecyclerViewTools
	lateinit var glide: RequestManager
	private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

	private val mColorAdapters = SparseArray<FilterProductColorsAdapter>()

	override fun getItemViewType(position: Int): Int {
		val item = mItems[position]
		return if (item.id == -1) {
			R.layout.loadmore_vertical
		} else {
			R.layout.template_filter_product_item
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Product> {
		val binding = if (viewType == R.layout.loadmore_vertical) {
			LoadmoreVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		} else {
			TemplateFilterProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		}
		return object : BaseHolder<Product>(binding) {
			override fun onBindUI(item: Product, position: Int) {
				if (item.id > 0) {
					handleProduct(item, binding as TemplateFilterProductItemBinding)
				}

				binding.executePendingBindings()
			}

			private fun handleProduct(item: Product, binding: TemplateFilterProductItemBinding) {
				binding.item = item

				//set icon
				item.media?.main?.also {
					glide.load(it.file).into(binding.imgProduct)
				}

				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.txtRealPrice.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG

				if (mColorAdapters[bindingAdapterPosition] == null) {
					val colorsAdapter = FilterProductColorsAdapter().apply {
						glide = this@FilterProductsAdapter.glide
					}

					item.items?.forEach { mItem ->
						mItem.color?.also { colorsAdapter.mItems.add(it) }
					}

					mColorAdapters[bindingAdapterPosition] = colorsAdapter

					binding.rvColors.setHasFixedSize(true)
					binding.rvColors.setRecycledViewPool(viewPool)
					binding.rvColors.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
				}

				try {
					binding.rvColors.removeItemDecorationAt(0)
				} catch (_: Exception) {
				} finally {
					binding.rvColors.addItemDecoration(MarginItemDecoration(
						binding.root.context.resources.getDimension(com.intuit.sdp.R.dimen._2sdp).toInt(),
						MarginItemDecoration.RIGHT
					))
				}

				binding.rvColors.adapter = mColorAdapters[bindingAdapterPosition]
			}
		}
	}
}