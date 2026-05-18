package com.tedmob.challenge.applicationtofix.utils.challenge

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Part of the challenge, don't change this file.
 */
class ChallengeInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if (
            request.url.pathSegments.lastOrNull() == "breeds" &&
            request.headers["access-token"].isNullOrEmpty()
        ) {
            Response.Builder()
                .code(401)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("Missing access token")
                .body("Missing access token".toResponseBody())
                .build()
        } else {
            chain.proceed(request)
        }
    }
}