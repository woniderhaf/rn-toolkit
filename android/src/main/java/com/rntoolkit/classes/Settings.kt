package com.rntoolkit.classes

import com.rntoolkit.Constants
import android.content.Context
import android.os.Build
import android.provider.Settings
import java.lang.Exception
import android.util.Log
import android.text.format.DateFormat
import android.app.AlarmManager

class Settings(private val context: Context) {

  private val resolver = context.getContentResolver()

  private val SDK_INT = Build.VERSION.SDK_INT

  fun getAirplaneModeOn(): Int? = getSystemInt(Settings.System.AIRPLANE_MODE_ON)
  fun getUserRotation(): Int? = getSystemInt(Settings.System.USER_ROTATION)
  fun getScreenBrightness(): Int? = getSystemInt(Settings.System.SCREEN_BRIGHTNESS)
  fun getScreenBrightnessMode(): Int? = getSystemInt(Settings.System.SCREEN_BRIGHTNESS_MODE)
  fun getScreenOffTimeout(): Int? = getSystemInt(Settings.System.SCREEN_OFF_TIMEOUT)
  fun getSoundEffectsEnabled(): Int? = getSystemInt(Settings.System.SOUND_EFFECTS_ENABLED)
  fun getAccelerometerRotation(): Int? = getSystemInt(Settings.System.ACCELEROMETER_ROTATION)

  fun getAndroidId(): String? = getSystemString(Settings.System.ANDROID_ID)
  fun getFontScale(): String? = getSystemString(Settings.System.FONT_SCALE)

  fun getAutoTime(): Int? = getGlobalInt(Settings.Global.AUTO_TIME)
  fun getBootCount(): Int? = getGlobalInt(Settings.Global.BOOT_COUNT)
  fun getAdbEnabled(): Int?  = getGlobalInt(Settings.Global.ADB_ENABLED)
  fun getBluetoothOn(): Int? = getGlobalInt(Settings.Global.BLUETOOTH_ON)
  fun getDataRoaming(): Int? = getGlobalInt(Settings.Global.DATA_ROAMING)
  fun getAutoTimeZone(): Int? = getGlobalInt(Settings.Global.AUTO_TIME_ZONE)
  fun getDeviceProvisioned(): Int? = getGlobalInt(Settings.Global.DEVICE_PROVISIONED)
  fun getStayOnWhilePluggedIn(): Int? = getGlobalInt(Settings.Global.STAY_ON_WHILE_PLUGGED_IN)
  fun getDevelopmentSettingsEnabled(): Int? = getGlobalInt(Settings.Global.DEVELOPMENT_SETTINGS_ENABLED)

  fun getDefaultInputMethod(): String? = getSecureString(Settings.Secure.DEFAULT_INPUT_METHOD)
  fun getAllowedGeolocationOrigins(): String? = getSecureString(Settings.Secure.ALLOWED_GEOLOCATION_ORIGINS)

  fun getTouchExplorationEnabled(): Int? = getSecureInt(Settings.Secure.TOUCH_EXPLORATION_ENABLED)
  fun getAccessibilityEnabled(): Int? = getSecureInt(Settings.Secure.ACCESSIBILITY_ENABLED)

  fun getTime_12_24(): String = if(DateFormat.is24HourFormat(context)) "24" else "12"

  fun getNextAlarmFormatted(): String? {
    return try {
      val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
      alarmManager.getNextAlarmClock()?.let { info ->
        return info.triggerTime.toString()
      } ?: run {
        null
      }
    } catch (e: Exception) {
      null
    }
  }

  fun getHapticFeedbackEnabled(): Int? {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // для версий 33+ нет данного флага,
        // можно косвенно его получить,
        // и это будет не 100% результат
        return null
      } else {
        return getSystemInt(Settings.System.HAPTIC_FEEDBACK_ENABLED)
      }
  }

  fun getInstallNonMarketApps(): Int? {
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Google убрала возможность отслеживать этот флаг с 26 версии API
        return null
      } else {
        return getSecureInt(Settings.Secure.INSTALL_NON_MARKET_APPS)
      }
  }

  fun getWindowAnimationScale(): Double? {
    return try {
    getGlobalFloat(Settings.Global.WINDOW_ANIMATION_SCALE)
      ?.toDouble()
    } catch (e: Exception) {
      null
    }
  }

  fun getAnimatorDurationScale(): Double? {
    return try {
      getGlobalFloat(Settings.Global.ANIMATOR_DURATION_SCALE)
        ?.toDouble()
    } catch (e: Exception) {
      null
    }
  }

  fun getAlwaysFinishActivities(): Double? {
    return try {
      getGlobalFloat(Settings.Global.ALWAYS_FINISH_ACTIVITIES)
        ?.toDouble()
    } catch (e: Exception) {
      null
    }
  }

  fun getTransitionAnimationScale(): Double? {
    return try {
      getGlobalFloat(Settings.Global.TRANSITION_ANIMATION_SCALE)
        ?.toDouble() ?: null
    } catch (e: Exception) {
      null
    }
  }


  // Общий метод для получения System Int значений
  private fun getSystemInt(key: String): Int? = try {
    Settings.System.getInt(resolver, key) ?: null
  } catch (e: Exception) {
    Log.w(Constants.TAG, "Failed to get system int for $key", e)
    null
  }

  // Общий метод для получения Global Int значений
  private fun getGlobalInt(key: String): Int? = try {
    Settings.Global.getInt(resolver, key) ?: null
  } catch (e: Exception) {
    Log.w(Constants.TAG, "Failed to get global int for $key", e)
    null
  }

  // Общий метод для получения Secure Int значений
  private fun getSecureInt(key: String): Int? = try {
    Settings.Secure.getInt(resolver, key) ?: null
  } catch (e: Exception) {
    Log.w(Constants.TAG, "Failed to get secure int for $key", e)
    null
  }

  // Общий метод для получения System String значений
  private fun getSystemString(key: String): String? = try {
    Settings.System.getString(resolver, key) ?: null
  } catch (e: Exception) {
    Log.w(Constants.TAG, "Failed to get system string for $key", e)
    null
  }

  // Общий метод для получения Secure String значений
  private fun getSecureString(key: String): String? = try {
    Settings.Secure.getString(resolver, key) ?: null
  } catch (e: Exception) {
    Log.w(Constants.TAG, "Failed to get secure string for $key", e)
    null
  }

  // Общий метод для получения Global Float значений
  private fun getGlobalFloat(key: String): Float? = try {
    Settings.Global.getFloat(resolver, key) ?: null
  } catch (e: Exception) {
    Log.w(Constants.TAG, "Failed to get global float for $key", e)
    null
  }

}
