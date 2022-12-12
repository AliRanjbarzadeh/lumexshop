package com.zarinfanavaran.presentation.categories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentCategoriesBinding

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>(R.layout.fragment_categories) {

	private lateinit var categoriesAdapter: CategoriesAdapter

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.txtToolbarTitle.text = getToolbarSearchText()

		setAdapter()
	}

	private val categoriesRecyclerViewTools = object : RecyclerViewTools {
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
	}

	private fun setAdapter() {
		if (!this::categoriesAdapter.isInitialized) {
			categoriesAdapter = CategoriesAdapter(categoriesRecyclerViewTools)

			val categories = mutableListOf<Category>()
			val subItems = mutableListOf<Category>()

			val category = Category(
				title = "لوازم الکترونیکی",
				icon = R.drawable.temp_icon_mobile2,
				image = R.drawable.temp_icon_mobile
			)

			subItems.add(
				Category(
					icon = R.drawable.temp_icon_mobile2,
					image = R.drawable.temp_category,
					title = "گوشی موبایل"
				)
			)

			subItems.add(
				Category(
					icon = R.drawable.temp_icon_mobile2,
					image = R.drawable.temp_category2,
					title = "لپ تاپ"
				)
			)

			subItems.add(
				Category(
					icon = R.drawable.temp_icon_mobile2,
					image = R.drawable.temp_category3,
					title = "دوربین عکاسی"
				)
			)

			subItems.add(
				Category(
					icon = R.drawable.temp_icon_mobile2,
					image = R.drawable.temp_category3,
					title = "دوربین عکاسی"
				).apply {
					isHasMore = true
				}
			)

			category.subItems = subItems

			categories.add(category)
			categories.add(category)
			categories.add(category)
			categories.add(category)
			categories.add(category)
			categories.add(category)
			categories.add(category)
			categories.add(category)
			categories.add(category)
			categories.add(category)
			categories.add(category)

			categoriesAdapter.mItems = categories
		}

		binding.rvCategories.setHasFixedSize(true)
		binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
		binding.rvCategories.adapter = categoriesAdapter
	}
}