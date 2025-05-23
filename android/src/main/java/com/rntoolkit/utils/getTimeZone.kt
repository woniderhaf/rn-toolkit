package com.rntoolkit.utils

import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.TimeZone

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import kotlin.math.abs

fun getTimeZone(): WritableMap {
  var map = Arguments.createMap()

  val timeZone = TimeZone.getDefault()

  map.putString("name", timeZone.id)
  map.putString("offset", getTimeZoneOffset(timeZone.id))

  return map
}


private fun getTimeZoneOffset(timeZoneId: String): String {
  val zoneId = ZoneId.of(timeZoneId)
  val zonedDateTime = ZonedDateTime.now(zoneId)
  val zoneOffset = zonedDateTime.offset
  return zoneOffset.id
}


