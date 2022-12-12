package com.zarinfanavaran.presentation.home

import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zarinfanavaran.domain.models.Banner
import com.zarinfanavaran.domain.models.BannersBox
import com.zarinfanavaran.domain.models.ProductsBox
import com.zarinfanavaran.domain.models.SliderBox
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.*

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class HomeAdapter : BaseAdapter<Any>() {

	lateinit var recyclerViewTools: RecyclerViewTools
	private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
	private val mAdapters = SparseArray<Any>()
	private val scrollStates = SparseArray<Parcelable?>()

	override fun onViewRecycled(holder: BaseHolder<Any>) {
		super.onViewRecycled(holder)

		val mRecyclerView = holder.itemView.findViewById<RecyclerView>(R.id.rvItems)
		scrollStates.put(holder.bindingAdapterPosition, mRecyclerView?.layoutManager?.onSaveInstanceState())
	}

	override fun getItemViewType(position: Int): Int {
		return when (mItems[position]) {
			is SliderBox -> R.layout.template_home_slider

			is ProductsBox -> R.layout.template_products

			is BannersBox -> R.layout.template_home_multiple_banner

			is Banner -> R.layout.template_home_single_banner_item

			else -> 0
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Any> {

		val binding = when (viewType) {
			R.layout.template_home_slider -> {
				TemplateHomeSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			}

			R.layout.template_products -> {
				TemplateProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			}

			R.layout.template_home_multiple_banner -> {
				TemplateHomeMultipleBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			}

			R.layout.template_home_single_banner_item -> {
				TemplateHomeSingleBannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			}

			else -> {
				TemplateWarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			}
		}

		return object : BaseHolder<Any>(binding) {
			override fun onBindUI(item: Any, position: Int) {
				when (item) {
					is SliderBox -> handleSlider(item, binding as TemplateHomeSliderBinding)

					is ProductsBox -> handleProducts(item, binding as TemplateProductsBinding)

					is BannersBox -> handleBanners(item, binding as TemplateHomeMultipleBannerBinding)

					is Banner -> handleBanner(item, binding as TemplateHomeSingleBannerItemBinding)
				}
				binding.executePendingBindings()
			}

			private fun handleSlider(sliderBox: SliderBox, binding: TemplateHomeSliderBinding) {
				if (mAdapters[bindingAdapterPosition] == null) {
					val homeSliderAdapter = HomeSliderAdapter(recyclerViewTools)
					homeSliderAdapter.mItems.addAll(sliderBox.slides)
					mAdapters.append(bindingAdapterPosition, homeSliderAdapter)
				}

				binding.vpSlider.adapter = mAdapters[bindingAdapterPosition] as HomeSliderAdapter
			}

			private fun handleProducts(productsBox: ProductsBox, binding: TemplateProductsBinding) {
				binding.item = productsBox

				binding.imgIcon.setImageResource(productsBox.icon)
				binding.imgBackground.setImageDrawable(productsBox.image)

				if (mAdapters[bindingAdapterPosition] == null) {
					val homeProductAdapter = HomeProductAdapter(recyclerViewTools)
					homeProductAdapter.mItems.addAll(productsBox.products)
					mAdapters.put(bindingAdapterPosition, homeProductAdapter)

					binding.rvItems.setHasFixedSize(true)
					binding.rvItems.setRecycledViewPool(viewPool)
					binding.rvItems.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
				}

				scrollStates[bindingAdapterPosition]?.let {
					binding.rvItems.layoutManager?.onRestoreInstanceState(it)
				}
				binding.rvItems.adapter = mAdapters[bindingAdapterPosition] as HomeProductAdapter
			}

			private fun handleBanners(bannersBox: BannersBox, binding: TemplateHomeMultipleBannerBinding) {
				if (mAdapters[bindingAdapterPosition] == null) {
					val homeBannerAdapter = HomeBannerAdapter(recyclerViewTools)
					homeBannerAdapter.mItems = bannersBox.banners
					mAdapters.put(bindingAdapterPosition, homeBannerAdapter)

					binding.rvItems.setHasFixedSize(false)
					binding.rvItems.setRecycledViewPool(viewPool)
					binding.rvItems.layoutManager = GridLayoutManager(binding.root.context, 2)
				}

				scrollStates[bindingAdapterPosition]?.let {
					binding.rvItems.layoutManager?.onRestoreInstanceState(it)
				}
				binding.rvItems.adapter = mAdapters[bindingAdapterPosition] as HomeBannerAdapter
			}

			private fun handleBanner(banner: Banner, binding: TemplateHomeSingleBannerItemBinding) {
				binding.imgBanner.setImageResource(banner.image)
			}
		}
	}
}