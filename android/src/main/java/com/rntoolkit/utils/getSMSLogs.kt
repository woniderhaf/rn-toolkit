package com.rntoolkit.utils

import android.content.Context
import com.facebook.react.bridge.*
import android.Manifest
import android.content.pm.PackageManager
import com.facebook.react.bridge.ReadableMap
import android.net.Uri

fun getSMSLogs(context: Context): WritableArray {
  val smsList = Arguments.createArray()
  val resolver = context.contentResolver


  val cursor = resolver.query(
    Uri.parse("content://sms/inbox"),
    arrayOf(
      "_id",
      "address",
      "body",
      "date",
      "type"
    ),
    null,
    null,
    null
  )

  cursor?.use {
    while (it.moveToNext()) {
      val sms = Arguments.createMap().apply {
        putString("id", it.getString(0))
        putString("sender", it.getString(1))
        putString("message", it.getString(2))
        putDouble("date", it.getLong(3).toDouble())
        putInt("type", it.getInt(4))
      }
      smsList.pushMap(sms)
    }
  }

  return smsList
}

