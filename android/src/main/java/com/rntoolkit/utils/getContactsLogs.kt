package com.rntoolkit.utils

import android.content.ContentResolver
import android.content.Context

import android.database.Cursor

import android.provider.ContactsContract

import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.*

fun getContactsLogs(context: Context, filters: ReadableMap?): WritableArray {
  val contacts = Arguments.createArray()
  val resolver: ContentResolver = context.contentResolver

  // фильтры
  val limit = filters?.getInt("limit") ?: 100

  val cursor: Cursor? = resolver.query(
    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
    arrayOf(
      ContactsContract.Contacts._ID,
      ContactsContract.Contacts.DISPLAY_NAME,
      ContactsContract.CommonDataKinds.Phone.NUMBER,
      ContactsContract.CommonDataKinds.Phone.TYPE
    ),
    null,
    null,
    "${ContactsContract.Contacts.DISPLAY_NAME} ASC LIMIT $limit"
  )

  cursor?.use {
    while (it.moveToNext()) {
      val contact = Arguments.createMap().apply {
        putString("id", it.getString(0))
        putString("name", it.getString(1))
        putString("phone", it.getString(2))
        putInt("phoneType", it.getInt(3))
      }
      contacts.pushMap(contact)
    }
  }

  return contacts
}

