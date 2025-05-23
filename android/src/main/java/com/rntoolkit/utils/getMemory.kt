package com.rntoolkit.utils

import android.content.Context
import android.app.ActivityManager
import java.util.Locale

fun getMemory(context: Context): Double {

  val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
  val memoryInfoObject = ActivityManager.MemoryInfo()
  activityManager.getMemoryInfo(memoryInfoObject)

  activityManager.getMemoryInfo(memoryInfoObject)

  val totalGB = memoryInfoObject.totalMem.toDouble() / (1000.0 * 1000.0 * 1000.0)

  val formatted = String.format(Locale.US, "%.1f", totalGB)

  return formatted.toDouble()
}
