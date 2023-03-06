@file:OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)

package com.thk.sevendays.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thk.sevendays.ui.viewmodels.ChallengeViewModel
import com.thk.sevendays.ui.viewmodels.StampViewModel
import com.thk.sevendays.ui.screens.ChallengeDetailScreen
import com.thk.sevendays.ui.screens.SettingsScreen
import com.thk.sevendays.ui.screens.SevenDaysHome
import com.thk.sevendays.ui.theme.SevenDaysAppTheme
import com.thk.sevendays.ui.viewmodels.SettingsViewModel
import com.thk.sevendays.utils.navigateToDetail

@Composable
fun SevenDaysApp(
    challengeViewModel: ChallengeViewModel,
    stampViewModel: StampViewModel,
    settingsViewModel: SettingsViewModel
) {
    val navController = rememberNavController()

    SevenDaysAppTheme {
        Scaffold {
            SevenDaysNavHost(
                navController = navController,
                challengeViewModel = challengeViewModel,
                stampViewModel = stampViewModel,
                settingsViewModel = settingsViewModel
            )
        }
    }
}

@Composable
private fun SevenDaysNavHost(
    navController: NavHostController,
    challengeViewModel: ChallengeViewModel,
    stampViewModel: StampViewModel,
    settingsViewModel: SettingsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = SevenDaysScreen.Home.name
    ) {
        // 메인 리스트 화면
        composable(
            route = SevenDaysScreen.Home.name,
            deepLinks = listOf(navDeepLink { uriPattern = "svd://sevendays/${SevenDaysScreen.Home.name}" })
        ) {
            SevenDaysHome(
                uiStateFlow = challengeViewModel.uiState,
                onAddChallenge = challengeViewModel::addChallenge,
                onRemoveChallenge = challengeViewModel::removeChallenge,
                onChallengeClick = { navController.navigateToDetail(it) },
                onSettingsClick =  { navController.navigate(SevenDaysScreen.Settings.name) }
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

            stampViewModel.getStamps(id)

            ChallengeDetailScreen(
                challenge = challenge,
                uiStateFlow = stampViewModel.uiState,
                setStampChecked = stampViewModel::setStampChecked,
                onDisposed = stampViewModel::clearUiState,
                onBackClick = { navController.popBackStack() }
            )
        }

        // 설정 화면 
        composable(route = SevenDaysScreen.Settings.name) {
            SettingsScreen(
                viewModel = settingsViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}


@Deprecated("더이상 사용하지 않음")
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