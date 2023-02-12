package com.zarinfanavaran.presentation.home

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zarinfanavaran.domain.models.*
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.RetryDialog
import com.zarinfanavaran.presentation.databinding.FragmentHomeBinding
import com.zarinfanavaran.presentation.product.ProductActivity

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

	private var homeAdapter = HomeAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		//set click handlers
		homeAdapter.recyclerViewTools = this
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//set toolbar text
		binding.txtToolbarTitle.text = getToolbarSearchText()

		binding.rvHome.setHasFixedSize(true)
		binding.rvHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

		//set home adapter
		setAdapter()
	}

	override fun <T> onItemClick(position: Int, view: View, item: T) {
		when (item) {
			is Product -> {
				Log.d(TAG, "onItemClick: $item")
				startActivity(Intent(requireContext(), ProductActivity::class.java))
			}
		}
	}

	private fun setAdapter() {
		if (homeAdapter.mItems.isEmpty()) {
			//set slider item
			sliderAdapter()

			//products box 1
			productsBox(
				icon = R.drawable.temp_icon_fire,
				title = "تخفیفات روز",
				image = ContextCompat.getDrawable(requireContext(), R.drawable.temp_box)!!,
			)

			//banners box
			bannerBox()

			//products box 2
			productsBox(
				icon = R.drawable.temp_icon_mobile,
				title = "خرید موبایل",
				image = ContextCompat.getDrawable(requireContext(), R.drawable.temp_box2)!!,
			)

			//single banner
			homeAdapter.mItems.add(Banner(R.drawable.temp_banner_single))

			//products box 3
			productsBox(
				icon = R.drawable.temp_icon_camera,
				title = "خرید دوربین",
				image = ContextCompat.getDrawable(requireContext(), R.drawable.temp_box3)!!,
				onlyCamera = true
			)

			//two banner
			bannerBox(true)
		}

		binding.rvHome.adapter = homeAdapter
	}

	private fun sliderAdapter() {
		val slides = mutableListOf<Slide>()
		slides.add(Slide(image = R.drawable.temp_slide3, color = "#B75915"))
		slides.add(Slide(image = R.drawable.temp_slide, color = "#FEDD31"))
		slides.add(Slide(image = R.drawable.temp_slide2, color = "#0FB9D9"))
		val sliderBox = SliderBox(slides)
		homeAdapter.mItems.add(sliderBox)
	}

	private fun productsBox(
		@DrawableRes
		icon: Int, title: String, image: Drawable, onlyCamera: Boolean = false
	) {
		//product box 1
		val products = mutableListOf<Any>()

//		val mobile = Product(
//			title = "گوشی موبایل S22 ultra حافظه 512 و رم 12 گیگ",
//			price = 65000000,
//			discount = 15000000,
//			image = R.drawable.temp_product1
//		)
//
//		val camera = Product(
//			title = "دوربین کانن فوق حرفه ای عکاسی D800",
//			price = 65000000,
//			discount = 15000000,
//			image = R.drawable.temp_product2
//		)
//
//		if (onlyCamera) {
//			products.add(camera)
//			products.add(camera)
//			products.add(camera)
//		} else {
//			products.add(mobile)
//			products.add(camera)
//			products.add(mobile)
//		}

		//add more
//		products.add(
//			Category(
//				name = "",
//				image = R.drawable.temp_icon_camera,
//				icon = R.drawable.temp_icon_mobile2,
//			)
//		)

		val productsBox = ProductsBox(
			icon = icon,
			title = title,
			image = image,
			products = products
		)

		homeAdapter.mItems.add(productsBox)
	}

	private fun bannerBox(twoColumns: Boolean = false) {
		val banners = mutableListOf<Banner>()

		if (twoColumns) {
			banners.add(Banner(R.drawable.temp_banner_c))
			banners.add(Banner(R.drawable.temp_banner_c2))
		} else {
			banners.add(Banner(R.drawable.temp_banner))
			banners.add(Banner(R.drawable.temp_banner2))
			banners.add(Banner(R.drawable.temp_banner3))
			banners.add(Banner(R.drawable.temp_banner4))
		}

		homeAdapter.mItems.add(BannersBox(banners))
	}
}