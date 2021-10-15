/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.detail

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.DataSource
import com.example.androiddevchallenge.data.FakePuppyRepository
import com.example.androiddevchallenge.data.PuppyData
import com.example.androiddevchallenge.data.Result
import com.example.androiddevchallenge.ui.components.Loading
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.runBlocking

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel,
    onBack: () -> Unit,
) {
    val data by detailViewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(data.puppyData?.breed ?: stringResource(R.string.title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            DetailScreen(detailsUiState = data, modifier = Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun DetailScreen(detailsUiState: DetailsUiState, modifier: Modifier) {
    when {
        detailsUiState.loading -> Loading()
        detailsUiState.puppyData != null -> DetailContent(data = detailsUiState.puppyData, modifier)
        detailsUiState.error != null -> Text(text = stringResource(R.string.general_error))
    }
}

@Composable
fun DetailContent(data: PuppyData, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(dimensionResource(id = R.dimen.large_padding))
    ) {
        Image(
            painter = painterResource(id = data.imageRes),
            contentDescription = data.breed,
            modifier = Modifier
                .aspectRatio(1.0f)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius))),
            contentScale = ContentScale.Crop
        )
        Text(
            data.breed,
            style = typography.h6,
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.large_padding),
                bottom = dimensionResource(id = R.dimen.large_padding)
            )
        )
        Text(
            data.description,
            style = typography.body2,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_padding))
        )
        AdoptButton()
    }
}

@Composable
fun AdoptButton(modifier: Modifier = Modifier) {
    var adoptState by rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier) {
        Button(onClick = { adoptState = true }, enabled = !adoptState) {
            if (adoptState) {
                Text(text = stringResource(R.string.adopted))
            } else {
                Text(text = stringResource(R.string.adopt))
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview("Details screen")
@Preview("Details screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Details screen (big font)", fontScale = 1.5f)
@Composable
fun PreviewHomeScreen() {
    MyTheme {
        val data = runBlocking {
            (FakePuppyRepository().fetchPuppyData(DataSource.data.first().id) as Result.Success).data
        }
        DetailContent(data = data)
    }
}
