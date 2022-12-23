package com.zarinfanavaran.presentation.categories.detail

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.zarinfanavaran.domain.models.*
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.databinding.FragmentCategoryDetailBinding

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class CategoryDetailFragment : BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {

	private val args: CategoryDetailFragmentArgs by navArgs()

	private lateinit var categoryDetailAdapter: CategoryDetailAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupUI()

		binding.txtToolbarTitle.text = getToolbarSearchText()

		binding.imgToolbarIcon.setOnClickListener { back() }

		binding.rvCategoryDetail.setHasFixedSize(true)
		binding.rvCategoryDetail.layoutManager = LinearLayoutManager(requireContext())

		try {
			binding.rvCategoryDetail.removeItemDecorationAt(0)
		} catch (_: Exception) {
		}

		binding.rvCategoryDetail.addItemDecoration(
			MarginItemDecoration(
				mHeight = requireContext().resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp),
				marginPosition = MarginItemDecoration.TOP,
				isShowOnFirstItem = false
			)
		)

		setAdapter()
	}

	private fun setupUI() {}

	private fun setupObservers() {}

	private val categoryDetailRecyclerViewTools = object : RecyclerViewTools {}

	private fun setAdapter() {
		if (!this::categoryDetailAdapter.isInitialized) {
			categoryDetailAdapter = CategoryDetailAdapter(categoryDetailRecyclerViewTools)

			//categories
			categoriesBox()

			//brands
			brandsBox()

			val drawableWhite = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white))

			//product box 1
			productsBox(
				icon = R.drawable.temp_icon_chart,
				title = "پرفروش ترین ها",
				image = drawableWhite
			)

			//product box 2
			productsBox(
				icon = R.drawable.temp_icon_heart_tick,
				title = "محبوب ترین ها",
				image = drawableWhite
			)
		}

		binding.rvCategoryDetail.adapter = categoryDetailAdapter
	}

	private fun categoriesBox() {
		val categoriesBox = CategoriesBox(categories = mutableListOf())
//		categoriesBox.categories.add(
//			Category(
//				name = "لپ تاپ",
//				icon = R.drawable.temp_icon_mobile,
//				image = R.drawable.temp_category2
//			)
//		)
//		categoriesBox.categories.add(
//			Category(
//				name = "لوازم جانبی",
//				icon = R.drawable.temp_icon_mobile,
//				image = R.drawable.temp_category4
//			)
//		)

		categoryDetailAdapter.mItems.add(categoriesBox)
	}

	private fun brandsBox() {
		val brands = mutableListOf<Any>()
		brands.add(
			Brand(
				nameFa = "اِیسوس",
				image = R.drawable.temp_brand
			)
		)
		brands.add(
			Brand(
				nameFa = "کانُن",
				image = R.drawable.temp_brand2
			)
		)
		brands.add(
			Brand(
				nameFa = "سامسونگ",
				image = R.drawable.temp_brand3
			)
		)

		//more
//		brands.add(
//			Category(
//				name = "",
//				icon = R.drawable.temp_icon_mobile2,
//				image = R.drawable.temp_category2
//			)
//		)

		categoryDetailAdapter.mItems.add(
			BrandsBox(
				title = "برندها",
				items = brands,
				icon = R.drawable.temp_icon_brands
			)
		)
	}

	private fun productsBox(@DrawableRes icon: Int, title: String, image: Drawable, onlyCamera: Boolean = false) {
		val products = mutableListOf<Any>()

		val mobile = Product(
			title = "گوشی موبایل S22 ultra حافظه 512 و رم 12 گیگ",
			price = 65000000,
			discount = 15000000,
			image = R.drawable.temp_product1
		)

		val camera = Product(
			title = "دوربین کانن فوق حرفه ای عکاسی D800",
			price = 65000000,
			discount = 15000000,
			image = R.drawable.temp_product2
		)

		if (onlyCamera) {
			products.add(camera)
			products.add(camera)
			products.add(camera)
		} else {
			products.add(mobile)
			products.add(camera)
			products.add(mobile)
		}

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

		categoryDetailAdapter.mItems.add(productsBox)
	}
}