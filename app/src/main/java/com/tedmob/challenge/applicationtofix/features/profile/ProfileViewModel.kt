package com.tedmob.challenge.applicationtofix.features.profile

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.utils.prefs.user
import com.tedmob.challenge.applicationtofix.utils.prefs.userDataKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    //...
) : ViewModel() {

    val prefs: SharedPreferences = App.prefs

    private val _user = MutableStateFlow(prefs.user)
    val user = _user.asStateFlow()


    val onPrefsChanged = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        if (key == userDataKey) {
            _user.value = prefs.user
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