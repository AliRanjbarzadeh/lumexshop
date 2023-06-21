package com.zarinfanavaran.presentation.profile.address

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentAddressMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressMapFragment : BaseFragment<FragmentAddressMapBinding>(R.layout.fragment_address_map) {

	private val args: AddressMapFragmentArgs by navArgs()

	private var isLocationRequest = false
	private var lat = 29.6679769
	private var lng = 52.4562958

	private lateinit var mapView: SupportMapFragment
	private var mGoogleMap: GoogleMap? = null

	val locationsPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
		if (permissions.filter { permission -> !permission.value }.isEmpty()) {
			//all permission granted
			if (isLocationRequest) {
				isLocationRequest = false
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	private fun setupUI() {
		locationsPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
		binding.imgToolbarIcon.setOnClickListener { back() }

		binding.btnZoomIn.setOnClickListener {
			mGoogleMap?.animateCamera(CameraUpdateFactory.zoomIn())
		}

		binding.btnZoomOut.setOnClickListener {
			mGoogleMap?.animateCamera(CameraUpdateFactory.zoomOut())
		}

		binding.btnMyLocation.setOnClickListener {
			mGoogleMap?.also {
				isLocationRequest = true
				locationsPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
			}
		}

		binding.btnNext.setOnClickListener {
			mGoogleMap?.cameraPosition?.target?.also { latLng: LatLng ->
				args.address.lat = latLng.latitude
				args.address.lng = latLng.longitude

				val action = AddressMapFragmentDirections.actionAddressMapFragmentToAddressDetailFragment(args.address)
				findNavController().navigate(action)
			}
		}

		showMap()
	}

	private fun showMap() {
		try {
			mapView = SupportMapFragment.newInstance()
			childFragmentManager.beginTransaction()
					.replace(R.id.flMap, mapView)
					.commit()
			mapView.getMapAsync(onMapReady)
		} catch (_: Exception) {
		}
	}

	private val onMapReady = OnMapReadyCallback { googleMap ->
		mGoogleMap = googleMap
		val target = if (args.address.id > 0 || (!args.address.lat.equals(0.0) && !args.address.lng.equals(0.0))) {
			LatLng(args.address.lat, args.address.lng)
		} else {
			LatLng(lat, lng)
		}

		val cameraPosition =
			CameraPosition.Builder()
					.zoom(16f)
					.target(target)
					.build()

		mGoogleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

		mGoogleMap?.also {
			val uiSettings = it.uiSettings
			uiSettings.isMyLocationButtonEnabled = false
			uiSettings.isCompassEnabled = false
			uiSettings.isZoomControlsEnabled = false
			uiSettings.isIndoorLevelPickerEnabled = false
			uiSettings.isRotateGesturesEnabled = false
			uiSettings.isTiltGesturesEnabled = false
		}
	}
}