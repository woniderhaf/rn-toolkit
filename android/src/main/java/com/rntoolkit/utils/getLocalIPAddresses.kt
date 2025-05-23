package com.rntoolkit.utils

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

import java.net.NetworkInterface
import java.net.Inet6Address
import java.net.Inet4Address

fun getLocalIPAddresses(): WritableMap {
  var map = Arguments.createMap()

  try {
    val interfaces = NetworkInterface.getNetworkInterfaces()
    while (interfaces.hasMoreElements()) {
      val networkInterface = interfaces.nextElement()

      for (address in networkInterface.inetAddresses) {
        if (!address.isLoopbackAddress) {
          when (address) {
            is Inet4Address -> map.putString("ipv4",address.hostAddress)
            is Inet6Address -> map.putString("ipv6",address.hostAddress)
          }
        }
      }
    }
  } catch (e: Exception) {
    map.putNull("ipv4")
    map.putNull("ipv6")
  }

  return map
}
