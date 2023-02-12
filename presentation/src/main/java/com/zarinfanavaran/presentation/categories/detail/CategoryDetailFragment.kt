package com.zarinfanavaran.presentation.categories.detail

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.*
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.base.RetryDialog
import com.zarinfanavaran.presentation.databinding.FragmentCategoryDetailBinding
import com.zarinfanavaran.presentation.filter.FilterActivity
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */

@AndroidEntryPoint
class CategoryDetailFragment :
	BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {

	@Inject
	lateinit var glide: RequestManager

	private val args: CategoryDetailFragmentArgs by navArgs()

	private val viewModel: CategoryDetailViewModel by viewModels()

	private val categoryDetailAdapter = CategoryDetailAdapter()

	private lateinit var categoryDetail: CategoryDetail

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()

		categoryDetailAdapter.apply {
			recyclerViewTools = this@CategoryDetailFragment
			glide = this@CategoryDetailFragment.glide
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun onRetry() {
		viewModel.fetchDetail(args.categoryId)
	}

	override fun onCancel() {
		back()
	}

	override fun <T> onItemClick(position: Int, view: View, item: T) {
		val filterIntent = Intent(requireActivity(), FilterActivity::class.java)
		filterIntent.putExtra("categoryDetail", categoryDetail)
		when (item) {
			is Category -> {
				if (!item.isHasMore) {
					filterIntent.putExtra("category", item)
				} else {
					categoryDetail.sortOptions?.find { sortOption -> sortOption.key == item.name }?.also { sortOption ->
						filterIntent.putExtra("sortOption", sortOption)
					}
				}
				startActivity(filterIntent)
			}

			is Brand -> {
				if (item.id > 0) {
					filterIntent.putExtra("brand", item)
				}
				startActivity(filterIntent)
			}

			is BrandsBox -> {
				startActivity(filterIntent)
			}

			is ProductsBox -> {
				categoryDetail.sortOptions?.find { sortOption -> sortOption.key == item.sort }?.also { sortOption ->
					filterIntent.putExtra("sortOption", sortOption)
				}
				startActivity(filterIntent)
			}
		}
	}

	private fun setupUI() {
		binding.txtToolbarTitle.text = getToolbarSearchText()
		binding.imgToolbarIcon.setOnClickListener { back() }

		Log.d(TAG, "setupUI: ${categoryDetailAdapter.mItems}")

		if (categoryDetailAdapter.mItems.isNotEmpty()) // check if already category detail fetched
			setAdapter()
		else // fetch category detail
			viewModel.fetchDetail(args.categoryId)
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getCategoryDetail(), ::initCategoryDetail)
		}
	}

	private fun initLoading(isLoading: Boolean) {
		setProgressView(binding.clMain, isLoading)
	}

	private fun initCategoryDetail(result: NetworkResult<CategoryDetail>) {
		if (result is NetworkResult.Success) {
			if (categoryDetailAdapter.mItems.isNotEmpty()) {
				categoryDetailAdapter.mItems.clear()
			}

			categoryDetail = result.data

			//categories
			categoryDetail.category.children?.also { children ->
				categoryDetailAdapter.mItems.add(CategoriesBox(children.toMutableList()))
			}

			//brands
			if (categoryDetail.brands.isNotEmpty()) {
				categoryDetail.brands.add(Brand(0, "", "", "", null))
				categoryDetailAdapter.mItems.add(
					BrandsBox(
						title = "برند ها",
						items = categoryDetail.brands,
						icon = R.drawable.temp_icon_brands
					)
				)
			}

			//most sold products
			if (categoryDetail.mostSoldProducts.isNotEmpty()) {
				val mostSoldProducts = mutableListOf<Any>()
				mostSoldProducts.addAll(categoryDetail.mostSoldProducts)
				mostSoldProducts.add(Category(0, "MOST_SOLD", 0, null, null).apply { isHasMore = true })

				val drawableWhite =
					ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white))

				categoryDetailAdapter.mItems.add(
					ProductsBox(
						icon = R.drawable.temp_icon_chart,
						title = "پرفروش ترین ها",
						image = drawableWhite,
						products = mostSoldProducts,
						sort = "MOST_SOLD"
					)
				)
			}

			//most viewed products
			if (categoryDetail.mostViewedProducts.isNotEmpty()) {
				val mostViewedProducts = mutableListOf<Any>()
				mostViewedProducts.addAll(categoryDetail.mostViewedProducts)
				mostViewedProducts.add(Category(0, "MOST_VISITED", 0, null, null).apply { isHasMore = true })

				val drawableWhite =
					ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white))

				categoryDetailAdapter.mItems.add(
					ProductsBox(
						icon = R.drawable.temp_icon_chart,
						title = "محبوب ترین ها",
						image = drawableWhite,
						products = mostViewedProducts
					)
				)
			}

			setAdapter()
		} else if (result is NetworkResult.Error) {
			Log.e(TAG, "initCategoryDetail: ${result.error.message}")
			RetryDialog(requireContext(), this).show()
		}
	}

	private fun setAdapter() {
		binding.rvCategoryDetail.setHasFixedSize(true)
		binding.rvCategoryDetail.layoutManager = LinearLayoutManager(requireContext())

		try {
			binding.rvCategoryDetail.removeItemDecorationAt(0)
		} catch (_: Exception) {
		}

		binding.rvCategoryDetail.addItemDecoration(
			MarginItemDecoration(
				mHeight = requireContext().resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp),
				marginPosition = MarginItemDecoration.TOP,
				isShowOnFirstItem = false
			)
		)

		binding.rvCategoryDetail.adapter = categoryDetailAdapter
	}
}