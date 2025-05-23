package com.rntoolkit.extensions

import android.util.DisplayMetrics

fun DisplayMetrics.getScreenResolution(): String {
  val widthPixels = this.widthPixels
  val heightPixels = this.heightPixels

  return "${heightPixels}x${widthPixels}"
}
