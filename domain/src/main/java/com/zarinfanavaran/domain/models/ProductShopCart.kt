package com.zarinfanavaran.domain.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zarinfanavaran.domain.BR
import com.zarinfanavaran.domain.extensions.priceFormat
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Created by Ali Ranjbarzadeh on 10/22/2022 AD.
 */
@Parcelize
data class ProductShopCart(
	val title: String,
	val price: Int,
	val discount: Int,
	@DrawableRes val image: Int,
	var _count: Int = 0,
	var maxCount: Int = 0,
	var colorName: String = "",
	var colorValue: String = "#000000",
	var warranty: String = "",
) : BaseObservable(), Parcelable {

	@IgnoredOnParcel
	var count = _count
		@Bindable get() = _count
		set(value) {
			field = value
			_count = value
			notifyPropertyChanged(BR.count)
			notifyPropertyChanged(BR.countString)
		}

	@IgnoredOnParcel
	val countString: String
		@Bindable get() = count.toString()

	fun getFormatRealPrice(): String {
		return price.priceFormat()
	}

	fun getFormatPrice(): String {
		return price.priceFormat()
	}

	fun getDiscountPercent(): String {
		return "${((discount * 100) / price)}%";
	}

	fun getFormatDiscount(): String {
		return discount.priceFormat("تومان تخفیف");
	}
}
