package com.rntoolkit.utils

import android.content.Context
import android.hardware.camera2.CameraManager

fun getCameraCount(context: Context): Int? {
  return  try {
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    cameraManager.cameraIdList.size
  } catch(e: Exception)  {
    null
  }
}
