package com.tedmob.challenge.applicationtofix.features.authentication.domain

import com.tedmob.challenge.applicationtofix.data.api.MainApi
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class RegisterUseCase(
    private val api: MainApi,
) {
    class Params(
        val firstName: String,
        val lastName: String,
        val username: String,
        val password: String,
    )


    suspend fun execute(
        params: Params,
    ) {
        //...
        delay(1.seconds)
        throw Exception("Feature is not available now")
    }
}