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
package com.example.androiddevchallenge.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.data.Repository
import com.example.androiddevchallenge.ui.detail.DetailScreen
import com.example.androiddevchallenge.ui.home.HomeScreen

object MainDestinations {
    const val HOME = "home"
    const val DETAIL = "detail/{id}"
    const val DETAIL_ARG = "id"
}

@ExperimentalAnimationApi
@Composable
fun NavGraph(repository: Repository) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    NavHost(navController, startDestination = MainDestinations.HOME) {
        composable(MainDestinations.HOME) {
            HomeScreen(
                repository = repository,
                onNext = actions.navigateToDetails
            )
        }
        composable(
            MainDestinations.DETAIL,
            arguments = listOf(
                navArgument(MainDestinations.DETAIL_ARG) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(MainDestinations.DETAIL_ARG)
            id?.let { DetailScreen(id = id, repository = repository, onBack = actions.goBack) }
        }
    }
}

class MainActions(navController: NavHostController) {
    val navigateToDetails: (id: String) -> Unit = {
        navController.navigate("detail/$it")
    }
    val goBack: () -> Unit = {
        navController.popBackStack()
    }
}
