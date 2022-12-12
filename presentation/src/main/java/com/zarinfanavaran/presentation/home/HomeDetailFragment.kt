package com.zarinfanavaran.presentation.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentHomeDetailBinding

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class HomeDetailFragment : BaseFragment<FragmentHomeDetailBinding>(R.layout.fragment_home_detail) {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.txtDetail2.setOnClickListener {
			findNavController().navigate(R.id.homeDetail2Fragment)
		}
	}
}