package com.zarinfanavaran.presentation.sale

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentSaleBinding

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class SaleFragment : BaseFragment<FragmentSaleBinding>(R.layout.fragment_sale) {

	private lateinit var saleProductAdapter: SaleProductAdapter

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.rvSale.setHasFixedSize(true)
		binding.rvSale.layoutManager = GridLayoutManager(requireContext(), 2)

		setAdapter()
	}

	private val productRecyclerViewTools = object : RecyclerViewTools {}

	private fun setAdapter() {
		if (!this::saleProductAdapter.isInitialized) {
			saleProductAdapter = SaleProductAdapter(productRecyclerViewTools)

			val camera = Product(
				title = "دوربین کانن فوق حرفه ای عکاسی D800",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_product2
			).apply {
				timerTime = 79020
			}

			val camera2 = Product(
				title = "دوربین کانن فوق حرفه ای عکاسی D800",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_product2
			).apply {
				timerTime = 79126
			}

			val camera3 = Product(
				title = "دوربین کانن فوق حرفه ای عکاسی D800",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_product2
			).apply {
				timerTime = 79228
			}

			val camera4 = Product(
				title = "دوربین کانن فوق حرفه ای عکاسی D800",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_product2
			).apply {
				timerTime = 79230
			}

			val mobile = Product(
				title = "گوشی موبایل S22 ultra حافظه 512 و رم 12 گیگ",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_product1
			).apply {
				timerTime = 79338
			}

			val mobile2 = Product(
				title = "گوشی موبایل S22 ultra حافظه 512 و رم 12 گیگ",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_product1
			).apply {
				timerTime = 79448
			}

			val mobile3 = Product(
				title = "گوشی موبایل S22 ultra حافظه 512 و رم 12 گیگ",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_product1
			).apply {
				timerTime = 79578
			}

			val mobile4 = Product(
				title = "گوشی موبایل S22 ultra حافظه 512 و رم 12 گیگ",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_product1
			).apply {
				timerTime = 79688
			}

			val laptop = Product(
				title = "لپ تاپ ایسوس X582 رم 16 گیگ و هارد 1 ترابایت",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_category2
			).apply {
				timerTime = 79798
			}

			val laptop2 = Product(
				title = "لپ تاپ ایسوس X582 رم 16 گیگ و هارد 1 ترابایت",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_category2
			).apply {
				timerTime = 79810
			}

			val laptop3 = Product(
				title = "لپ تاپ ایسوس X582 رم 16 گیگ و هارد 1 ترابایت",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_category2
			).apply {
				timerTime = 79924
			}

			val laptop4 = Product(
				title = "لپ تاپ ایسوس X582 رم 16 گیگ و هارد 1 ترابایت",
				price = 65000000,
				discount = 15000000,
				image = R.drawable.temp_category2
			).apply {
				timerTime = 80038
			}

			saleProductAdapter.mItems.add(camera)
			saleProductAdapter.mItems.add(mobile)
			saleProductAdapter.mItems.add(laptop)
			saleProductAdapter.mItems.add(camera2)
			saleProductAdapter.mItems.add(mobile2)
			saleProductAdapter.mItems.add(laptop2)
			saleProductAdapter.mItems.add(camera3)
			saleProductAdapter.mItems.add(mobile3)
			saleProductAdapter.mItems.add(laptop3)
			saleProductAdapter.mItems.add(camera4)
			saleProductAdapter.mItems.add(mobile4)
			saleProductAdapter.mItems.add(laptop4)
		}

		binding.rvSale.adapter = saleProductAdapter
	}
}