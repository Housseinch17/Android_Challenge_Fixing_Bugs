package com.tedmob.challenge.applicationtofix.features.settings

import android.content.SharedPreferences
import com.tedmob.challenge.applicationtofix.data.api.MainApi
import com.tedmob.challenge.applicationtofix.utils.prefs.accessToken
import com.tedmob.challenge.applicationtofix.utils.prefs.user

class LogoutUseCase(
    private val prefs: SharedPreferences,
    private val api: MainApi,
) {

    suspend fun execute() {
        try {
            api.logout()
            prefs.accessToken = ""
            prefs.user = null
        }catch (e: Exception){
            throw e
        }
    }
}