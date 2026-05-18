package com.tedmob.challenge.applicationtofix.utils.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.tedmob.challenge.applicationtofix.data.entity.User
import kotlinx.serialization.json.Json

var SharedPreferences.accessToken: String
    get() = getString("pref_access_token", "").orEmpty()
    set(value) {
        edit { putString("pref_access_token", value) }
    }


const val userDataKey: String = "pref_user"

var SharedPreferences.user: User?
    get() = getString(userDataKey, null)?.let {
        Json.decodeFromString(User.serializer(), it)
    }
    set(value) {
        edit {
            putString(userDataKey, value?.let { Json.encodeToString(User.serializer(), it) })
        }
    }