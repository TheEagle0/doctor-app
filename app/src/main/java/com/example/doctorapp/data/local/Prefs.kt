package com.example.doctorapp.data.local

import android.preference.PreferenceManager
import com.example.doctorapp.base.DoctorApp

object Prefs {
    fun saveString(key: String, value: String) {
        PreferenceManager.getDefaultSharedPreferences(DoctorApp.appInstance).edit().putString(key, value).apply()
    }
    fun getString(key: String, default: String = ""): String? {
        return PreferenceManager.getDefaultSharedPreferences(DoctorApp.appInstance).getString(key, default)
    }
}

