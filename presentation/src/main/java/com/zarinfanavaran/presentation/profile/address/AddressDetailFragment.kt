package com.zarinfanavaran.presentation.profile.address

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zarinfanavaran.domain.models.Address
import com.zarinfanavaran.domain.models.City
import com.zarinfanavaran.domain.models.Province
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentAddressDetailBinding
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class AddressDetailFragment : BaseFragment<FragmentAddressDetailBinding>(R.layout.fragment_address_detail) {

	private val viewModel: AddressDetailViewModel by viewModels()
	private val args: AddressDetailFragmentArgs by navArgs()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setFragmentResultListener("province") { requestKey: String, bundle: Bundle ->
			val province = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				bundle.getParcelable("province", Province::class.java)
			} else {
				bundle.getParcelable("province")
			}

			province?.also {
				if (args.address.provinceId != it.id) {
					args.address.city = ""
					args.address.cityId = 0
				}
				args.address.province = it.name
				args.address.provinceId = it.id
			}
		}

		setFragmentResultListener("city") { requestKey: String, bundle: Bundle ->
			val city = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				bundle.getParcelable("city", City::class.java)
			} else {
				bundle.getParcelable("city")
			}

			city?.also {
				args.address.city = it.name
				args.address.cityId = it.id
			}
		}

		setupObservers()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		backgroundResColor = R.color.white
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.item = args.address

		setupUI()
	}

	override fun keyboardState(isShow: Boolean) {
		if (!isShow) {
			binding.etAddress.clearFocus()
			binding.etPostalCode.clearFocus()
			binding.etPlaque.clearFocus()
		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getAddress(), ::addAddress)
		}
	}

	private fun setupUI() {
		binding.imgToolbarIcon.setOnClickListener { back() }

		binding.etProvince.setOnClickListener {
			val action = AddressDetailFragmentDirections.actionAddressDetailFragmentToProvincesDialogFragment()
			findNavController().navigate(action)
		}

		binding.etCity.setOnClickListener {
			if (args.address.provinceId == 0) {
				Toast.makeText(requireContext(), getString(R.string.please_select_prvince), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			val action = AddressDetailFragmentDirections.actionAddressDetailFragmentToCitiesDialogFragment(args.address.provinceId)
			findNavController().navigate(action)
		}

		binding.btnNext.setOnClickListener {
			if (args.address.provinceId == 0) {
				Toast.makeText(requireContext(), getString(R.string.please_select_prvince), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			if (args.address.cityId == 0) {
				Toast.makeText(requireContext(), getString(R.string.please_select_city), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			val address = binding.etAddress.text.toString().trim()
			val postalCode = binding.etPostalCode.text.toString().trim()
			val plaque = binding.etPlaque.text.toString().trim()
			val mobile = binding.etMobile.text.toString().trim()

			if (address.isEmpty()) {
				Toast.makeText(requireContext(), getString(R.string.please_enter_address), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}
			if (postalCode.isEmpty()) {
				Toast.makeText(requireContext(), getString(R.string.please_enter_postal_code), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}
			if (plaque.isEmpty()) {
				Toast.makeText(requireContext(), getString(R.string.please_enter_plaque), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}
			if (mobile.isEmpty()) {
				Toast.makeText(requireContext(), getString(R.string.please_enter_mobile), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			args.address.also {
				it.address = address
				it.postalCode = postalCode
				it.plaque = plaque
				it.mobile = mobile
			}

			//TODO: save address
			val jsonObject = JSONObject()
			jsonObject.put("address", args.address.address)
			jsonObject.put("province_id", args.address.provinceId)
			jsonObject.put("city_id", args.address.cityId)
			jsonObject.put("house_number", args.address.mobile)
			jsonObject.put("floor", args.address.plaque)
			jsonObject.put("postal_code", args.address.postalCode)
			jsonObject.put("lat", args.address.lat)
			jsonObject.put("lng", args.address.lng)
//			viewModel.saveAddress(jsonObject.toRequestBody())

			val bundle = Bundle()
			bundle.putParcelable("address", args.address)
			setFragmentResult("address", bundle)
			findNavController().popBackStack(R.id.addressesFragment, false)
//			val action = AddressDetailFragmentDirections.actionAddressDetailFragmentToAddressesFragment(args.address)
//			findNavController().navigate(action)
		}
	}

	private fun initLoading(isLoading: Boolean) {
		setProgressView(binding.clMain, isLoading)
	}

	private fun addAddress(result: NetworkResult<Address>) {}
}