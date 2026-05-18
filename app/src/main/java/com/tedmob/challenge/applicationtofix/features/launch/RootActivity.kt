package com.tedmob.challenge.applicationtofix.features.launch

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.tedmob.challenge.applicationtofix.base.BaseActivity
import com.tedmob.challenge.applicationtofix.features.home.MainActivity
import com.tedmob.challenge.applicationtofix.theme.AppTheme

class RootActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                RootPage(
                    onRedirectToMain = ::redirectToMain,
                )
            }
        }
    }


    private fun redirectToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}