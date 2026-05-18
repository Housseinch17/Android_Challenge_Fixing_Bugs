package com.tedmob.challenge.applicationtofix.features.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.utils.prefs.user
import com.tedmob.challenge.applicationtofix.utils.prefs.userDataKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    //...
) : ViewModel() {

    val prefs: SharedPreferences = App.prefs

    private val _username = MutableStateFlow(prefs.user?.username)
    val username = _username.asStateFlow()


    val onPrefsChanged = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        if (key == userDataKey) {
            _username.value = prefs.user?.username
        }
    }

    init {
        prefs.registerOnSharedPreferenceChangeListener(onPrefsChanged)
    }

    override fun onCleared() {
        super.onCleared()
        prefs.unregisterOnSharedPreferenceChangeListener(onPrefsChanged)
    }
}