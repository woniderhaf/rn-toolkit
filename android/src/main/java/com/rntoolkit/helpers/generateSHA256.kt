package com.rntoolkit.helpers

import java.security.MessageDigest
import kotlin.math.sin

fun generateSHA256(input: ByteArray): String {
  val digest = MessageDigest.getInstance("SHA-256")
  val hash = digest.digest(input)
  // Конвертируем байты в hex-строку
  return hash.fold("") { str, byte -> str + "%02x".format(byte) }
}
