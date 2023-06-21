package com.zarinfanavaran.presentation.filter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.Filter
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.base.MarginItemDecoration
import com.zarinfanavaran.presentation.databinding.TemplateFilterBooleanBinding
import com.zarinfanavaran.presentation.databinding.TemplateFilterMultiSelectBinding
import com.zarinfanavaran.presentation.databinding.TemplateFilterSingleChoiceBinding

/**
 * Created by Ali Ranjbarzadeh on 2023/02/08.
 */
class FilterFiltersAdapter : BaseAdapter<Filter>() {

	lateinit var recyclerViewTools: RecyclerViewTools
	lateinit var glide: RequestManager
	private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

	val mAdapters = SparseArray<FilterOptionsAdapter>()

	override fun getItemViewType(position: Int): Int {
		return when (mItems[position].type) {
			"MULTIPLE_OPTION" -> R.layout.template_filter_multi_select

			"SINGLE_OPTION" -> R.layout.template_filter_single_choice

			"BOOLEAN" -> R.layout.template_filter_boolean

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

					"BOOLEAN" -> handleBoolean(item, binding as TemplateFilterBooleanBinding)
				}

				binding.executePendingBindings()
			}

			private fun handleMultiple(item: Filter, binding: TemplateFilterMultiSelectBinding) {
				binding.item = item

				//expand/collapse click
				binding.clFilterTitle.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, binding.root, item) }

				item.options?.also { filterOptions ->
					if (mAdapters[bindingAdapterPosition] == null) {
						val filterOptionsAdapter = FilterOptionsAdapter(bindingAdapterPosition).also {
							it.recyclerViewTools = recyclerViewTools
						}

						filterOptionsAdapter.mItems.addAll(filterOptions)
						binding.rvOptions.layoutManager = LinearLayoutManager(binding.root.context)
						binding.rvOptions.setHasFixedSize(true)
						binding.rvOptions.setRecycledViewPool(viewPool)

						mAdapters[bindingAdapterPosition] = filterOptionsAdapter
					}

					try {
						binding.rvOptions.removeItemDecorationAt(0)
					} catch (_: Exception) {
					} finally {
						binding.rvOptions.addItemDecoration(
							MarginItemDecoration(
								binding.root.context.resources.getDimension(com.intuit.sdp.R.dimen._8sdp).toInt(),
								MarginItemDecoration.TOP
							)
						)
					}

					binding.rvOptions.adapter = mAdapters[bindingAdapterPosition]
				}

				//search
				binding.etInnerSearch.doAfterTextChanged { text ->
					if (text != null)
						recyclerViewTools.onFilter(bindingAdapterPosition, item, text.toString())
					else
						recyclerViewTools.onFilter(bindingAdapterPosition, item, "")
				}
			}

			private fun handleSingle(item: Filter, binding: TemplateFilterSingleChoiceBinding) {
				binding.item = item

				//expand/collapse click
				binding.clFilterTitle.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, binding.root, item) }

				item.options?.also { filterOptions ->
					if (mAdapters[bindingAdapterPosition] == null) {
						val filterOptionsAdapter = FilterOptionsAdapter(bindingAdapterPosition).also {
							it.recyclerViewTools = recyclerViewTools
						}

						filterOptionsAdapter.mItems.addAll(filterOptions)
						val layoutManager = LinearLayoutManager(binding.root.context)
						binding.rvOptions.layoutManager = layoutManager
						binding.rvOptions.setRecycledViewPool(viewPool)

						mAdapters[bindingAdapterPosition] = filterOptionsAdapter
					}

					try {
						binding.rvOptions.removeItemDecorationAt(0)
					} catch (_: Exception) {
					} finally {
						binding.rvOptions.addItemDecoration(
							MarginItemDecoration(
								binding.root.context.resources.getDimension(com.intuit.sdp.R.dimen._8sdp).toInt(),
								MarginItemDecoration.TOP
							)
						)
					}

					binding.rvOptions.setHasFixedSize(false)
					binding.rvOptions.adapter = mAdapters[bindingAdapterPosition]
				}

				//search
				binding.etInnerSearch.doAfterTextChanged { text ->
					if (text != null)
						recyclerViewTools.onFilter(bindingAdapterPosition, item, text.toString())
					else
						recyclerViewTools.onFilter(bindingAdapterPosition, item, "")
				}
			}

			private fun handleBoolean(item: Filter, binding: TemplateFilterBooleanBinding) {
				binding.item = item

				//expand/collapse click
				binding.clFilterTitle.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }
			}
		}
	}
}