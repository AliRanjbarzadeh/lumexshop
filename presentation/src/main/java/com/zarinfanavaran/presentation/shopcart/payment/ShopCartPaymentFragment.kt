package com.zarinfanavaran.presentation.shopcart.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zarinfanavaran.domain.extensions.priceFormat
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentShopcartPaymentBinding

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class ShopCartPaymentFragment : BaseFragment<FragmentShopcartPaymentBinding>(R.layout.fragment_shopcart_payment) {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		baseFragmentCallback?.bottomNavigationVisibility(false)
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.imgToolbarIcon.setOnClickListener { back() }

		binding.txtWallet.text = spannableString(
			mContext = requireContext(),
			firstString = "موجودی:   ",
			firstFont = getString(R.string.font_light),
			secondString = 30000.priceFormat(),
			secondFont = getString(R.string.font_semi_bold)
		)

		binding.txtPrice.text = spannableString(
			mContext = requireContext(),

			firstString = 13900000.priceFormat(""),
			firstFont = getString(R.string.font_medium),
			firstSize = com.intuit.ssp.R.dimen._12ssp,

			secondString = "  تومان",
			secondFont = getString(R.string.font_light),
			secondSize = com.intuit.ssp.R.dimen._11ssp,
		)

		binding.txtDiscount.text = spannableString(
			mContext = requireContext(),

			firstString = 900000.priceFormat(""),
			firstFont = getString(R.string.font_medium),
			firstSize = com.intuit.ssp.R.dimen._12ssp,

			secondString = "  تومان",
			secondFont = getString(R.string.font_light),
			secondSize = com.intuit.ssp.R.dimen._11ssp,
		)

		binding.txtDeliveryPrice.text = spannableString(
			mContext = requireContext(),

			firstString = 300000.priceFormat(""),
			firstFont = getString(R.string.font_medium),
			firstSize = com.intuit.ssp.R.dimen._12ssp,

			secondString = "  تومان",
			secondFont = getString(R.string.font_light),
			secondSize = com.intuit.ssp.R.dimen._11ssp,
		)
	}

	override fun keyboardState(isShow: Boolean) {
		if (!isShow) {
			binding.etDiscount.clearFocus()
		}
	}
}