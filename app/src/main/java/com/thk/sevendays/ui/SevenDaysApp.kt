@file:OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)

package com.thk.sevendays.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thk.data.model.Challenge
import com.thk.data.model.Stamp
import com.thk.data.model.sampleChallengeList
import com.thk.sevendays.SevenDaysScreen
import com.thk.sevendays.data.ChallengeViewModel
import com.thk.sevendays.data.StampViewModel
import com.thk.sevendays.ui.theme.SevenDaysAppTheme
import com.thk.sevendays.utils.navigateToDetail
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SevenDaysApp(
    challengeViewModel: ChallengeViewModel,
    stampViewModel: StampViewModel,
) {
    val navController = rememberNavController()

    SevenDaysAppTheme {
        Scaffold(
            topBar = {
                NavigationTopAppBar(
                    navController = navController,
                    title = {},
                    navigationIcon = {
                         IconButton(onClick = { /*TODO*/ }) {
                             Icon(Icons.Default.Home, contentDescription = "home")
                         }
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    elevation = 0.dp
                )
            }
        ) {
            SevenDaysNavHost(
                navController = navController,
                challengeViewModel = challengeViewModel,
                stampViewModel = stampViewModel
            )
        }
    }
}

@Composable
private fun NavigationTopAppBar(
    navController: NavHostController,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation
) = TopAppBar(
    title = title,
    modifier = modifier,
    navigationIcon = NavigationIcon(navController = navController, navigationIcon = navigationIcon),
    actions = actions,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    elevation = elevation
)

@Composable
private fun NavigationIcon(
    navController: NavHostController,
    navigationIcon: @Composable (() -> Unit)? = null
): @Composable (() -> Unit)? {
    val previousBackStackEntry by navController.rememberPreviousBackStackEntryAsState()

    return previousBackStackEntry?.let {
        {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "back")
            }
        }
    } ?: navigationIcon
}

@Composable
private fun NavController.rememberPreviousBackStackEntryAsState(): State<NavBackStackEntry?> {
    val previousNavBackStackEntry = remember { mutableStateOf(previousBackStackEntry) }

    DisposableEffect(this) {
        val callback = NavController.OnDestinationChangedListener { controller, _, _ ->
            previousNavBackStackEntry.value = controller.previousBackStackEntry
        }

        addOnDestinationChangedListener(callback)

        onDispose {
            removeOnDestinationChangedListener(callback)
        }
    }

    return previousNavBackStackEntry
}

@Composable
private fun SevenDaysNavHost(
    navController: NavHostController,
    challengeViewModel: ChallengeViewModel,
    stampViewModel: StampViewModel
) {
    NavHost(
        navController = navController,
        startDestination = SevenDaysScreen.Home.name
    ) {
        // 메인 리스트 화면
        composable(route = SevenDaysScreen.Home.name) {
            SevenDaysHome(
                challengesFlow = challengeViewModel.challenges,
                onAddChallenge = challengeViewModel::addChallenge,
                onRemoveChallenge = challengeViewModel::removeChallenge,
                onChallengeClick = { navController.navigateToDetail(it) }
            )
        }

        // 도전 상세 화면
        composable(
            route = "${SevenDaysScreen.Detail.name}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                }
            )
        ) { entry ->
            val id = entry.arguments?.getLong("id", -1) ?: -1
            val challenge = challengeViewModel.getChallengeById(id)

            ChallengeDetailScreen(
                challenge = challenge,
                setStampChecked = stampViewModel::setStampChecked,
            )
        }
    }
}