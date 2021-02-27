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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.PuppyData
import com.example.androiddevchallenge.data.Repository

@Composable
fun DetailScreen(
    id: String,
    repository: Repository,
    onBack: () -> Unit,
    detailViewModel: DetailViewModel = viewModel(factory = DetailViewModelFactory(id, repository))
) {
    val data by detailViewModel.data.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(data?.breed ?: stringResource(R.string.title)) },
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
            data?.let { DetailContent(data = it, modifier = Modifier.padding(innerPadding)) }
        }
    )
}

@Composable
fun DetailContent(data: PuppyData, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = data.imageRes),
            contentDescription = data.breed,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            data.breed,
            style = typography.h6,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Text(
            data.description,
            style = typography.body2,
            modifier = Modifier.padding(bottom = 16.dp)
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
