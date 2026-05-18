package com.tedmob.challenge.applicationtofix.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppProgress(
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = {},
        modifier,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Surface(
            shape = AlertDialogDefaults.shape,
        ) {
            CircularProgressIndicator(
                Modifier
                    .padding(24.dp)
                    .wrapContentSize(),
            )
        }
    }
}