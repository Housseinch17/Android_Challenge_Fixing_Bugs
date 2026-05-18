package com.tedmob.challenge.applicationtofix.features.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.tedmob.challenge.applicationtofix.base.BaseActivity
import com.tedmob.challenge.applicationtofix.features.launch.RootActivity
import com.tedmob.challenge.applicationtofix.theme.AppTheme

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainPage(
                    onRestartApp = ::restartApp,
                )
            }
        }
    }

    private fun restartApp() {
        startActivity(
            Intent(this, RootActivity::class.java)
                .addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                )
        )
    }
}