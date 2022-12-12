package com.zarinfanavaran.presentation.base

import com.github.islamkhsh.CardSliderAdapter

abstract class BaseSlideAdapter<T> : CardSliderAdapter<BaseHolder<T>>() {

	var mItems: MutableList<T> = mutableListOf()
		set(value) {
			field = value
			notifyItemRangeInserted(0, field.size)
		}

	override fun getItemCount(): Int {
		return mItems.size
	}

	override fun bindVH(holder: BaseHolder<T>, position: Int) {
		holder.onBindUI(mItems[position], position)
	}
}