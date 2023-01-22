package com.zarinfanavaran.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.models.CategoryDetail
import com.zarinfanavaran.presentation.base.BaseActivity
import com.zarinfanavaran.presentation.databinding.ActivityFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterActivity : BaseActivity<ActivityFilterBinding>(R.layout.activity_filter) {

	private var categoryDetail: CategoryDetail? = null
	private var category: Category? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		//set toolbar text
		binding.txtToolbarTitle.text = getToolbarSearchText()

		//set toolbar action
		binding.imgToolbarIcon.setOnClickListener { finish() }

		intent.extras?.also { bundle ->
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				category = bundle.getParcelable("category", Category::class.java)
				categoryDetail = bundle.getParcelable("categoryDetail", CategoryDetail::class.java)
			} else {
				category = bundle.getParcelable("category")
				categoryDetail = bundle.getParcelable("categoryDetail")
			}
		}
	}
}