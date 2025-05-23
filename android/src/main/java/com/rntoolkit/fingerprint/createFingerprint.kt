package com.rntoolkit.fingerprint

import com.rntoolkit.utils.*
import com.rntoolkit.extensions.*
import com.rntoolkit.helpers.generateCanvasFingerprint

import android.os.Build
import android.content.Context
import android.util.Log

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import java.lang.Exception
import java.lang.Runtime
import java.lang.RuntimeException


fun createFingerprint(context: Context): Result<WritableMap> {
  try {
    var map = Arguments.createMap()
    var addreses = getLocalIPAddresses()
    map.putString("ip_address_v4", addreses.getString("ipv4"))
    map.putString("ip_address_v6", addreses.getString("ipv6"))

    map.putString("screen_resolution", "${getDisplay(context).getScreenResolution()}")
    map.putString("os_language", getSystemLanguage())
    map.putDouble("device_ram", getMemory(context))

    getBatteryInfo(context)?.let { batteryInfo ->
      batteryInfo["battery_level"]?.let {map.putString("battery_level", it)}
      batteryInfo["battery_state"]?.let {map.putString("battery_charging", it)}
    }

    map.putMap("timezone", getTimeZone())

    map.putString("platform", Build.SUPPORTED_ABIS.firstOrNull())

    map.putInt("hardware_concurrency", Runtime.getRuntime().availableProcessors())

    map.putBoolean("touch_support", getIsTouchScreen(context))

    generateCanvasFingerprint()?.let { map.putString("hash_canvas", it) }

    return Result.success(map)
  } catch (e: Exception) {
    return Result.failure(e)
  }
}


