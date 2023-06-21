package com.zarinfanavaran.presentation.profile.wishlist

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.WishList
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.databinding.LoadmoreVerticalBinding
import com.zarinfanavaran.presentation.databinding.TemplateWishlistItemBinding
import com.zarinfanavaran.presentation.product.ProductColorsAdapter

/**
 * Created by Ali Ranjbarzadeh on 2023/01/29.
 */
class WishListsAdapter : BaseAdapter<WishList>() {

	lateinit var recyclerViewTools: RecyclerViewTools
	lateinit var glide: RequestManager
	private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

	private val mColorAdapters = SparseArray<ProductColorsAdapter>()

	override fun getItemViewType(position: Int): Int {
		val item = mItems[position]
		return if (item.id == -1) {
			R.layout.loadmore_vertical
		} else {
			R.layout.template_wishlist_item
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<WishList> {
		val binding = if (viewType == R.layout.loadmore_vertical) {
			LoadmoreVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		} else {
			TemplateWishlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		}
		return object : BaseHolder<WishList>(binding) {
			override fun onBindUI(item: WishList, position: Int) {
				if (item.id > 0) {
					handleWishList(item, binding as TemplateWishlistItemBinding)
				}

				binding.executePendingBindings()
			}

			private fun handleWishList(item: WishList, binding: TemplateWishlistItemBinding) {
				binding.item = item

				//set icon
				item.product.media?.main?.also {
					glide.load(it.file).into(binding.imgProduct)
				}

				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }
				binding.btnDelete.setOnClickListener { recyclerViewTools.onDeleteClick(bindingAdapterPosition, it, item) }

				if (mColorAdapters[bindingAdapterPosition] == null) {
					val colorsAdapter = ProductColorsAdapter().apply {
						glide = this@WishListsAdapter.glide
					}

					item.product.items?.forEach { mItem ->
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
					binding.rvColors.addItemDecoration(
						MarginItemDecoration(
							binding.root.context.resources.getDimension(com.intuit.sdp.R.dimen._2sdp).toInt(),
							MarginItemDecoration.RIGHT
						)
					)
				}

				binding.rvColors.adapter = mColorAdapters[bindingAdapterPosition]
			}
		}
	}
}