package com.zarinfanavaran.presentation.profile.address

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.zarinfanavaran.domain.extensions.toEnglish
import com.zarinfanavaran.domain.models.City
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseDialogFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.base.RetryDialog
import com.zarinfanavaran.presentation.databinding.FragmentCitiesDialogBinding
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesDialogFragment : BaseDialogFragment<FragmentCitiesDialogBinding>(R.layout.fragment_cities_dialog) {

	private val viewModel: CitiesDialogViewModel by viewModels()
	private val args: CitiesDialogFragmentArgs by navArgs()

	private val citiesAdapter = CitiesAdapter()
	private val cities = mutableListOf<City>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		citiesAdapter.also {
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
		viewModel.cities(args.provinceId)
	}

	override fun onCancel() {
		dismiss()
	}

	override fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int) {
		item as City

		val bundle = Bundle()
		bundle.putParcelable("city", item)
		setFragmentResult("city", bundle)

		back()

//		val action = CitiesDialogFragmentDirections.actionCitiesDialogFragmentToAddressDetailFragment(args.address)
//		findNavController().navigate(action)
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun setupUI() {
		binding.btnClose.setOnClickListener { dismiss() }

		viewModel.cities(args.provinceId)

		binding.etInnerSearch.doAfterTextChanged { text ->
			val word = text.toString().trim().toEnglish()

			val filteredCities = if (word.isNotEmpty()) {
				cities.filter { city: City -> city.name.equals(word) or city.name.contains(word) }.toMutableList()
			} else {
				cities
			}

			citiesAdapter.mItems = filteredCities
			citiesAdapter.notifyDataSetChanged()
		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getCities(), ::initCities)
		}
	}

	private fun initLoading(isLoading: Boolean) {
		setProgressView(binding.clMain, isLoading)
	}

	private fun initCities(result: NetworkResult<List<City>>) {
		if (result is NetworkResult.Success) {
			if (citiesAdapter.mItems.isNotEmpty()) {
				citiesAdapter.mItems.clear()
			}

			citiesAdapter.mItems = result.data.toMutableList()
			cities.addAll(result.data)
			setAdapter()
		} else if (result is NetworkResult.Error) {
			RetryDialog(requireContext(), this, false).show()
		}
	}

	private fun setAdapter() {
		try {
			binding.rvCities.removeItemDecorationAt(0)
		} catch (_: Exception) {
		} finally {
			binding.rvCities.addItemDecoration(
				MarginItemDecoration(
					resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp),
					MarginItemDecoration.TOP
				)
			)
		}

		binding.rvCities.setHasFixedSize(true)
		binding.rvCities.layoutManager = LinearLayoutManager(requireContext())
		binding.rvCities.adapter = citiesAdapter
	}
}