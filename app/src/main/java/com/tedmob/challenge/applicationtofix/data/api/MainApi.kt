@file:OptIn(ExperimentalSerializationApi::class)

package com.tedmob.challenge.applicationtofix.data.api

import com.tedmob.challenge.applicationtofix.data.api.dto.BreedDetailsResponseDTO
import com.tedmob.challenge.applicationtofix.data.api.dto.BreedsResponseDTO
import com.tedmob.challenge.applicationtofix.data.api.dto.LoginResponseDTO
import com.tedmob.challenge.applicationtofix.utils.api.ApiCaller
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

/**
 * Part of the challenge, don't change this file.
 */
class MainApi(
    okHttpClient: OkHttpClient,
    json: Json,
) : ApiCaller(okHttpClient, json) {

    //fixme some of these are fake apis

    private val baseUrl = "https://dogapi.dog/api/v2/".toHttpUrl()


    suspend fun login(
        username: String,
        password: String,
    ): LoginResponseDTO {
        /*val response = okHttpClient.newCall(
            Request(
                baseUrl.resolve("login")!!,
                method = "POST",
                body = buildJSONRequestBody {
                    put("username", username)
                    put("password", password)
                },
            )
        ).executeAsync()

        return json.decodeFromBufferedSource(response.body.source())*/

        delay(2.seconds)
        return LoginResponseDTO(
            "test_token",
        )
    }

    suspend fun breeds(): BreedsResponseDTO {
        val response = okHttpClient.newCall(
            Request(
                baseUrl.resolve("breeds")!!,
                method = "GET",
            )
        ).executeAsyncIfSuccessful()

        return json.decodeFromBufferedSource(response.body.source())
    }

    suspend fun breed(id: String): BreedDetailsResponseDTO {
        val response = okHttpClient.newCall(
            Request(
                baseUrl.newBuilder("breeds")!!
                    .addPathSegment(id)
                    .build(),
                method = "GET",
            )
        ).executeAsyncIfSuccessful()
        return json.decodeFromBufferedSource(response.body.source())
    }

    suspend fun logout() {
        delay(3.seconds)
        if (Random.nextInt(0, 4) == 3) {
            throw Exception("An error occurred.")
        }
    }

    //...
}