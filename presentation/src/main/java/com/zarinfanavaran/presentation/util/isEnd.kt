package com.zarinfanavaran.presentation.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.isEnd(isEndOfRecyclerView: IsEndOfRecyclerView) {
	this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
		override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
			if (dy > 0) {
//				layoutManager?.also {
//					val linearLayoutManager = it as LinearLayoutManager
//					if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.itemCount - 1) {
//						isEndOfRecyclerView.onReachToEnd()
//					}
//				}
				val visibleItemCount = layoutManager!!.childCount
				val totalItemCount = layoutManager!!.itemCount
				val pastVisibleItems = when (layoutManager) {
					is LinearLayoutManager -> (layoutManager!! as LinearLayoutManager).findFirstVisibleItemPosition()

					is GridLayoutManager -> (layoutManager!! as GridLayoutManager).findFirstVisibleItemPosition()

					else -> 0
				}
				if (visibleItemCount + pastVisibleItems >= totalItemCount) {
					isEndOfRecyclerView.onReachToEnd()
				}
			}
		}
	})
}

interface IsEndOfRecyclerView {
	fun onReachToEnd() {}
}