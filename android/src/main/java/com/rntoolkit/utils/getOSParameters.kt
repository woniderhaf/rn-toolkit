package com.rntoolkit.utils

import android.os.Build
import android.content.Context
import android.util.Log

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

import com.rntoolkit.classes.Settings
import com.rntoolkit.extensions.*
import java.lang.Exception

interface OSParameterKeys {
  companion object {
    const val INTERNAL_STORAGE_SIZE = "internal_storage_size"
    const val SYSTEM_LANGUAGE = "system_language"
    const val SCREEN_DIAGONAL = "screen_diagonal"
    const val CAMERA_COUNT = "camera_count"
    const val FONT_SCALE = "font_scale"
    const val AIRPLANE_MODE_ON = "airplane_mode_on"
    const val ANDROID_ID = "device_id"
    const val AUTO_TIME = "auto_time"
    const val BOOT_COUNT = "boot_count"
    const val TIME_12_24 = "time_12_24"
    const val ADB_ENABLED = "adb_enabled"
    const val BLUETOOTH_ON = "bluetooth_on"
    const val DATA_ROAMING = "data_roaming"
    const val USER_ROTATION = "user_rotation"
    const val AUTO_TIME_ZONE = "auto_time_zone"
    const val SCREEN_BRIGHTNESS = "screen_brightness"
    const val SCREEN_BRIGHTNESS_MODE = "screen_brightness_mode"
    const val DEVICE_PROVISIONED = "device_provisioned"
    const val SCREEN_OFF_TIMEOUT = "screen_off_timeout"
    const val DEFAULT_INPUT_METHOD = "default_input_method"
    const val NEXT_ALARM_FORMATTED = "next_alarm_formatted"
    const val ACCESSIBILITY_ENABLED = "accessibility_enabled"
    const val SOUND_EFFECTS_ENABLED = "sound_effects_enabled"
    const val ACCELEROMETER_ROTATION = "accelerometer_rotation"
    const val HAPTIC_FEEDBACK_ENABLED = "haptic_feedback_enabled"
    const val WINDOW_ANIMATION_SCALE = "window_animation_scale"
    const val ANIMATOR_DURATION_SCALE = "animator_duration_scale"
    const val TRANSITION_ANIMATION_SCALE = "transition_animation_scale"
    const val ALWAYS_FINISH_ACTIVITIES = "always_finish_activities"
    const val DEVELOPMENT_SETTINGS_ENABLED = "development_settings_enabled"
    const val INSTALL_NON_MARKET_APPS = "install_non_market_apps"
    const val STAY_ON_WHILE_PLUGGED_IN = "stay_on_while_plugged_in"
    const val ALLOWED_GEOLOCATION_ORIGINS = "allowed_geolocation_origins"
    const val FINGERPRINT = "fingerprint"
    const val DEVICE_NAME = "device_name"
    const val BRAND_NAME = "brand_name"
    const val PRODUCT = "product"
    const val BOARD = "board"
    const val TAGS = "tags"
  }
}


fun getOSParameters(context: Context): Result<WritableMap> {
  try {
    val map = Arguments.createMap()
    val settings = Settings(context)

    getInternalStorageSize(context)?.let { map.putDouble(OSParameterKeys.INTERNAL_STORAGE_SIZE, it) }
    getSystemLanguage()?.let { map.putString(OSParameterKeys.SYSTEM_LANGUAGE, it) }
    getDisplay(context).getDiagonal()?.let { map.putDouble(OSParameterKeys.SCREEN_DIAGONAL, it) }
    getCameraCount(context)?.let { map.putInt(OSParameterKeys.CAMERA_COUNT, it) }

    settings.getAirplaneModeOn()?.let { map.putInt(OSParameterKeys.AIRPLANE_MODE_ON, it) }
    settings.getAndroidId()?.let { map.putString(OSParameterKeys.ANDROID_ID, it) }
    settings.getAutoTime()?.let { map.putInt(OSParameterKeys.AUTO_TIME, it) }
    settings.getBootCount()?.let { map.putInt(OSParameterKeys.BOOT_COUNT, it) }
    settings.getFontScale()?.let { map.putString(OSParameterKeys.FONT_SCALE, it) }
    settings.getTime_12_24()?.let { map.putString(OSParameterKeys.TIME_12_24, it) }
    settings.getAdbEnabled()?.let { map.putInt(OSParameterKeys.ADB_ENABLED, it) }
    settings.getBluetoothOn()?.let { map.putInt(OSParameterKeys.BLUETOOTH_ON, it) }
    settings.getDataRoaming()?.let { map.putInt(OSParameterKeys.DATA_ROAMING, it) }
    settings.getUserRotation()?.let { map.putInt(OSParameterKeys.USER_ROTATION, it) }
    settings.getAutoTimeZone()?.let { map.putInt(OSParameterKeys.AUTO_TIME_ZONE, it) }
    settings.getScreenBrightness()?.let { map.putInt(OSParameterKeys.SCREEN_BRIGHTNESS, it) }
    settings.getScreenBrightnessMode()?.let { map.putInt(OSParameterKeys.SCREEN_BRIGHTNESS_MODE, it) }
    settings.getDeviceProvisioned()?.let { map.putInt(OSParameterKeys.DEVICE_PROVISIONED, it) }
    settings.getScreenOffTimeout()?.let { map.putInt(OSParameterKeys.SCREEN_OFF_TIMEOUT, it) }
    settings.getDefaultInputMethod()?.let { map.putString(OSParameterKeys.DEFAULT_INPUT_METHOD, it) }
    settings.getNextAlarmFormatted()?.let { map.putString(OSParameterKeys.NEXT_ALARM_FORMATTED, it) }
    settings.getAccessibilityEnabled()?.let { map.putInt(OSParameterKeys.ACCESSIBILITY_ENABLED, it) }
    settings.getSoundEffectsEnabled()?.let { map.putInt(OSParameterKeys.SOUND_EFFECTS_ENABLED, it) }
    settings.getAccelerometerRotation()?.let { map.putInt(OSParameterKeys.ACCELEROMETER_ROTATION, it) }
    settings.getHapticFeedbackEnabled()?.let { map.putInt(OSParameterKeys.HAPTIC_FEEDBACK_ENABLED, it) }
    settings.getInstallNonMarketApps()?.let { map.putInt(OSParameterKeys.INSTALL_NON_MARKET_APPS, it) }
    settings.getStayOnWhilePluggedIn()?.let { map.putInt(OSParameterKeys.STAY_ON_WHILE_PLUGGED_IN, it) }
    settings.getAllowedGeolocationOrigins()?.let { map.putString(OSParameterKeys.ALLOWED_GEOLOCATION_ORIGINS, it) }
    settings.getWindowAnimationScale()?.let { map.putDouble(OSParameterKeys.WINDOW_ANIMATION_SCALE, it) }
    settings.getAnimatorDurationScale()?.let { map.putDouble(OSParameterKeys.ANIMATOR_DURATION_SCALE, it) }
    settings.getTransitionAnimationScale()?.let { map.putDouble(OSParameterKeys.TRANSITION_ANIMATION_SCALE, it) }
    settings.getAlwaysFinishActivities()?.let { map.putDouble(OSParameterKeys.ALWAYS_FINISH_ACTIVITIES, it) }
    settings.getDevelopmentSettingsEnabled()?.let { map.putInt(OSParameterKeys.DEVELOPMENT_SETTINGS_ENABLED, it) }

    map.putString(OSParameterKeys.FINGERPRINT, Build.FINGERPRINT)
    map.putString(OSParameterKeys.DEVICE_NAME, Build.MODEL)
    map.putString(OSParameterKeys.BRAND_NAME, Build.BRAND)
    map.putString(OSParameterKeys.PRODUCT, Build.PRODUCT)
    map.putString(OSParameterKeys.BOARD, Build.BOARD)
    map.putString(OSParameterKeys.TAGS, Build.TAGS)

    return Result.success(map)
  } catch (e: Exception) {
    return Result.failure(e)
  }
}
