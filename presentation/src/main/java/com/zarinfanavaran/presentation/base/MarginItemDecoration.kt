package com.zarinfanavaran.presentation.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
	private val mHeight: Int,
	private val marginPosition: Int = 1,
	private val isShowOnFirstItem: Boolean = false
) : RecyclerView.ItemDecoration() {
	companion object {
		val BOTTOM = 1
		val TOP = 2
		val LEFT = 3
		val RIGHT = 4
	}

	override fun getItemOffsets(
		outRect: Rect,
		view: View,
		parent: RecyclerView,
		state: RecyclerView.State
	) {
		with(outRect) {
			when (marginPosition) {
				BOTTOM -> {
					if (isShowOnFirstItem) {
						bottom = mHeight
					} else if (parent.getChildAdapterPosition(view) != 0) {
						bottom = mHeight
					}

				}
				TOP -> {
					if (isShowOnFirstItem) {
						top = mHeight
					} else if (parent.getChildAdapterPosition(view) != 0) {
						top = mHeight
					}
				}
				LEFT -> {
					if (isShowOnFirstItem) {
						left = mHeight
					} else if (parent.getChildAdapterPosition(view) != 0) {
						left = mHeight
					}
				}
				RIGHT -> {
					if (isShowOnFirstItem) {
						right = mHeight
					} else if (parent.getChildAdapterPosition(view) != 0) {
						right = mHeight
					}
				}
			}
		}
	}

}