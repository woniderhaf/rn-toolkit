package com.rntoolkit.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.content.Context
import android.database.Cursor
import android.provider.CallLog
import com.facebook.react.bridge.ReadableMap
import android.os.Build
import com.facebook.react.bridge.*

fun getCallLogs(context: Context): WritableArray {
  val logs = Arguments.createArray()
  val resolver: ContentResolver = context.contentResolver



  val projection = arrayOf(
    CallLog.Calls.NUMBER,
    CallLog.Calls.TYPE,
    CallLog.Calls.DATE,
    CallLog.Calls.DURATION,
    CallLog.Calls.CACHED_NAME
  )

  val cursor = resolver.query(
    CallLog.Calls.CONTENT_URI,
    projection,
    null,
    null,
    null
  )

  cursor?.use {
    while (it.moveToNext()) {
      val log = Arguments.createMap().apply {
        putString("number", cursor.getString(0))
        putInt("type", cursor.getInt(1)) // 1=входящий, 2=исходящий, 3=пропущенный
        putDouble("date", cursor.getLong(2).toDouble())
        putInt("duration", cursor.getInt(3))
        putString("name", cursor.getString(4) ?: "")
      }
      logs.pushMap(log)
    }
  }

  return logs
}
