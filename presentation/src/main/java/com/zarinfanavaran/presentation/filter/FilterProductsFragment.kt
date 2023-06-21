package com.zarinfanavaran.presentation.filter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.BuildConfig.SHARE_URL
import com.zarinfanavaran.domain.models.Meta
import com.zarinfanavaran.domain.models.MyResponse
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.models.SortOption
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.base.RetryDialog
import com.zarinfanavaran.presentation.databinding.FragmentFilterProductsBinding
import com.zarinfanavaran.presentation.databinding.TemplateDialogSortBinding
import com.zarinfanavaran.presentation.util.isEnd
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterProductsFragment : BaseFragment<FragmentFilterProductsBinding>(R.layout.fragment_filter_products) {

	@Inject
	lateinit var glide: RequestManager

	private lateinit var args: FilterProductsFragmentArgs

	private val viewModel: FilterProductsViewModel by viewModels()
	private val filterShareViewModel: FilterShareViewModel by activityViewModels()

	private val filterProductsAdapter = FilterProductsAdapter()

	private val brandIds = mutableListOf<Int>()
	private val params = HashMap<String, Any?>()
	private var filtersCount = 0

	private var sortDialog: MaterialDialog? = null
	private var mSortOption: SortOption? = null
	private val filterSortsAdapter = FilterSortsAdapter()
	private var meta: Meta? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		filterProductsAdapter.apply {
			recyclerViewTools = this@FilterProductsFragment
			glide = this@FilterProductsFragment.glide
		}

		filterSortsAdapter.apply {
			recyclerViewTools = this@FilterProductsFragment
		}

		requireActivity().intent?.extras?.also { bundle ->
			args = FilterProductsFragmentArgs.fromBundle(bundle)
		} ?: kotlin.run {
			args = FilterProductsFragmentArgs()
		}

		// set category ids in params
		args.category?.also { category ->
			params.put("category_ids[0]", category.id)
		} ?: kotlin.run {
			args.categoryDetail?.categoryIds?.forEachIndexed { categoryIndex, categoryId ->
				params.put("category_ids[$categoryIndex]", categoryId)
			}
		}

		args.brand?.also { brand -> brandIds.add(brand.id) }

		// save filters in viewModel
		args.categoryDetail?.filters?.also { filterShareViewModel.filters = it }
		filterShareViewModel.baseDialogFragmentCallback = this

		mSortOption = args.sortOption ?: kotlin.run {
			args.categoryDetail?.sortOptions?.find { sortOption ->
				sortOption.key == "MOST_RECENT"
			} ?: kotlin.run {
				args.categoryDetail?.sortOptions?.first()
			}
		}

		mSortOption?.also { sortOption ->
			params.put("sort", sortOption.key)
		}

		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun onDestroy() {
		super.onDestroy()
		filterShareViewModel.filters.forEach { filter ->
			filter.isSelected = false
			filter.options?.forEach { option ->
				option.isSelected = false
			}
		}
		filterShareViewModel.filterCount.set("0")
	}

	override fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int) {
		when (item) {
			//handle sorting
			is SortOption -> {
				args.categoryDetail?.sortOptions?.forEach { it.isSelected = false }
				item.isSelected = true
				mSortOption = item
				sortDialog?.dismiss()
				params.put("page", 1)
				params.put("sort", item.key)
				viewModel.fetchProducts(params)
				updateSortText()
			}

			is Product -> {
				Toast.makeText(requireContext(), item.nameFa, Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun onReachToEnd() {
		if (viewModel.isLoading().value == true) {
			return
		}
		meta?.also {
			if (it.currentPage < it.lastPage) {
				params.put("page", it.currentPage + 1)
				viewModel.fetchProducts(params)
			}
		}
	}

	override fun onFiltersChanged() {

		// clear all filters first
		val filterKeys = params.keys.filter { mapKey -> mapKey.startsWith("filter_") }
		filterKeys.forEach { mapKey -> params.remove(mapKey) }

		// filter from filters if available
		var index = 0
		filterShareViewModel.filters.filter { filter ->
			(filter.type == "BOOLEAN" && filter.isSelected) or (filter.type != "BOOLEAN" && filter.options?.find { option -> option.isSelected } != null)
		}.forEach { filter ->
			if (filter.type == "BOOLEAN") {
				params.put("filter_ids[$index][${filter.id}]", "HAS")
				index += 1
			} else {
				filter.options?.filter { option -> option.isSelected }?.forEach { option ->
					params.put("filter_ids[$index][${filter.id}]", option.id)
					index += 1
				}
			}
		}

		params.put("page", 1)
		viewModel.fetchProducts(params)

		updateFilterText()
	}

	override fun onRetry() {
		viewModel.fetchProducts(params)
	}

	override fun onCancel() {
		val page = params.get("page").toString().toInt()
		if (page == 1) {
			requireActivity().finish()
		}
	}

	private fun setupUI() {

		//set toolbar text
		binding.txtToolbarTitle.text = getToolbarSearchText()

		//set toolbar action
		binding.imgToolbarIcon.setOnClickListener { requireActivity().finish() }

		binding.rvProducts.isEnd(this)

		//default params
//		page: Int,
//		q: String = "",
//		categoryIds: List<String>,
//		brandIds: List<String> = listOf(),
//		colorIds: List<String> = listOf(),
//		filterIds: Map<String, String>,
//		inStock: Int? = null,
//		fromPrice: String,
//		toPrice: String,
//		sort: String
		params.put("page", 1)

		updateFilterText()
		updateSortText()

		//sort
		binding.btnSort.setOnClickListener { showSortDialog() }

		//share
		binding.btnShare.setOnClickListener {
			var shareBody = "جستجو در لومکس شاپ\n"
			args.category?.also {
				shareBody += "${SHARE_URL}category/${it.name}"
			} ?: kotlin.run {
				args.categoryDetail?.category?.also {
					shareBody += "${SHARE_URL}category/${it.name}"
				}
			}

			shareText(shareBody, getString(R.string.share_with))
		}

		//filters
		binding.btnFilters.setOnClickListener {
			if (filterShareViewModel.filters.isNotEmpty()) {
				val action = FilterProductsFragmentDirections.actionProductsToFilterFiltersDialogFragment()
				findNavController().navigate(action)
			} else {
				Toast.makeText(requireContext(), "No filter founds", Toast.LENGTH_SHORT).show()
			}
		}

		setAdapter()
		if (filterProductsAdapter.mItems.isEmpty()) {
			viewModel.fetchProducts(params)
		}
	}

	private fun updateFilterText() {
		filtersCount = 0

		//set brand ids to params
		if (brandIds.isNotEmpty()) {
			brandIds.forEachIndexed { brandIndex, brandId -> params.put("brand_ids[$brandIndex]", brandId) }
		}

		filtersCount = filterShareViewModel.filters.count { filter ->
			(filter.type == "BOOLEAN" && filter.isSelected) or (filter.type != "BOOLEAN" && filter.options?.find { option -> option.isSelected } != null)
		}

		val btnFilterText = if (filtersCount > 0) {
			getString(R.string.filters_with_count, filtersCount.toString())
		} else {
			getString(R.string.filters)
		}
		binding.btnFilters.text = btnFilterText
	}

	private fun updateSortText() {
		mSortOption?.also { sortOption ->
			binding.btnSort.text = sortOption.name
		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getProducts(), ::initProducts)

			filterShareViewModel._isLoading = isLoading()
		}
	}

	private fun initLoading(isLoading: Boolean) {
		val page = params.get("page").toString().toInt()
		if (page > 1) {
			if (isLoading && filterProductsAdapter.mItems.last().id > 0) {
				filterProductsAdapter.mItems.add(Product(id = -1))
				filterProductsAdapter.notifyItemInserted(filterProductsAdapter.mItems.size - 1)
				binding.rvProducts.scrollToPosition(filterProductsAdapter.mItems.size - 1)
			}
		} else {
			setProgressView(binding.clMain, isLoading)
		}
	}

	private fun initProducts(result: NetworkResult<MyResponse<List<Product>, Meta>>) {
		val page = params.get("page").toString().toInt()
		if (result is NetworkResult.Success) {
			val products = result.data.data.toMutableList()
			if (page == 1) {
				val allItems = filterProductsAdapter.mItems.size
				if (allItems > 0) {
					filterProductsAdapter.mItems.clear()
					filterProductsAdapter.notifyItemRangeRemoved(0, allItems)
				}
				filterProductsAdapter.mItems = products
				filterProductsAdapter.notifyItemRangeInserted(0, products.size)
			} else if (page > 1) {
				if (filterProductsAdapter.mItems.last().id == -1) {
					filterProductsAdapter.mItems.removeLast()
					filterProductsAdapter.notifyItemRemoved(filterProductsAdapter.mItems.size)
				}
				val startPosition = filterProductsAdapter.mItems.size
				filterProductsAdapter.mItems.addAll(products)
				filterProductsAdapter.notifyItemRangeInserted(startPosition, products.size)
			}

			//set meta for pagination
			meta = result.data.meta

			meta?.also {
				filterShareViewModel.filterCount.set(it.total.toString())
			}
		} else if (result is NetworkResult.Error) {
			if (page > 1) {
				if (filterProductsAdapter.mItems.last().id == -1) {
					filterProductsAdapter.mItems.removeLast()
					filterProductsAdapter.notifyItemRemoved(filterProductsAdapter.mItems.size)
				}
			}
			Log.e(TAG, "initProducts: ${result.error.message}")
			RetryDialog(requireContext(), this).show()
		}
	}

	private fun setAdapter() {
		binding.rvProducts.setHasFixedSize(true)
		binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())

		try {
			binding.rvProducts.removeItemDecorationAt(0)
		} catch (_: Exception) {
		}

		binding.rvProducts.addItemDecoration(
			MarginItemDecoration(
				resources.getDimension(com.intuit.sdp.R.dimen._12sdp).toInt(),
				MarginItemDecoration.TOP
			)
		)
		binding.rvProducts.adapter = filterProductsAdapter
	}

	private fun showSortDialog() {
		if (filterSortsAdapter.mItems.isEmpty()) {
			args.categoryDetail?.sortOptions?.also { sortOptions ->
				filterSortsAdapter.mItems.addAll(sortOptions)
			}
		}

		val templateDialogSortBinding = TemplateDialogSortBinding.inflate(layoutInflater)
		sortDialog = MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT))
				.show {
					cornerRadius(0f)
					customView(view = templateDialogSortBinding.root)

					onShow {
						this.getCustomView().setPadding(
							resources.getDimension(com.intuit.sdp.R.dimen._8sdp).toInt(),
							resources.getDimension(com.intuit.sdp.R.dimen._4sdp).toInt(),
							resources.getDimension(com.intuit.sdp.R.dimen._8sdp).toInt(),
							resources.getDimension(com.intuit.sdp.R.dimen._4sdp).toInt()
						)

						templateDialogSortBinding.rvSortOptions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
						templateDialogSortBinding.rvSortOptions.setHasFixedSize(false)
						templateDialogSortBinding.rvSortOptions.addItemDecoration(
							MarginItemDecoration(
								resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._2sdp),
								MarginItemDecoration.TOP, false
							)
						)
						templateDialogSortBinding.rvSortOptions.adapter = filterSortsAdapter
					}
				}
	}
}