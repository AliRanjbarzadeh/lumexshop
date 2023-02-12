package com.zarinfanavaran.presentation.base

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zarinfanavaran.domain.BuildConfig.DEFAULT_LANGUAGE
import com.zarinfanavaran.domain.BuildConfig.SESSION_LANGUAGE
import com.zarinfanavaran.domain.extensions.loadFromSp
import com.zarinfanavaran.domain.extensions.setLanguage
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
abstract class BaseActivity<VDB : ViewDataBinding>(
	@LayoutRes
	private val resId: Int
) : AppCompatActivity(), BaseFragmentCallback, RecyclerViewTools {
	protected val TAG = this::class.java.simpleName + "Log"

	lateinit var binding: VDB

	override fun attachBaseContext(newBase: Context?) {
		newBase?.let {
			val displayMetrics = newBase.resources.displayMetrics
			val configuration = newBase.resources.configuration
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				if (displayMetrics.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE) {
					// Current density is different from Default Density. Override it
					configuration.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE
				}
			}
			val newOverride = Configuration(newBase.resources.configuration)
			newOverride.fontScale = 1.0f
			applyOverrideConfiguration(newOverride)

			setLanguage(newBase, loadFromSp(SESSION_LANGUAGE, DEFAULT_LANGUAGE))
			super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
		} ?: kotlin.run {
			super.attachBaseContext(newBase)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, resId)
	}

	protected fun getToolbarSearchText(): SpannableString {
		return spannableString(
			this,
			firstString = "جستجو در   ",
			firstColor = R.color.colorA3,
			firstSize = com.intuit.sdp.R.dimen._10sdp,
			firstFont = getString(R.string.font_regular),

			secondString = "لومکس شاپ",
			secondColor = R.color.color42,
			secondSize = com.intuit.sdp.R.dimen._14sdp,
			secondFont = getString(R.string.font_bold)
		)
	}

	protected fun setProgressView(viewGroup: ViewGroup, isLoading: Boolean) {
		if (isLoading) {
			val progressView = layoutInflater.inflate(R.layout.loading, viewGroup, false)
			viewGroup.addView(progressView)
		} else {
			Log.d(TAG, "setProgressView: ${viewGroup.findViewById<FrameLayout>(R.id.flLoading)}")
			viewGroup.removeView(viewGroup.findViewById<FrameLayout>(R.id.flLoading))
		}
	}
}