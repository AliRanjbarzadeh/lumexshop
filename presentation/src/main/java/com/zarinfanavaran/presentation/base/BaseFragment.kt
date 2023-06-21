package com.zarinfanavaran.presentation.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.util.IsEndOfRecyclerView
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import java.io.File

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
abstract class BaseFragment<VDB : ViewDataBinding>(
	@LayoutRes
	private val resId: Int,
) : Fragment(), RecyclerViewTools, IsEndOfRecyclerView, BaseDialogFragmentCallback, RetryCallback {

	protected val TAG = this::class.java.simpleName + "Log"

	@ColorRes
	var backgroundResColor: Int = R.color.colorEF

	lateinit var binding: VDB
	protected var baseFragmentCallback: BaseFragmentCallback? = null
	protected var mDialog: MaterialDialog? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate(inflater, resId, container, false)

		//set background color
		binding.root.setBackgroundResource(backgroundResColor)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		TedKeyboardObserver(requireActivity())
				.listen { isShow ->
					keyboardState(isShow)
				}
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context is BaseFragmentCallback) {
			baseFragmentCallback = context
		}
	}

	override fun onDetach() {
		super.onDetach()
		baseFragmentCallback = null
	}

	protected open fun keyboardState(isShow: Boolean) {}

	fun hideKeyboard() {
		val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
		view?.also {
			imm.hideSoftInputFromWindow(it.rootView.windowToken, 0)
		}
	}

	fun showInputMethod(v: EditText) {
		val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		v.requestFocus()
		inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
	}

	fun back() {
		findNavController().popBackStack()
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

	protected fun getToolbarSearchText(): SpannableString {
		return spannableString(
			requireContext(),
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

	protected fun getAuthority(): String {
		return requireContext().packageName + ".my_file_provider"
	}

	protected fun getFileUri(file: File): Uri {
		return FileProvider.getUriForFile(requireContext(), getAuthority(), file)
	}

	protected fun shareText(shareBody: String, shareTitle: String = " Share With ") {
		val sharingIntent = Intent(Intent.ACTION_SEND)
		sharingIntent.type = "text/plain"
		sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
		startActivity(Intent.createChooser(sharingIntent, shareTitle))
	}
}