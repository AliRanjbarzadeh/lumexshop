package com.zarinfanavaran.presentation.categories

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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

		binding.txtToolbarTitle.text = getToolbarSearchText()
	}

	override fun <T> onItemClick(position: Int, view: View, item: T) {
		item as Category

		when (view.id) {
			R.id.btnShowAll -> {
				Toast.makeText(requireContext(), "Go to show all page", Toast.LENGTH_SHORT).show()
			}

			else -> {
				when (item.isHasMore) {
					true -> {
						Toast.makeText(requireContext(), "Go to show all page", Toast.LENGTH_SHORT).show()
					}

					false -> {
						findNavController().navigate(R.id.categoryDetailFragment)
					}
				}
			}
		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(getCategories(), ::initCategories)
		}
	}

	private fun initCategories(result: NetworkResult<List<Category>>) {
		if (result is NetworkResult.Success) {
			setAdapter(result.data)
		} else if (result is NetworkResult.Error) {
			//TODO: try again
			Toast.makeText(requireContext(), "${result.error.message}", Toast.LENGTH_SHORT).show()
		}
	}

	private fun setAdapter(categories: List<Category>) {
		Log.d(TAG, "setAdapter: ${categories}")
		if (categoriesAdapter.mItems.isEmpty()) {
			categoriesAdapter.mItems = categories.toMutableList()
		}

		Log.d(TAG, "setAdapter: ${categoriesAdapter.mItems}")

		binding.rvCategories.setHasFixedSize(true)
		binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
		binding.rvCategories.adapter = categoriesAdapter
	}
}