package com.zarinfanavaran.presentation.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import gun0912.tedkeyboardobserver.TedKeyboardObserver

/**
 * Created by Ali Ranjbarzadeh on 2023/02/03.
 */
abstract class BaseDialogFragment<VDB : ViewDataBinding>(
	@LayoutRes
	private val resId: Int,
) : DialogFragment(), RecyclerViewTools, RetryCallback {
	protected val TAG = this::class.java.simpleName + "Log"

	@ColorRes
	var backgroundResColor: Int = R.color.colorEF

	lateinit var binding: VDB
	var baseDialogFragmentCallback: BaseDialogFragmentCallback? = null
	protected var baseFragmentCallback: BaseFragmentCallback? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate(inflater, resId, container, false)

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
		baseDialogFragmentCallback = null
	}

	fun back() {
		findNavController().popBackStack()
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

	protected fun setProgressView(
		viewGroup: ViewGroup, isLoading: Boolean, isSmall: Boolean = false,
		@ColorRes
		backgroundColor: Int = R.color.colorEF
	) {
		if (isLoading) {
			val progressView =
				if (isSmall)
					layoutInflater.inflate(R.layout.loading_small, viewGroup, false)
				else
					layoutInflater.inflate(R.layout.loading, viewGroup, false)

			progressView.findViewById<FrameLayout>(R.id.flLoading).setBackgroundResource(backgroundColor)
			if (viewGroup is ConstraintLayout) {
				val layoutParams = ConstraintLayout.LayoutParams(
					ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
					ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
				)

				layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
				layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
				layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
				layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID

				progressView.layoutParams = layoutParams
			}
			viewGroup.addView(progressView)
		} else {
			viewGroup.removeView(viewGroup.findViewById<FrameLayout>(R.id.flLoading))
		}
	}
}