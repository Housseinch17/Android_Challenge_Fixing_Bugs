package com.tedmob.challenge.applicationtofix.features.authentication.domain

import android.content.SharedPreferences
import com.tedmob.challenge.applicationtofix.data.api.MainApi
import com.tedmob.challenge.applicationtofix.data.entity.User
import com.tedmob.challenge.applicationtofix.utils.prefs.accessToken
import com.tedmob.challenge.applicationtofix.utils.prefs.user

class LoginUseCase(
    private val api: MainApi,
    private val prefs: SharedPreferences,
) {
    class Params(
        val username: String,
        val password: String,
    )


    suspend fun execute(
        params: Params,
    ) {
        val response = api.login(
            params.username,
            params.password,
        )
        prefs.accessToken = response.token
        prefs.user = User(
            "John",
            "Doe",
            params.username,
        )
    }
}