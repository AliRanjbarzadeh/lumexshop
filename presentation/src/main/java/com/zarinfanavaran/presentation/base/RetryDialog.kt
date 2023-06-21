package com.zarinfanavaran.presentation.base

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onShow
import com.zarinfanavaran.presentation.R

/**
 * Created by Ali Ranjbarzadeh on 2023/02/07.
 */
class RetryDialog(context: Context, private val retryCallback: RetryCallback, isShowCancel: Boolean = true, mTitle: String? = null, mMessage: String? = null) {
	private var mDialog: MaterialDialog = MaterialDialog(context)
			.title(text = mTitle ?: context.getString(R.string.retry_dialog_title))
			.message(text = mMessage ?: context.getString(R.string.retry_dialog_message))
			.cancelable(false)
			.noAutoDismiss()
			.positiveButton(text = context.getString(R.string.try_again)) { materialDialog ->
				materialDialog.dismiss()
				retryCallback.onRetry()
			}
			.onShow { materialDialog ->
				materialDialog.cornerRadius(0f)
			}

	init {
		if (isShowCancel) {
			mDialog.negativeButton(text = context.getString(R.string.close)) { materialDialog ->
				materialDialog.dismiss()
				retryCallback.onCancel()
			}
		}
	}

	fun show() {
		if (mDialog.isShowing) {
			mDialog.dismiss()
		}
		mDialog.show()
	}

}