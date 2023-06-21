package com.zarinfanavaran.presentation.profile.address

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.databinding.FragmentAddressesBinding
import com.zarinfanavaran.presentation.databinding.TemplateDialogSimpleBinding
import com.zarinfanavaran.presentation.databinding.TemplateEditDeleteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesFragment : BaseFragment<FragmentAddressesBinding>(R.layout.fragment_addresses) {

	private val addressesAdapter = AddressesAdapter()
	private val args: AddressesFragmentArgs by navArgs()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		baseFragmentCallback?.bottomNavigationVisibility(false)

		addressesAdapter.also {
			it.recyclerViewTools = this
		}

		setFragmentResultListener("address") { requestKey: String, bundle: Bundle ->
			val address = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				bundle.getParcelable("address", Address::class.java)
			} else {
				bundle.getParcelable("address")
			}

			address?.also {
				addressesAdapter.mItems.add(0, it)
				addressesAdapter.notifyItemInserted(0)
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int) {
		item as Address

		mDialog?.dismiss()
		val editDeleteBinding = TemplateEditDeleteBinding.inflate(layoutInflater)
		editDeleteBinding.txtTitle.text = getString(R.string.address_setting)
		editDeleteBinding.btnDelete.text = getString(R.string.address_delete)

		//edit address
		editDeleteBinding.btnEdit.setOnClickListener {
			mDialog?.dismiss()
			val action = AddressesFragmentDirections.actionAddressesFragmentToAddressMapFragment(item)
			findNavController().navigate(action)
		}

		//delete address
		editDeleteBinding.btnDelete.setOnClickListener { showDeleteDialog(position) }

		mDialog = MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
			customView(view = editDeleteBinding.root)

			onShow {
				this.getCustomView().setPadding(0, 0, 0, 0)
			}
		}
	}

	private fun setupUI() {
		binding.imgToolbarIcon.setOnClickListener { back() }

		binding.btnAddAddress.setOnClickListener {
			val action = AddressesFragmentDirections.actionAddressesFragmentToAddressMapFragment(Address())
			findNavController().navigate(action)
		}

		binding.rvAddresses.adapter = addressesAdapter

		setAdapter()
	}

	private fun setAdapter() {

		//rvAddresses
		binding.rvAddresses.setHasFixedSize(true)
		binding.rvAddresses.layoutManager = LinearLayoutManager(requireContext())

		try {
			binding.rvAddresses.removeItemDecorationAt(0)
		} catch (_: Exception) {
		} finally {
			binding.rvAddresses.addItemDecoration(
				MarginItemDecoration(
					resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._16sdp),
					MarginItemDecoration.TOP
				)
			)
		}
		if (addressesAdapter.mItems.isEmpty()) {
			for (i in 1 until 10) {
				addressesAdapter.mItems.add(Address(
					id = i,
					_address = "خ. ملاصدرا، کوچه ۹ ، ساختمان پویا ، طبقه ۳ ، واحد ۴ زنگ سوم",
					_postalCode = "7777777777",
					_plaque = "4",
					_mobile = "09125469874",
					provinceId = 0,
					cityId = 0,
					_province = "فارس",
					_city = "شیراز",
					_lat = 29.6002444,
					_lng = 52.5369748
				))
			}
			addressesAdapter.notifyDataSetChanged()
		}
	}

	private fun showDeleteDialog(position: Int) {
		mDialog?.dismiss()

		val dialogSimpleBinding = TemplateDialogSimpleBinding.inflate(layoutInflater)
		dialogSimpleBinding.txtTitle.text = getString(R.string.sure_delete_address)

		dialogSimpleBinding.btnAction.setOnClickListener {
			//TODO: remove address
			mDialog?.dismiss()

			addressesAdapter.mItems.removeAt(position)
			addressesAdapter.notifyItemRemoved(position)
		}

		dialogSimpleBinding.btnClose.setOnClickListener { mDialog?.dismiss() }

		mDialog = MaterialDialog(requireContext()).show {
			customView(view = dialogSimpleBinding.root)

			onShow {
				dialogSimpleBinding.cvMainDialog.setPadding(
					resources.getDimension(com.intuit.sdp.R.dimen._20sdp).toInt(),
					resources.getDimension(com.intuit.sdp.R.dimen._10sdp).toInt(),
					resources.getDimension(com.intuit.sdp.R.dimen._20sdp).toInt(),
					resources.getDimension(com.intuit.sdp.R.dimen._10sdp).toInt()
				)
			}
		}
	}
}