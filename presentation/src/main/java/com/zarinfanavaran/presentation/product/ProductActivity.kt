package com.zarinfanavaran.presentation.product

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zarinfanavaran.domain.models.ListTitle
import com.zarinfanavaran.domain.models.ProductColor
import com.zarinfanavaran.domain.models.ProductTool
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseActivity
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.databinding.ActivityProductBinding

class ProductActivity : BaseActivity<ActivityProductBinding>(R.layout.activity_product) {

	private val productTagAdapter = ProductTagAdapter()
	private val productToolAdapter = ProductToolAdapter()
	private val productColorAdapter = ProductColorAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		//set toolbar text
		binding.txtToolbarTitle.text = getToolbarSearchText()

		setTagAdapter()
		setToolAdapter()
		setColorAdapter()
	}

	override fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int) {
		when (item) {
			is ProductTool -> {
				when (item.type) {
					"bookmark" -> {
						item.isChecked = !item.isChecked

						if (item.isChecked)
							item.image = R.drawable.ic_bookmark_filled
						else
							item.image = R.drawable.ic_bookmark_stroke
					}
				}
			}

			is ProductColor -> {
				productColorAdapter.mItems.find { productColor -> productColor.isChecked }?.isChecked = false
			}
		}
	}

	private fun setTagAdapter() {
		if (productTagAdapter.mItems.isEmpty()) {
			productTagAdapter.mItems.add(ListTitle("فروشگاه اینترنتی لومکس"))
			productTagAdapter.mItems.add(ListTitle("لوازم الکترونیکی"))
			productTagAdapter.mItems.add(ListTitle("موبایل"))
			productTagAdapter.mItems.add(ListTitle("شیائومی"))
		}

		try {
			binding.rvTags.removeItemDecorationAt(0)
		} catch (_: Exception) {
		}

		binding.rvTags.setHasFixedSize(true)
		binding.rvTags.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		binding.rvTags.addItemDecoration(
			MarginItemDecoration(
				mHeight = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp),
				marginPosition = MarginItemDecoration.RIGHT,
				isShowOnFirstItem = false
			)
		)
		binding.rvTags.adapter = productTagAdapter
		binding.rvTags.scrollToPosition(productTagAdapter.mItems.size - 1)
	}

	private fun setToolAdapter() {
		productToolAdapter.recyclerViewTools = this
		if (productToolAdapter.mItems.isEmpty()) {
			productToolAdapter.mItems.add(ProductTool(R.drawable.ic_share_filled, "share"))
			productToolAdapter.mItems.add(ProductTool(R.drawable.ic_user_pic_stroke, "user_pic"))
			productToolAdapter.mItems.add(ProductTool(R.drawable.ic_bookmark_stroke, "bookmark"))
			productToolAdapter.mItems.add(ProductTool(R.drawable.ic_scale, "scale"))
		}

		try {
			binding.rvTools.removeItemDecorationAt(0)
		} catch (_: Exception) {
		}

		binding.rvTools.setHasFixedSize(true)
		binding.rvTools.layoutManager = LinearLayoutManager(this)
		binding.rvTools.addItemDecoration(
			MarginItemDecoration(
				mHeight = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp),
				marginPosition = MarginItemDecoration.TOP,
				isShowOnFirstItem = false
			)
		)

		binding.rvTools.adapter = productToolAdapter
	}

	private fun setColorAdapter() {
		productColorAdapter.recyclerViewTools = this

		if (productColorAdapter.mItems.isEmpty()) {
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#BAE4FE", true))
			productColorAdapter.mItems.add(ProductColor("#FFFAFA", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
			productColorAdapter.mItems.add(ProductColor("#000000", false))
		}

		try {
			binding.rvColors.removeItemDecorationAt(0)
		} catch (_: Exception) {
		}

		binding.rvColors.setHasFixedSize(true)
		binding.rvColors.layoutManager = LinearLayoutManager(this)
		binding.rvColors.addItemDecoration(
			MarginItemDecoration(
				resources.getDimension(com.intuit.sdp.R.dimen._4sdp).toInt(),
				MarginItemDecoration.TOP, false
			)
		)

		binding.rvColors.adapter = productColorAdapter
	}
}