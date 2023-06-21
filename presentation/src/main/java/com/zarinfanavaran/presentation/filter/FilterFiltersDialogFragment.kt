package com.zarinfanavaran.presentation.filter

import android.animation.ValueAnimator
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.zarinfanavaran.domain.extensions.toEnglish
import com.zarinfanavaran.domain.models.Filter
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseDialogFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.databinding.FragmentDialogFilterFiltersBinding
import com.zarinfanavaran.presentation.util.observe
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

		setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.filterViewModel = filterShareViewModel

		filterShareViewModel.filters.forEach { filter ->
			if (filter.hasOption == 1) {
				filter.isSelected = false
			}
		}

		setupUI()
	}

	override fun keyboardState(isShow: Boolean) {
		if (!isShow) {
			filterFiltersAdapter.mItems.forEachIndexed { index, filter ->
				if (listOf("MULTIPLE_OPTION", "SINGLE_OPTION").contains(filter.type)) {
					binding.rvFilters.findViewHolderForAdapterPosition(index)?.itemView
							?.findViewById<TextInputEditText>(R.id.etInnerSearch)?.clearFocus()
				}
			}
		}
	}

	override fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int) {
		item as Filter

		item.isSelected = !item.isSelected


		when (item.type) {
			"MULTIPLE_OPTION", "SINGLE_OPTION" -> {
				if (item.isSelected) {
					view.findViewById<MaterialCardView>(R.id.cvExpandable)?.strokeWidth = resources.getDimension(com.intuit.sdp.R.dimen._1sdp).toInt()
				} else {
					view.findViewById<MaterialCardView>(R.id.cvExpandable)?.strokeWidth = 0
				}

				view.findViewById<AppCompatImageView>(R.id.imgFilterArrow)?.also { arrowImage ->
					val currentRotation = arrowImage.rotation
					val remainRotation = 180f - Math.abs(currentRotation)
					val rotation = if (item.isSelected) {
						180f - currentRotation
					} else {
						-180f + remainRotation
					}
					arrowImage.animate().rotationBy(rotation).setDuration(400).setInterpolator(LinearInterpolator()).start()
				}

				val optionsCount = item.options?.count() ?: 0
				val expandedHeight = if (optionsCount > 0) {
					(optionsCount * resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._24sdp)) +
							((optionsCount - 1) * resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp)) +
							resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._51sdp)
				} else {
					0
				}
				val targetHeight = if (item.isSelected) expandedHeight else 0

				view.findViewById<ConstraintLayout>(R.id.clExpandable)?.also { clExpandable ->
					val currentHeight = clExpandable.measuredHeight
					val anim = ValueAnimator.ofInt(currentHeight, targetHeight)
					anim.setInterpolator(AccelerateDecelerateInterpolator())
					anim.setDuration(400)
					anim.addUpdateListener { animation ->
						val clParams = clExpandable.layoutParams
						clParams.height = animation.animatedValue as Int
						clExpandable.layoutParams = clParams
					}
					anim.start()
				}
			}

			"BOOLEAN" -> {
				filterShareViewModel.baseDialogFragmentCallback.onFiltersChanged()
			}
		}
	}

	override fun <T> onItemSelect(position: Int, view: View, item: T, parentPosition: Int) {
		item as Filter.Option

		item.isSelected = !item.isSelected

		filterShareViewModel.baseDialogFragmentCallback.onFiltersChanged()
	}

	override fun <T> onFilter(position: Int, item: T, search: String, parentPosition: Int) {
		item as Filter
		val word = search.toEnglish().lowercase()
		val filteredOptions = if (word.isNotEmpty()) {
			item.options?.filter { option ->
				val nameFa = option.nameFa.toEnglish().lowercase()
				val nameEn = option.nameEn.toEnglish().lowercase()

				nameFa.equals(word) or nameEn.equals(word) or nameFa.contains(word) or nameEn.contains(word)
			}
		} else {
			item.options
		}
		filterFiltersAdapter.mAdapters[position]?.apply {
			mItems.clear()
			filteredOptions?.also { options ->
				mItems.addAll(options)
			}
			notifyDataSetChanged()
		}
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

	private fun setupObservers() {
		filterShareViewModel.run {
			observe(isLoading(), ::initLoading)
		}
	}

	private fun initLoading(isLoading: Boolean) {
		setProgressView(binding.llBottom, isLoading, true, R.color.white)
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

		binding.rvFilters.setItemViewCacheSize(filterShareViewModel.filters.size)
		binding.rvFilters.setHasFixedSize(false)
		binding.rvFilters.layoutManager = LinearLayoutManager(requireContext())
		binding.rvFilters.adapter = filterFiltersAdapter
	}
}