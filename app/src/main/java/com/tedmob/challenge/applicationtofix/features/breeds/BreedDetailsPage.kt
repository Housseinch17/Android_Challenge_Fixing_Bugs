package com.tedmob.challenge.applicationtofix.features.breeds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tedmob.challenge.applicationtofix.data.entity.BreedDetails
import com.tedmob.challenge.applicationtofix.theme.AppTheme
import com.tedmob.challenge.applicationtofix.ui.AppTopBar
import com.tedmob.challenge.applicationtofix.ui.AppTopBarBack

@Composable
fun BreedDetailsPage(
    id: String,
) {
    val viewModel = viewModel<BreedDetailsViewModel>()
    val pageState by viewModel.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
    ) {
        AppTopBar(
            title = { Text("Breed Details") },
            Modifier.fillMaxWidth(),
            navigationIcon = { AppTopBarBack() },
        )
        Box(
            Modifier
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                    )
                )
                .weight(1f)
                .fillMaxWidth()
        ) {
            when {
                pageState.isLoading -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                pageState.error != null -> {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            pageState.error.orEmpty(),
                            textAlign = TextAlign.Center,
                        )
                        Button(onClick = { viewModel.getBreed(id) }) {
                            Text("Retry")
                        }
                    }
                }

                pageState.data != null -> {
                    BreedDetailsUI(
                        pageState.data!!,
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize(),
                    )
                }

                else -> {}
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getBreed(id)
    }
}


@Composable
private fun BreedDetailsUI(
    item: BreedDetails,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(24.dp),
    ) {
        Text(
            item.name,
            style = MaterialTheme.typography.headlineSmall,
            fontStyle = FontStyle.Italic,
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "Description:",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            item.description,
            style = MaterialTheme.typography.bodySmall,
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "Average Lifetime:",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            "Minimum: ${item.minLife} years",
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            "Maximum: ${item.maxLife} years",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(Modifier.height(16.dp))

        if (item.isHypoAllergenic) {
            Row {
                Icon(Icons.Default.Warning, null)
                Spacer(Modifier.width(8.dp))
                Text(
                    "Hypo-Allergenic",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BreedDetailsUI_Preview() {
    AppTheme {
        BreedDetailsUI(
            BreedDetails(
                "1",
                "Breed 1",
                "Description 1",
                "14",
                "16",
                true,
            ),
            Modifier.fillMaxSize(),
        )
    }
}