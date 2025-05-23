package com.rntoolkit.helpers

import java.util.UUID

fun generateUniqueId(): String {
  // создает уникальный id
  return UUID.randomUUID().toString()
}
