package com.zarinfanavaran.presentation.shopcart.address

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.button.MaterialButton
import com.zarinfanavaran.domain.models.Receiver
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.databinding.FragmentShopcartAddressBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */

@AndroidEntryPoint
class ShopCartAddressFragment : BaseFragment<FragmentShopcartAddressBinding>(R.layout.fragment_shopcart_address) {

	private val viewModel: ShopCartAddressViewModel by viewModels()

	private val shopCartReceiverAdapter = ShopCartReceiverAdapter()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		backgroundResColor = R.color.white
		baseFragmentCallback?.bottomNavigationVisibility(false)
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.viewModel = viewModel

		binding.imgToolbarIcon.setOnClickListener { back() }

		//set callback for adapter items clicks
		shopCartReceiverAdapter.recyclerViewTools = this

		binding.rvReceivers.setHasFixedSize(false)
		binding.rvReceivers.layoutManager = LinearLayoutManager(requireContext())

		try {
			if (binding.rvReceivers.itemDecorationCount > 0)
				binding.rvReceivers.removeItemDecorationAt(0)
		} catch (_: IndexOutOfBoundsException) {
		}

		setAdapter()

		binding.rvReceivers.addItemDecoration(MarginItemDecoration(resources.getDimension(com.intuit.sdp.R.dimen._12sdp).toInt(), MarginItemDecoration.TOP, false))

		binding.cbReceiver.setOnCheckedChangeListener { _, isChecked ->
			viewModel.isIAmReceiver.set(isChecked)
			if (isChecked) {
				binding.txtReceiverInfo.iconTint = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
				viewModel.receiver.apply {
					firstName = "شرلوک"
					lastName = "هولمز"
					mobile = "09123456789"
					personalCode = "2281495682"
				}
			} else {
				viewModel.clearReceiver()
				binding.txtReceiverInfo.iconTint = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color18))
			}
		}

		binding.btnNext.setOnClickListener { findNavController().navigate(R.id.shopCartPaymentFragment) }
	}

	override fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int) {
		hideKeyboard()
	}

	override fun <T> onDeleteClick(position: Int, view: View, item: T, parentPosition: Int) {
		hideKeyboard()
		MaterialDialog(requireContext()).show {
			cornerRadius(0f)
			customView(R.layout.template_dialog_simple)

			onShow {
				this.findViewById<ConstraintLayout>(R.id.cvMainDialog).setPadding(
					resources.getDimension(com.intuit.sdp.R.dimen._20sdp).toInt(),
					resources.getDimension(com.intuit.sdp.R.dimen._10sdp).toInt(),
					resources.getDimension(com.intuit.sdp.R.dimen._20sdp).toInt(),
					resources.getDimension(com.intuit.sdp.R.dimen._10sdp).toInt()
				)
			}

			this.view.findViewById<MaterialButton>(R.id.btnClose).setOnClickListener {
				this.dismiss()
			}

			this.view.findViewById<MaterialButton>(R.id.btnAction).setOnClickListener {
				shopCartReceiverAdapter.mItems.removeAt(position)
				shopCartReceiverAdapter.notifyItemRemoved(position)
				this.dismiss()
			}
		}
	}

	override fun keyboardState(isShow: Boolean) {
		if (!isShow) {
			binding.etFirstName.clearFocus()
			binding.etLastName.clearFocus()
			binding.etMobile.clearFocus()
			binding.etPersonalCode.clearFocus()
		}
	}

	private fun setAdapter() {
		if (shopCartReceiverAdapter.mItems.isEmpty()) {
			val receiver = Receiver(
				_firstName = getString(R.string.sample_first_name),
				_lastName = getString(R.string.sample_last_name),
				_mobile = getString(R.string.sample_mobile),
				_personalCode = getString(R.string.sample_personal_code),
			)
			shopCartReceiverAdapter.mItems.add(receiver)
			shopCartReceiverAdapter.mItems.add(receiver)
			shopCartReceiverAdapter.mItems.add(receiver)
		}

		binding.rvReceivers.adapter = shopCartReceiverAdapter
	}
}