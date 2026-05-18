package com.tedmob.challenge.applicationtofix.features.breeds

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tedmob.challenge.applicationtofix.data.entity.Breed
import com.tedmob.challenge.applicationtofix.theme.AppTheme

@Composable
fun BreedsPage(
    onSelectedBreed: (item: Breed) -> Unit,
) {
    val viewModel = viewModel<BreedsViewModel>()
    val pageState by viewModel.state.collectAsStateWithLifecycle()

    when {
        pageState.isLoading -> {
            Box(
                Modifier
                    .safeDrawingPadding()
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        pageState.error != null -> {
            Column(
                Modifier
                    .safeDrawingPadding()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    pageState.error.orEmpty(),
                    textAlign = TextAlign.Center,
                )
                Button(onClick = { viewModel.getBreeds() }) {
                    Text("Retry")
                }
            }
        }

        pageState.data != null -> {
            BreedsUI(
                pageState.data.orEmpty(),
                onSelected = onSelectedBreed,
                Modifier
                    .safeDrawingPadding()
                    .fillMaxSize(),
            )
        }

        else -> {}
    }

    LaunchedEffect(Unit) {
        viewModel.getBreeds()
    }
}


@Composable
private fun BreedsUI(
    items: List<Breed>,
    onSelected: (item: Breed) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(items) {
            BreedItemComponent(
                it,
                Modifier
                    .clickable { onSelected(it) }
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun BreedItemComponent(
    item: Breed,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            Modifier.fillMaxWidth(),
        ) {
            Text(
                item.name,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                item.description,
                style = MaterialTheme.typography.bodyLarge,
            )
            Icon(Icons.AutoMirrored.Default.ArrowForward, null)
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BreedsUI_Preview() {
    AppTheme {
        BreedsUI(
            listOf(
                Breed(
                    "1",
                    "Breed 1",
                    "Description 1",
                ),
                Breed(
                    "2",
                    "Breed 2",
                    "Description 2",
                ),
                Breed(
                    "3",
                    "Breed 3",
                    "Description 3",
                ),
            ),
            onSelected = {},
            Modifier.fillMaxSize(),
        )
    }
}