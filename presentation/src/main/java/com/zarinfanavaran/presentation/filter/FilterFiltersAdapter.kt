package com.zarinfanavaran.presentation.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.Filter
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateFilterMultiSelectBinding
import com.zarinfanavaran.presentation.databinding.TemplateFilterSingleChoiceBinding

/**
 * Created by Ali Ranjbarzadeh on 2023/02/08.
 */
class FilterFiltersAdapter : BaseAdapter<Filter>() {

	lateinit var recyclerViewTools: RecyclerViewTools
	lateinit var glide: RequestManager

	override fun getItemViewType(position: Int): Int {
		return when (mItems[position].type) {
			"MULTIPLE_OPTION" -> R.layout.template_filter_multi_select

			"SINGLE_OPTION" -> R.layout.template_filter_single_choice

			else -> R.layout.template_warning
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Filter> {
		val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)

		return object : BaseHolder<Filter>(binding) {
			override fun onBindUI(item: Filter, position: Int) {

				when (item.type) {
					"MULTIPLE_OPTION" -> handleMultiple(item, binding as TemplateFilterMultiSelectBinding)

					"SINGLE_OPTION" -> handleSingle(item, binding as TemplateFilterSingleChoiceBinding)
				}

				binding.executePendingBindings()
			}

			private fun handleMultiple(item: Filter, binding: TemplateFilterMultiSelectBinding) {
				binding.item = item
			}

			private fun handleSingle(item: Filter, binding: TemplateFilterSingleChoiceBinding) {
				binding.item = item
			}
		}
	}
}