package com.zarinfanavaran.domain.extensions

import android.content.Context


fun dp2px(context: Context, dp: Number) = (dp.toFloat() * context.resources.displayMetrics.density + 0.5f).toInt()

fun sp2px(context: Context, sp: Number) = (sp.toFloat() * context.resources.displayMetrics.scaledDensity + 0.5f).toInt()

fun px2dp(context: Context, px: Number) = (px.toFloat() / context.resources.displayMetrics.density + 0.5f).toInt()

fun px2sp(context: Context, px: Number) = (px.toFloat() / context.resources.displayMetrics.scaledDensity + 0.5f).toInt()
