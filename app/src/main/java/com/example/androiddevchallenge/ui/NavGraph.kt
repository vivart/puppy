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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androiddevchallenge.ui.detail.DetailScreen
import com.example.androiddevchallenge.ui.detail.DetailViewModel
import com.example.androiddevchallenge.ui.home.HomeScreen
import org.koin.androidx.compose.getStateViewModel

/**
 * List of screens
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
        const val NAV_ARG = "id"
    }
}

@ExperimentalAnimationApi
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = actions.navigateToDetails
            )
        }
        composable(
            Screen.Detail.route,
            arguments = listOf(
                navArgument(Screen.Detail.NAV_ARG) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.let {
                val viewModel: DetailViewModel = getStateViewModel(state = { it })
                DetailScreen(
                    detailViewModel = viewModel,
                    onBack = actions.goBack
                )
            }
        }
    }
}

class MainActions(navController: NavHostController) {
    val navigateToDetails: (id: String) -> Unit = {
        navController.navigate(Screen.Detail.createRoute(it))
    }
    val goBack: () -> Unit = {
        navController.popBackStack()
    }
}
