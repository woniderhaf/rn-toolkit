package com.rntoolkit.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import java.lang.SecurityException

fun getCoarseLocation(context: Context): Result<WritableMap>  {
  val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

  // Проверяем наличие разрешения
  if (ActivityCompat.checkSelfPermission(
      context,
      Manifest.permission.ACCESS_COARSE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED
  ) {
    // Разрешение отсутствует, необходимо запросить его у пользователя.
    return Result.failure(SecurityException("ACCESS_COARSE_LOCATION permission not granted"))
  }

  val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

  if(location == null) {
    return Result.failure(SecurityException("location is null"))
  }

  val map = Arguments.createMap()

  map.putDouble("latitude", location.latitude)
  map.putDouble("lontitude", location.longitude)

  return Result.success(map)
}
