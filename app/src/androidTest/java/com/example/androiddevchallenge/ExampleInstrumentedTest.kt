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
package com.example.androiddevchallenge

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androiddevchallenge.data.DataSource
import com.example.androiddevchallenge.data.FakePuppyRepository
import com.example.androiddevchallenge.ui.detail.DetailContent
import com.example.androiddevchallenge.ui.home.HomeContent
import com.example.androiddevchallenge.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalAnimationApi
class ExampleInstrumentedTest {
    @ExperimentalMaterial3Api
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val repository = FakePuppyRepository()

    @ExperimentalMaterial3Api
    @Test
    fun homeScreenTest() {
        composeTestRule.setContent {
            AppTheme {
                HomeContent(list = DataSource.data, onNext = {})
            }
        }
        composeTestRule.onNodeWithText(
            text = DataSource.data.first().breed,
            substring = true
        ).assertExists()
    }

    @ExperimentalMaterial3Api
    @Test
    fun detailScreenTest() {
        composeTestRule.setContent {
            AppTheme {
                DetailContent(data = DataSource.data.first())
            }
        }

        composeTestRule.onNodeWithText(
            text = "Adopt",
            substring = true
        ).assertExists()
    }
}
