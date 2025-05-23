package com.rntoolkit.utils

import java.util.Locale

fun getSystemLanguage(): String {
  return Locale.getDefault().language
}
