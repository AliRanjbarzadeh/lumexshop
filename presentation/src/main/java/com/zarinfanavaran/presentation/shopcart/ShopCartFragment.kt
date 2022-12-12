package com.zarinfanavaran.presentation.shopcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.button.MaterialButton
import com.zarinfanavaran.domain.extensions.priceFormat
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.models.ListTitle
import com.zarinfanavaran.domain.models.ProductShopCart
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentShopcartBinding

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class ShopCartFragment : BaseFragment<FragmentShopcartBinding>(R.layout.fragment_shopcart) {

	private lateinit var shopCartProductAdapter: ShopCartProductAdapter

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		baseFragmentCallback?.bottomNavigationVisibility(true)
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.rvShopCart.setHasFixedSize(true)
		binding.rvShopCart.layoutManager = LinearLayoutManager(requireContext())

		setAdapter()

		binding.btnNext.setOnClickListener {
			findNavController().navigate(R.id.shopCartAddressFragment)
		}

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
	}

	private val shopCartRecyclerViewTools = object : RecyclerViewTools {
		override fun <T> onItemClick(position: Int, view: View, item: T) {
			item as ProductShopCart
			when (view.id) {
				R.id.btnIncrease -> handleIncrease(position, item)

				R.id.btnDecrease -> handleDecrease(position, item)
			}
		}
	}

	private fun setAdapter() {

		if (!this::shopCartProductAdapter.isInitialized) {
			shopCartProductAdapter = ShopCartProductAdapter(shopCartRecyclerViewTools)

			val mobile = ProductShopCart(
				title = "گوشی موبایل شیائومی حافظه 512 رم 12 گیگ",
				price = 60000000,
				discount = 2500000,
				image = R.drawable.temp_product1,
				_count = 1
			).apply {
				maxCount = 3
				colorName = "آبی سلستیال"
				colorValue = "#7CE1CB"
				warranty = "گارانتی: زرین فناوران فارس"
			}

			val camera = ProductShopCart(
				title = "دوربین کانن فوق حرفه ای عکاسی D800",
				price = 60000000,
				discount = 2500000,
				image = R.drawable.temp_product2,
				_count = 1
			).apply {
				maxCount = 2
				colorName = "مشکی"
				colorValue = "#000000"
				warranty = "گارانتی: زرین فناوران فارس"
			}

			val laptop = ProductShopCart(
				title = "لپ تاپ ایسوس UX582 رم 16 گیگ و هارد 1 ترابایت لپ تاپ ایسوس UX582",
				price = 60000000,
				discount = 2500000,
				image = R.drawable.temp_category2,
				_count = 1
			).apply {
				maxCount = 10
				colorName = "مشکی"
				colorValue = "#000000"
				warranty = "گارانتی: زرین فناوران فارس"
			}

			val mouse = ProductShopCart(
				title = "ماوس لاجیتک MX Master 3S",
				price = 60000000,
				discount = 2500000,
				image = R.drawable.temp_category4,
				_count = 1
			).apply {
				maxCount = 2
				colorName = "مشکی"
				colorValue = "#000000"
				warranty = "گارانتی: زرین فناوران فارس"
			}

			shopCartProductAdapter.mItems.add(ListTitle(title = "لیست خرید شما"))
			shopCartProductAdapter.mItems.add(mobile)
			shopCartProductAdapter.mItems.add(camera)
			shopCartProductAdapter.mItems.add(laptop)
			shopCartProductAdapter.mItems.add(mouse)
		}

		binding.rvShopCart.adapter = shopCartProductAdapter

	}

	private fun handleIncrease(position: Int, productShopCart: ProductShopCart) {
		if (productShopCart.count == productShopCart.maxCount) {
			return
		}
		productShopCart.count += 1
	}

	private fun handleDecrease(position: Int, productShopCart: ProductShopCart) {
		if (productShopCart.count == 1) {
			handleDelete(position, productShopCart)
			return
		}
		productShopCart.count -= 1
	}

	private fun handleDelete(position: Int, productShopCart: ProductShopCart) {
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
				shopCartProductAdapter.mItems.removeAt(position)
				shopCartProductAdapter.notifyItemRemoved(position)
				this.dismiss()
			}
		}
	}
}