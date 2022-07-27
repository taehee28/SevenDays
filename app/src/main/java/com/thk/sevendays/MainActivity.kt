@file:OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)

package com.thk.sevendays

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thk.data.model.Challenge
import com.thk.sevendays.data.ChallengeViewModel
import com.thk.sevendays.ui.ChallengeDetailScreen
import com.thk.sevendays.ui.SevenDaysHome
import com.thk.sevendays.ui.theme.SevenDaysTheme
import com.thk.sevendays.utils.navigateToDetail

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private val challengeViewModel by viewModels<ChallengeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SevenDaysApp(challengeViewModel = challengeViewModel)
        }
    }
    
}

@Composable
private fun SevenDaysApp(challengeViewModel: ChallengeViewModel) {
    SevenDaysTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            SevenDaysNavHost(
                challenges = challengeViewModel.challenges,
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
