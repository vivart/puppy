package com.example.androiddevchallenge.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
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
            arguments = listOf(navArgument(MainDestinations.DETAIL_ARG) {
                type = NavType.StringType
            })
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