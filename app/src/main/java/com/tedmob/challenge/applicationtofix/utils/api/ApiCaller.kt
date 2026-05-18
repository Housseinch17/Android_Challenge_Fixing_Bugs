package com.tedmob.challenge.applicationtofix.utils.api

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.coroutines.executeAsync

abstract class ApiCaller(
    protected val okHttpClient: OkHttpClient,
    protected val json: Json,
) {

    //...

    protected suspend fun Call.executeAsyncIfSuccessful(): Response {
        val response = executeAsync()
        return if (response.isSuccessful) {
            response
        } else {
            val errorBody = response.body.use { it.string() }
            throw Exception(errorBody.takeIf { it.isNotBlank() } ?: response.message)
        }
    }

    protected fun buildJSONRequestBody(build: JsonObjectBuilder.() -> Unit): RequestBody =
        buildJsonObject(build)
            .let { json.encodeToString(it) }
            .toRequestBody("application/json".toMediaTypeOrNull())
}