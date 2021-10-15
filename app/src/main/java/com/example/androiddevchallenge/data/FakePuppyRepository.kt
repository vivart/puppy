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
package com.example.androiddevchallenge.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakePuppyRepository : Repository {
    private var requestCount = 0

    override suspend fun fetchAllPuppyData() = fakeNetworkCall(DataSource.data)

    override suspend fun fetchPuppyData(id: String) =
        fakeNetworkCall(DataSource.data.first { it.id == id })

    override fun getPuppyData(id: String): PuppyData {
        return DataSource.data.first { it.id == id }
    }

    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0

    private suspend fun <T> fakeNetworkCall(result: T) =
        withContext(Dispatchers.IO) {
            delay(800)
            if (shouldRandomlyFail()) {
                Result.Error(IllegalStateException())
            } else {
                Result.Success(result)
            }
        }
}
