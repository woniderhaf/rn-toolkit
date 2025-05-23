package com.rntoolkit.utils

import android.content.Context
import android.content.Intent
import android.content.IntentFilter

import android.os.BatteryManager

fun getBatteryInfo(context: Context): Map<String, String?>? {
  val battryIntent = getBatteryIntent(context)
  if(battryIntent != null) {
    val batteryLevel = getLevel(battryIntent)
    val batteryState = getState(battryIntent)
    return  mapOf("battery_level" to batteryLevel, "battery_state" to batteryState)
  } else {
    return null
  }
}

fun getLevel(batteryIntent: Intent): String? {
  val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
  val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

  return if (level >= 0 && scale > 0) {
    (level * 100.0 / scale).toString()
  } else {
    null
  }
}

fun getState(batteryIntent: Intent) : String? {
  return try {
    val status = batteryIntent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1

    when (status) {
      BatteryManager.BATTERY_STATUS_CHARGING -> {
        "charging"
      }
      BatteryManager.BATTERY_STATUS_DISCHARGING -> {
        "unplugged"
      }
      BatteryManager.BATTERY_STATUS_FULL -> {
        "full"
      }
      else -> {
        null
      }
    }
  } catch (e: Exception) {
    null
  }
}

private fun getBatteryIntent(context: Context): Intent? {
  return context.registerReceiver(
    null,
    IntentFilter(Intent.ACTION_BATTERY_CHANGED)
  )
}
