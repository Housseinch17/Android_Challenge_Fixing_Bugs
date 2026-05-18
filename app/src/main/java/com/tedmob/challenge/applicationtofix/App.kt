package com.tedmob.challenge.applicationtofix

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.tedmob.challenge.applicationtofix.data.api.MainApi
import com.tedmob.challenge.applicationtofix.utils.challenge.ChallengeInterceptor
import com.tedmob.challenge.applicationtofix.utils.prefs.accessToken
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.time.Duration.Companion.seconds

class App : Application() {

    companion object {
        lateinit var mainApi: MainApi
        lateinit var prefs: SharedPreferences

        val apiJson = Json {
            encodeDefaults = true
            explicitNulls = false
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }


    override fun onCreate() {
        super.onCreate()
        setupPrefs()
        setupOkHttpClient()
    }


    private fun setupPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
    }

    private fun setupOkHttpClient() {
        mainApi = MainApi(
            OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder()
                        .apply {
                            if (prefs.accessToken.isNotEmpty()) {
                                header("access-token", prefs.accessToken)
                            }
                        }
                        .build()

                    it.proceed(request)
                }
                .addInterceptor(ChallengeInterceptor()) //fixme don't remove this, assume it is part of the api
                .apply {
                    if (BuildConfig.DEBUG) {
                        addNetworkInterceptor(
                            HttpLoggingInterceptor { message ->
                                Log.d("OkHttp", message)
                            }.apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            }
                        )
                    }
                }
                .callTimeout(20.seconds)
                .build(),
            apiJson,
        )
    }
}