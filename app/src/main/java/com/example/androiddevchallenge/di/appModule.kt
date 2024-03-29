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
package com.example.androiddevchallenge.di

import androidx.lifecycle.SavedStateHandle
import com.example.androiddevchallenge.data.FakePuppyRepository
import com.example.androiddevchallenge.data.Repository
import com.example.androiddevchallenge.ui.detail.DetailViewModel
import com.example.androiddevchallenge.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { FakePuppyRepository() }

    viewModel { HomeViewModel(get()) }

    viewModel { (savedStateHandle: SavedStateHandle) -> DetailViewModel(savedStateHandle, get()) }
}
