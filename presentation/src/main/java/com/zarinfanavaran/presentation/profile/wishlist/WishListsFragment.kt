package com.zarinfanavaran.presentation.profile.wishlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.gson.JsonElement
import com.zarinfanavaran.domain.models.*
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.base.RetryDialog
import com.zarinfanavaran.presentation.databinding.FragmentWishlistsBinding
import com.zarinfanavaran.presentation.util.isEnd
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WishListsFragment : BaseFragment<FragmentWishlistsBinding>(R.layout.fragment_wishlists) {

	private val viewModel: WishListViewModel by viewModels()

	@Inject
	lateinit var glide: RequestManager

	private val params = HashMap<String, Any?>()
	private var meta: Meta? = null
	private var deletePosition: Int = -1
	private val wishListsAdapter = WishListsAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		wishListsAdapter.also {
			it.glide = glide
			it.recyclerViewTools = this
		}

		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun onRetry() {
		if (deletePosition >= 0) {
			viewModel.deleteWishList(wishListsAdapter.mItems[deletePosition].product.id)
		} else {
			viewModel.fetchWishLists(params)
		}
	}

	override fun onReachToEnd() {
		if (viewModel.isLoading().value == true) {
			return
		}
		meta?.also {
			if (it.currentPage < it.lastPage) {
				params.put("page", it.currentPage + 1)
//				viewModel.fetchWishLists(params)
				val startPosition = wishListsAdapter.mItems.size
				for (i in 1..10) {
					wishListsAdapter.mItems.add(
						WishList(
							id = i,
							Product(
								id = 0,
								nameFa = getString(R.string.sample_product_title),
								price = 62500000,
								pricePrettified = "62,500,000",
								discountAmount = 2500000,
								discounted = 60000000,
								discountInPercent = 23f,
								discountInAmount = 2500000,
								discountedPrettified = "60,000,000",
								items = listOf(
									Product.Item(
										id = 0,
										color = Color(
											id = 0,
											nameFa = "آبی",
											nameEn = "Blue",
											media = Media(
												icon = Media.MediaChild(
													id = 0,
													file = "https://zarinkala.com/storage/رنگ_ها/1655275054vxw92-blue.png"
												)
											)
										)
									),
									Product.Item(
										id = 0,
										color = Color(
											id = 0,
											nameFa = "قرمز",
											nameEn = "Red",
											media = Media(
												icon = Media.MediaChild(
													id = 0,
													file = "https://zarinkala.com/storage/رنگ_ها/1655275054vxw91-red.png"
												)
											)
										)
									)
								)
							)
						)
					)
				}
				wishListsAdapter.notifyItemRangeInserted(startPosition, 10)
			}
		}
	}

	override fun onCancel() {
		deletePosition = -1
	}

	private fun setupUI() {
		params.put("page", 1)
		params.put("per_page", 20)
//		viewModel.fetchWishLists(params)

		setAdapter()
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getWishLists(), ::initWishLists)
			observe(removeWishList(), ::initDeleteWishList)
		}
	}

	private fun initLoading(isLoading: Boolean) {
		val page = params.get("page").toString().toInt()
		if (page > 1) {
			if (isLoading && wishListsAdapter.mItems.last().id > 0) {
				wishListsAdapter.mItems.add(WishList(id = -1, Product(id = 0)))
				wishListsAdapter.notifyItemInserted(wishListsAdapter.mItems.size - 1)
				binding.rvWishLists.scrollToPosition(wishListsAdapter.mItems.size - 1)
			}
		} else {
			setProgressView(binding.clMain, isLoading)
		}
	}

	private fun initWishLists(result: NetworkResult<MyResponse<List<WishList>, Meta>>) {
		val page = params.get("page").toString().toInt()
		if (result is NetworkResult.Success) {
			val wishLists = result.data.data.toMutableList()
			if (page == 1) {
				val allItems = wishListsAdapter.mItems.size
				if (allItems > 0) {
					wishListsAdapter.mItems.clear()
					wishListsAdapter.notifyItemRangeRemoved(0, allItems)
				}
				wishListsAdapter.mItems = wishLists
				wishListsAdapter.notifyItemRangeInserted(0, wishLists.size)
			} else if (page > 1) {
				if (wishListsAdapter.mItems.last().id == -1) {
					wishListsAdapter.mItems.removeLast()
					wishListsAdapter.notifyItemRemoved(wishListsAdapter.mItems.size)
				}
				val startPosition = wishListsAdapter.mItems.size
				wishListsAdapter.mItems.addAll(wishLists)
				wishListsAdapter.notifyItemRangeInserted(startPosition, wishLists.size)
			}

			//set meta for pagination
			meta = result.data.meta
		} else if (result is NetworkResult.Error) {
			if (page > 1) {
				if (wishListsAdapter.mItems.last().id == -1) {
					wishListsAdapter.mItems.removeLast()
					wishListsAdapter.notifyItemRemoved(wishListsAdapter.mItems.size)
				}
			}
			RetryDialog(requireContext(), this).show()
		}
	}

	private fun initDeleteWishList(result: NetworkResult<JsonElement>) {
		if (result is NetworkResult.Success) {
			wishListsAdapter.mItems.removeAt(deletePosition)
			wishListsAdapter.notifyItemRemoved(deletePosition)
			deletePosition = -1
		} else if (result is NetworkResult.Error) {
			RetryDialog(requireContext(), this).show()
		}
	}

	private fun setAdapter() {
		binding.rvWishLists.isEnd(this)
		binding.rvWishLists.setHasFixedSize(true)
		binding.rvWishLists.layoutManager = LinearLayoutManager(requireContext())

		if (wishListsAdapter.mItems.isEmpty()) {
			for (i in 1..10) {
				wishListsAdapter.mItems.add(
					WishList(
						id = i,
						Product(
							id = 0,
							nameFa = getString(R.string.sample_product_title),
							price = 62500000,
							pricePrettified = "62,500,000",
							discountAmount = 2500000,
							discounted = 60000000,
							discountInPercent = 23f,
							discountInAmount = 2500000,
							discountedPrettified = "60,000,000",
							items = listOf(
								Product.Item(
									id = 0,
									color = Color(
										id = 0,
										nameFa = "آبی",
										nameEn = "Blue",
										media = Media(
											icon = Media.MediaChild(
												id = 0,
												file = "https://zarinkala.com/storage/رنگ_ها/1655275054vxw92-blue.png"
											)
										)
									)
								),
								Product.Item(
									id = 0,
									color = Color(
										id = 0,
										nameFa = "قرمز",
										nameEn = "Red",
										media = Media(
											icon = Media.MediaChild(
												id = 0,
												file = "https://zarinkala.com/storage/رنگ_ها/1655275054vxw91-red.png"
											)
										)
									)
								)
							)
						)
					)
				)
			}
		}

		try {
			binding.rvWishLists.removeItemDecorationAt(0)
		} catch (_: Exception) {
		}

		binding.rvWishLists.addItemDecoration(
			MarginItemDecoration(
				resources.getDimension(com.intuit.sdp.R.dimen._12sdp).toInt(),
				MarginItemDecoration.TOP
			)
		)
		binding.rvWishLists.adapter = wishListsAdapter
	}
}