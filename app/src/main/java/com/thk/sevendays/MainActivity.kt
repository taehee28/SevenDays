package com.thk.sevendays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.thk.sevendays.data.ChallengeViewModel
import com.thk.sevendays.ui.SevenDaysScreen
import com.thk.sevendays.ui.theme.SevenDaysTheme

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private val challengeViewModel by viewModels<ChallengeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SevenDaysTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SevenDaysActivityScreen(challengeViewModel = challengeViewModel)           
                }
            }
        }
    }
    
    @Composable
    private fun SevenDaysActivityScreen(challengeViewModel: ChallengeViewModel) {
        SevenDaysScreen(
            challenges = challengeViewModel.challenges,
            onAddChallenge = challengeViewModel::addChallenge
        )
    }
}