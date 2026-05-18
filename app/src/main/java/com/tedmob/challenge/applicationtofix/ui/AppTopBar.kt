package com.tedmob.challenge.applicationtofix.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@NonRestartableComposable
fun AppTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title,
        modifier,
        navigationIcon,
        actions,
        colors = TopAppBarDefaults.topAppBarColors(
            //...
        ),
    )
}

@Composable
fun AppTopBarBack(
    modifier: Modifier = Modifier,
) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    IconButton(onClick = { onBackPressedDispatcher?.onBackPressed() }, modifier) {
        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
    }
}