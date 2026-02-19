package com.example.motivation.repository

import android.content.Context

class SecurePreference(context: Context) {

    private val shared = context.getSharedPreferences("Motivation", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        shared.edit().putString(key, value).apply()
    }


    fun getString(key: String): String {
      return  shared.getString(key, "") ?: ""
    }


}