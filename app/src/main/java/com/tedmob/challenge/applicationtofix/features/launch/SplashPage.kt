package com.tedmob.challenge.applicationtofix.features.launch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.R
import com.tedmob.challenge.applicationtofix.theme.AppTheme
import com.tedmob.challenge.applicationtofix.utils.prefs.accessToken
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SplashPage(
    onRedirectToLogin: () -> Unit,
    onRedirectToMain: () -> Unit,
) {
    val prefs = remember { App.prefs }

    SplashUI(
        onAnimationFinished = {
            if (prefs.accessToken.isEmpty()) {
                onRedirectToLogin()
            } else {
                onRedirectToMain()
            }
        },
        Modifier
            .safeDrawingPadding()
            .fillMaxSize(),
    )
}

@Composable
private fun SplashUI(
    onAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Image(
            painterResource(R.drawable.ic_launcher_round),
            null,
            Modifier
                .size(96.dp)
                .align(Alignment.Center),
        )
    }

    LaunchedEffect(Unit) {
        delay(1.5.seconds)
        onAnimationFinished()
    }
}


@Preview(showBackground = true)
@Composable
private fun SplashUI_Preview() {
    AppTheme {
        SplashUI(
            onAnimationFinished = {},
            Modifier.fillMaxSize(),
        )
    }
}