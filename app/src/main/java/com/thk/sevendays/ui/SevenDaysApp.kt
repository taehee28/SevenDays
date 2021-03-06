@file:OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)

package com.thk.sevendays.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList
import com.thk.sevendays.SevenDaysScreen
import com.thk.sevendays.data.ChallengeViewModel
import com.thk.sevendays.ui.theme.SevenDaysTheme
import com.thk.sevendays.utils.navigateToDetail

@Composable
fun SevenDaysApp(challengeViewModel: ChallengeViewModel) {
    SevenDaysTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            SevenDaysNavHost(
                challenges = sampleChallengeList,
                onAddChallenge = challengeViewModel::addChallenge
            )
        }
    }
}

@Composable
private fun SevenDaysNavHost(challenges: List<Challenge>, onAddChallenge: (Challenge) -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SevenDaysScreen.Home.name
    ) {
        // 메인 리스트 화면
        composable(route = SevenDaysScreen.Home.name) {
            SevenDaysHome(
                challenges = challenges,
                onAddChallenge = onAddChallenge,
                onChallengeClick = { navController.navigateToDetail(it) }
            )
        }

        // 도전 상세 화면
        composable(
            route = "${SevenDaysScreen.Detail.name}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val id = entry.arguments?.getString("id") ?: "null"
            ChallengeDetailScreen(id = id)
        }
    }
}