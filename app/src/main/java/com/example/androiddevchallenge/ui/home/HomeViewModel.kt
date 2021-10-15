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
package com.example.androiddevchallenge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.PuppyData
import com.example.androiddevchallenge.data.Repository
import com.example.androiddevchallenge.data.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val loading: Boolean = false,
    val puppyData: List<PuppyData> = emptyList(),
    val error: String = ""
) {
    /**
     * True if this represents a first load
     */
    val initialLoad: Boolean
        get() = puppyData.isEmpty() && error.isEmpty() && loading
}

class HomeViewModel(val repository: Repository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchPuppyData()
    }

    private fun fetchPuppyData() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val result = repository.fetchAllPuppyData()
            _uiState.update {
                when (result) {
                    is Result.Success -> it.copy(puppyData = result.data, loading = false)
                    is Result.Error -> it.copy(
                        error = result.exception.localizedMessage,
                        loading = false
                    )
                }
            }
        }
    }

    fun refresh() {
        fetchPuppyData()
    }
}
