package com.rntoolkit.utils

import android.content.Context
import android.os.Build

import android.util.Log
import android.util.DisplayMetrics
import android.view.WindowManager

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

fun getDisplay(context: Context): DisplayMetrics {

    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()

    windowManager.defaultDisplay.getRealMetrics(metrics)

    val height = metrics.heightPixels
    val width = metrics.widthPixels


    val result = "${height}x${width}"

    return metrics
}

