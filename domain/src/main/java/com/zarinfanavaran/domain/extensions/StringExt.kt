package com.zarinfanavaran.domain.extensions

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.zarinfanavaran.domain.util.CustomTypefaceSpan

/**
 * Created by Ali Ranjbarzadeh on 10/21/2022 AD.
 */

fun spannableString(
	mContext: Context,

	firstString: String = "",
	@ColorRes firstColor: Int = 0,
	@DimenRes firstSize: Int = 0,
	firstFont: String = "",

	secondString: String = "",
	@ColorRes secondColor: Int = 0,
	@DimenRes secondSize: Int = 0,
	secondFont: String = "",

	thirdString: String = "",
	@ColorRes thirdColor: Int = 0,
	@DimenRes thirdSize: Int = 0,
	thirdFont: String = "",

	fourthString: String = "",
	@ColorRes fourthColor: Int = 0,
	@DimenRes fourthSize: Int = 0,
	fourthFont: String = "",

	fifthString: String = "",
	@ColorRes fifthColor: Int = 0,
	@DimenRes fifthSize: Int = 0,
	fifthFont: String = "",
): SpannableString {
	val totalStringPart1 = firstString + secondString
	val endFirst = firstString.length
	val endSecond = totalStringPart1.length
	val totalStringPart2 = totalStringPart1 + thirdString
	val endThird = totalStringPart2.length
	val totalStringPart3 = totalStringPart2 + fourthString
	val endFourth = totalStringPart3.length
	val totalStringPart4 = totalStringPart3 + fifthString
	val endFifth = totalStringPart4.length
	val wordToSpan = SpannableString(totalStringPart4)

	if (firstColor != 0)
		wordToSpan.setSpan(
			ForegroundColorSpan(ContextCompat.getColor(mContext, firstColor)),
			0,
			endFirst,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (firstSize != 0)
		wordToSpan.setSpan(
			AbsoluteSizeSpan(mContext.resources.getDimension(firstSize).toInt()),
			0,
			endFirst,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (firstFont.isNotEmpty()) {
		val font1 = Typeface.createFromAsset(mContext.assets, firstFont)
		wordToSpan.setSpan(
			CustomTypefaceSpan("", font1),
			0,
			endFirst,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)
	}

	if (secondColor != 0)
		wordToSpan.setSpan(
			ForegroundColorSpan(ContextCompat.getColor(mContext, secondColor)),
			endFirst,
			endSecond,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (secondSize != 0)
		wordToSpan.setSpan(
			AbsoluteSizeSpan(mContext.resources.getDimension(secondSize).toInt()),
			endFirst,
			endSecond,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (secondFont.isNotEmpty()) {
		val font2 = Typeface.createFromAsset(mContext.assets, secondFont)
		wordToSpan.setSpan(
			CustomTypefaceSpan("", font2),
			endFirst,
			endSecond,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)
	}

	if (thirdColor != 0)
		wordToSpan.setSpan(
			ForegroundColorSpan(ContextCompat.getColor(mContext, thirdColor)),
			endSecond,
			endThird,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (thirdSize != 0)
		wordToSpan.setSpan(
			AbsoluteSizeSpan(mContext.resources.getDimension(thirdSize).toInt()),
			endSecond,
			endThird,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (thirdFont.isNotEmpty()) {
		val font3 = Typeface.createFromAsset(mContext.assets, thirdFont)
		wordToSpan.setSpan(
			CustomTypefaceSpan("", font3),
			endSecond,
			endThird,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)
	}

	if (fourthColor != 0)
		wordToSpan.setSpan(
			ForegroundColorSpan(ContextCompat.getColor(mContext, fourthColor)),
			endThird,
			endFourth,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (fourthSize != 0)
		wordToSpan.setSpan(
			AbsoluteSizeSpan(mContext.resources.getDimension(fourthSize).toInt()),
			endThird,
			endFourth,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (fourthFont.isNotEmpty()) {
		val font4 = Typeface.createFromAsset(mContext.assets, fourthFont)
		wordToSpan.setSpan(
			CustomTypefaceSpan("", font4),
			endThird,
			endFourth,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)
	}

	if (fifthColor != 0)
		wordToSpan.setSpan(
			ForegroundColorSpan(ContextCompat.getColor(mContext, fifthColor)),
			endFourth,
			endFifth,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (fifthSize != 0)
		wordToSpan.setSpan(
			AbsoluteSizeSpan(mContext.resources.getDimension(fifthSize).toInt()),
			endFourth,
			endFifth,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)

	if (fifthFont.isNotEmpty()) {
		val font5 = Typeface.createFromAsset(mContext.assets, fifthFont)
		wordToSpan.setSpan(
			CustomTypefaceSpan("", font5),
			endFourth,
			endFifth,
			Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
		)
	}

	return wordToSpan
}

fun String.isMobile(): Boolean = this.matches("09\\d{9}".toRegex())

fun String.toEnglish(): String {
	val chars = CharArray(length)
	for (i in 0 until length) {
		var ch: Char = get(i)
		if (ch.toInt() in 0x0660..0x0669) {
			ch -= 0x0660.toChar() - '0'.toInt().toChar()
		} else if (ch.toInt() in 0x06f0..0x06F9) {
			ch -= 0x06f0.toChar() - '0'.toInt().toChar()
		}
		chars[i] = ch
	}
	return String(chars)
}