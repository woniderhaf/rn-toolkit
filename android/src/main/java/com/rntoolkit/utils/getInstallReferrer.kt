package com.rntoolkit.utils

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import java.lang.Exception
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


fun getInstallReferrer(context: Context): Result<WritableMap> {
  return try {
    val referrerClient = InstallReferrerClient.newBuilder(context).build()
    val future = CompletableFuture<Result<WritableMap>>()

    referrerClient.startConnection(object : InstallReferrerStateListener {
      override fun onInstallReferrerSetupFinished(responseCode: Int) {
        when (responseCode) {
          InstallReferrerClient.InstallReferrerResponse.OK -> {
            try {
              val response: ReferrerDetails = referrerClient.installReferrer
              val result = createResultMap(response)
              future.complete(Result.success(result))
            } catch (e: Exception) {
              future.complete(Result.failure(Exception("Failed to get referrer details: ${e.message}")))
            }

          }
          InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
            future.complete(Result.failure(Exception("API not supported")))
          }
          InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
            future.complete(Result.failure(Exception("Service unavailable")))
          }
          else -> {
            future.complete(Result.failure(Exception("Unkown error: $responseCode")))
          }
        }
        referrerClient.endConnection()
      }

      override fun onInstallReferrerServiceDisconnected() {
        future.complete(Result.failure(Exception("Service disconnected")))
      }
    })

    try {
      future.get(10, TimeUnit.SECONDS)
    } catch (e: Exception) {
      referrerClient.endConnection()
      return Result.failure(Exception("Timeout or other error during connection: ${e.message}"))
    }
  } catch (e: Exception) {
    return Result.failure(e)
  }
}

fun createResultMap(details: ReferrerDetails): WritableMap {
  return Arguments.createMap().apply {
    putString("installReferrer", details.installReferrer)
    putLong("referrerClickTimestampSeconds", details.referrerClickTimestampSeconds)
    putLong("installBeginTimestampSeconds", details.installBeginTimestampSeconds)
    putString("installVersion", details.installVersion)
    putBoolean("googlePlayInstantParam", details.googlePlayInstantParam)
  }
}
