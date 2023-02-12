package com.zarinfanavaran.presentation.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseDialogFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.databinding.FragmentDialogFilterFiltersBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ali Ranjbarzadeh on 2023/01/28.
 */

@AndroidEntryPoint
class FilterFiltersDialogFragment : BaseDialogFragment<FragmentDialogFilterFiltersBinding>(R.layout.fragment_dialog_filter_filters) {

	@Inject
	lateinit var glide: RequestManager

	private val filterShareViewModel: FilterShareViewModel by activityViewModels()
	private val filterFiltersAdapter = FilterFiltersAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		filterFiltersAdapter.also {
			it.recyclerViewTools = this
			it.glide = glide
		}

		setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.filterViewModel = filterShareViewModel

		setupUI()
	}

	private fun setupUI() {
		binding.btnToolbarAction.setOnClickListener {
			filterShareViewModel.filters.forEach { filter ->
				filter.isSelected = false
				filter.options?.forEach { option ->
					option.isSelected = false
				}
			}
			filterShareViewModel.baseDialogFragmentCallback.onFiltersChanged()
		}
		binding.imgToolbarIcon.setOnClickListener { dismissNow() }


		setAdapter()
	}

	private fun setAdapter() {
		if (filterFiltersAdapter.mItems.isEmpty()) {
			filterFiltersAdapter.mItems.addAll(filterShareViewModel.filters)
		}

		try {
			binding.rvFilters.removeItemDecorationAt(0)
		} catch (_: Exception) {
		} finally {
			binding.rvFilters.addItemDecoration(
				MarginItemDecoration(
					resources.getDimension(com.intuit.sdp.R.dimen._8sdp).toInt(),
					MarginItemDecoration.TOP
				)
			)
		}

		binding.rvFilters.setHasFixedSize(false)
		binding.rvFilters.layoutManager = LinearLayoutManager(requireContext())
		binding.rvFilters.adapter = filterFiltersAdapter
	}
}