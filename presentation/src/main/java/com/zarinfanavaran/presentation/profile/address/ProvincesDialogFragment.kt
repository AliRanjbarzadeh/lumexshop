package com.zarinfanavaran.presentation.profile.address

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zarinfanavaran.domain.extensions.toEnglish
import com.zarinfanavaran.domain.models.Province
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseDialogFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.base.RetryDialog
import com.zarinfanavaran.presentation.databinding.FragmentProvincesDialogBinding
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProvincesDialogFragment : BaseDialogFragment<FragmentProvincesDialogBinding>(R.layout.fragment_provinces_dialog) {

	private val viewModel: ProvincesDialogViewModel by viewModels()
	private val provinces = mutableListOf<Province>()

	private val provincesAdapter = ProvincesAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		provincesAdapter.also {
			it.recyclerViewTools = this
		}

		setStyle(STYLE_NORMAL, R.style.NormalScreenDialog)

		setupObservers()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		backgroundResColor = R.color.white
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun onRetry() {
		viewModel.provinces()
	}

	override fun onCancel() {
		dismiss()
	}

	override fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int) {
		item as Province

		val bundle = Bundle()
		bundle.putParcelable("province", item)
		setFragmentResult("province", bundle)
		back()
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun setupUI() {
		binding.btnClose.setOnClickListener { dismiss() }

		binding.etInnerSearch.doAfterTextChanged { text ->
			val word = text.toString().trim().toEnglish()

			val filteredProvinces = if (word.isNotEmpty()) {
				provinces.filter { province: Province -> province.name.equals(word) or province.name.contains(word) }.toMutableList()
			} else {
				provinces
			}

			provincesAdapter.mItems = filteredProvinces
			provincesAdapter.notifyDataSetChanged()

		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getProvinces(), ::initProvinces)
		}
	}

	private fun initLoading(isLoading: Boolean) {
		setProgressView(binding.clMain, isLoading)
	}

	private fun initProvinces(result: NetworkResult<List<Province>>) {
		if (result is NetworkResult.Success) {
			if (provincesAdapter.mItems.isNotEmpty()) {
				provincesAdapter.mItems.clear()
			}

			provincesAdapter.mItems = result.data.toMutableList()
			provinces.addAll(result.data)
			setAdapter()
		} else if (result is NetworkResult.Error) {
			RetryDialog(requireContext(), this, false).show()
		}
	}

	private fun setAdapter() {
		try {
			binding.rvProvinces.removeItemDecorationAt(0)
		} catch (_: Exception) {
		} finally {
			binding.rvProvinces.addItemDecoration(
				MarginItemDecoration(
					resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp),
					MarginItemDecoration.TOP
				)
			)
		}

		binding.rvProvinces.setHasFixedSize(true)
		binding.rvProvinces.layoutManager = LinearLayoutManager(requireContext())
		binding.rvProvinces.adapter = provincesAdapter
	}
}