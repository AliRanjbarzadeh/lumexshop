package com.zarinfanavaran.presentation.sale

import android.graphics.Paint
import android.os.CountDownTimer
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zarinfanavaran.domain.models.Product
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.base.BaseAdapter
import com.zarinfanavaran.presentation.base.BaseHolder
import com.zarinfanavaran.presentation.databinding.TemplateProductItemSaleBinding

/**
 * Created by Ali Ranjbarzadeh on 10/16/2022 AD.
 */
class SaleProductAdapter(private val recyclerViewTools: RecyclerViewTools) : BaseAdapter<Product>() {

	private val timers = SparseArray<CountDownTimer?>()

	override fun onViewRecycled(holder: BaseHolder<Product>) {
		super.onViewRecycled(holder)

		holder.timer?.cancel()
		timers.put(holder.bindingAdapterPosition, holder.timer)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Product> {
		val binding = TemplateProductItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Product>(binding) {
			override fun onBindUI(item: Product, position: Int) {
				binding.item = item

				//load image
				binding.imgProduct.setImageResource(item.image)

				binding.txtRealPrice.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG

				//click product
				binding.root.setOnClickListener { recyclerViewTools.onItemClick(bindingAdapterPosition, it, item) }

				if (timers[bindingAdapterPosition] != null) {
					timer = timers[bindingAdapterPosition]
					timer?.start()
				} else {
					timer = object : CountDownTimer((item.timerTime * 1000), 1000) {
						override fun onTick(millisUntilFinished: Long) {
							val allSeconds = millisUntilFinished / 1000
							val hour = allSeconds / 3600
							val min = (allSeconds - hour * 3600) / 60
							val second = allSeconds - (hour * 3600) - (min * 60)

							val hourString = if (hour > 0) {
								if (hour < 10) {
									"0$hour"
								} else {
									"$hour"
								}
							} else {
								"00"
							}

							val minString = if (min > 0) {
								if (min < 10) {
									"0$min"
								} else {
									"$min"
								}
							} else {
								"00"
							}

							val secondString = if (second > 0) {
								if (second < 10) {
									"0$second"
								} else {
									"$second"
								}
							} else {
								"00"
							}

							binding.txtTimer.text = "$hourString:$minString:$secondString"
						}

						override fun onFinish() {
							binding.txtTimer.text = "00:00:00"
						}
					}.start()
				}

				binding.executePendingBindings()
			}
		}
	}
}