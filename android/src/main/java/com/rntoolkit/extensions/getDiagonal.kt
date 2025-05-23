package com.rntoolkit.extensions

import kotlin.math.sqrt
import android.util.DisplayMetrics
import java.lang.Exception

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun DisplayMetrics.getDiagonal(): Double? {
  return try {
    val widthPixels = this.widthPixels.toDouble()
    val heightPixels = this.heightPixels.toDouble()
    val xdpi = this.xdpi.toDouble()
    val ydpi = this.ydpi.toDouble()


    val widthInches = widthPixels / xdpi
    val heightInches = heightPixels / ydpi

    val diagonal = sqrt(widthInches * widthInches + heightInches * heightInches)
    val df = DecimalFormat("#.###", DecimalFormatSymbols(Locale.US))

    return df.format(diagonal).toDouble()
  } catch (e: Exception) {
    null
  }

}
