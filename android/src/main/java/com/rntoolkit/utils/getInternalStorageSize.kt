package com.rntoolkit.utils

import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import android.os.StatFs

fun getInternalStorageSize(context: Context): Double? {
   try {
    val path = Environment.getDataDirectory()
    val stat = StatFs(path.path)

    val blockSize: Long
    val totalBlocks: Long
    val availableBlocks: Long

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      blockSize = stat.blockSizeLong
      totalBlocks = stat.blockCountLong
      availableBlocks = stat.availableBlocksLong
    } else {
      blockSize = stat.blockSize.toLong()
      totalBlocks = stat.blockCount.toLong()
      availableBlocks = stat.availableBlocks.toLong()
    }

    val totalSpace = blockSize * totalBlocks

    return totalSpace.toDouble()
  } catch (e: IllegalArgumentException) {
    e.printStackTrace()
    return null
  } catch (e: SecurityException) {
    e.printStackTrace()
    return null
  }
}

