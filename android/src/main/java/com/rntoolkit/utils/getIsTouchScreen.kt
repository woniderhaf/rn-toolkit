package com.rntoolkit.utils

import android.content.Context
import android.content.pm.PackageManager


fun getIsTouchScreen(context: Context): Boolean {
  return context.packageManager.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
}

