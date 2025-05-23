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

fun getCallLogs(context: Context, filters: ReadableMap?): WritableArray {
  val logs = Arguments.createArray()
  val resolver: ContentResolver = context.contentResolver

  // параметры фильтрации
  var minDate = filters?.getDouble("minDate")?.toLong() ?: 0L
  val limit = filters?.getInt("limit") ?: 100

  val selection = if (minDate > 0) {
    "${CallLog.Calls.DATE} > ?"
  } else {
    null
  }

  val selectionArgs = if (minDate > 0) {
    arrayOf(minDate.toString())
  } else {
    null
  }

  val projection = arrayOf(
    CallLog.Calls.NUMBER,
    CallLog.Calls.TYPE,
    CallLog.Calls.DATE,
    CallLog.Calls.DURATION,
    CallLog.Calls.CACHED_NAME
  )

  val sortOrder = "${CallLog.Calls.DATE} DESC LIMIT $limit"


  val cursor = resolver.query(
    CallLog.Calls.CONTENT_URI,
    projection,
    selection,
    selectionArgs,
    sortOrder
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
