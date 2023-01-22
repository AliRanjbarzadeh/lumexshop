package com.zarinfanavaran.presentation.categories

import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.zarinfanavaran.domain.models.Category
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateCategoriesBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */

class CategoriesAdapter : BaseAdapter<Category>() {

	lateinit var recyclerViewTools: RecyclerViewTools
	lateinit var glide: RequestManager
	private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
	private val mAdapters = SparseArray<CategoryAdapter>()
	private val scrollStates = SparseArray<Parcelable?>()

	override fun onViewRecycled(holder: BaseHolder<Category>) {
		super.onViewRecycled(holder)

		val mRecyclerView = holder.itemView.findViewById<RecyclerView>(R.id.rvSubCategories)
		scrollStates.append(holder.bindingAdapterPosition, mRecyclerView?.layoutManager?.onSaveInstanceState())
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Category> {
		val binding = TemplateCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Category>(binding) {
			override fun onBindUI(item: Category, position: Int) {
				binding.item = item

				//set icon
				item.media?.icon?.also {
					glide.load(it.file).into(binding.imgIcon)
				}

				//handle sub categories recyclerview
				handleCategories(item)

				//handle click show all
				binding.btnShowAll.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				binding.executePendingBindings()
			}

			private fun handleCategories(category: Category) {
				if (mAdapters[bindingAdapterPosition] == null) {
					val categoryAdapter = CategoryAdapter(recyclerViewTools).apply {
						glide = this@CategoriesAdapter.glide
					}
					category.children?.also {
						categoryAdapter.mItems = it
					}
					mAdapters.append(bindingAdapterPosition, categoryAdapter)

					binding.rvSubCategories.setRecycledViewPool(viewPool)
					binding.rvSubCategories.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
				}

				scrollStates[bindingAdapterPosition]?.let {
					binding.rvSubCategories.layoutManager?.onRestoreInstanceState(it)
				}

				binding.rvSubCategories.adapter = mAdapters[bindingAdapterPosition]
			}
		}
	}
}