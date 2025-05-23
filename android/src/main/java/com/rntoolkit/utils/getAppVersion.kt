package com.rntoolkit.utils

import android.content.Context
import java.lang.Exception

fun getAppVersion(context: Context): String? {
  return try {
    val info = context.packageManager.getPackageInfo(context.packageName, 0)

    info.versionName
  } catch (e: Exception) {
    null
  }
}
