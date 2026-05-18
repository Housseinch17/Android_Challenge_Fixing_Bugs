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
            throw Exception(errorBody.takeIf { it.isNotBlank() } ?: response.toErrorMessage())
        }
    }

    private fun Response.toErrorMessage(): String = when (code) {
        400 -> "Bad request"
        401 -> "Unauthorized"
        403 -> "Forbidden"
        404 -> "Breed not found"
        500 -> "Internal Server error"
        502 -> "Bad gateway"
        503 -> "Service Unavailable"
        else -> this.message
    }

    protected fun buildJSONRequestBody(build: JsonObjectBuilder.() -> Unit): RequestBody =
        buildJsonObject(build)
            .let { json.encodeToString(it) }
            .toRequestBody("application/json".toMediaTypeOrNull())
}