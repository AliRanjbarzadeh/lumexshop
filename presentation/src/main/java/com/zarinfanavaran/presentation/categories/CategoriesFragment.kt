package com.zarinfanavaran.presentation.categories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentCategoriesBinding
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */

@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>(R.layout.fragment_categories) {

	@Inject
	lateinit var glide: RequestManager

	private val viewModel: CategoriesViewModel by viewModels()

	private val categoriesAdapter = CategoriesAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()

		categoriesAdapter.apply {
			recyclerViewTools = this@CategoriesFragment
			glide = this@CategoriesFragment.glide
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupUI()
	}

	fun setupUI() {
		binding.txtToolbarTitle.text = getToolbarSearchText()

		if (categoriesAdapter.mItems.isNotEmpty()) // check if already categories fetched
			setAdapter()
		else //get categories
			viewModel.fetchCategories()

	}

	override fun <T> onItemClick(position: Int, view: View, item: T) {
		item as Category

		val action = CategoriesFragmentDirections.actionCategoriesFragmentToCategoryDetailFragment(item.id)

		when (view.id) {
			R.id.btnShowAll -> {
				findNavController().navigate(action)
			}

			else -> {
				when (item.isHasMore) {
					true -> {
						findNavController().navigate(action)
					}

					false -> {
						findNavController().navigate(action)
					}
				}
			}
		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getCategories(), ::initCategories)
		}
	}

	private fun initLoading(isLoading: Boolean) {
		setProgressView(binding.clMain, isLoading)
	}

	private fun initCategories(result: NetworkResult<List<Category>>) {
		if (result is NetworkResult.Success) {
			if (categoriesAdapter.mItems.isNotEmpty()) {
				categoriesAdapter.mItems.clear()
			}
			categoriesAdapter.mItems = result.data.toMutableList()
			categoriesAdapter.mItems.forEach { category ->
				category.children?.add(Category(category.id, category.name, category.level, category.media, null).apply {
					isHasMore = true
				})
			}
			setAdapter()
		} else if (result is NetworkResult.Error) {
			//TODO: try again
			Toast.makeText(requireContext(), "${result.error.message}", Toast.LENGTH_SHORT).show()
		}
	}

	private fun setAdapter() {
		binding.rvCategories.setHasFixedSize(true)
		binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
		binding.rvCategories.adapter = categoriesAdapter
	}
}