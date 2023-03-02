
package com.thk.sevendays.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.thk.sevendays.ui.viewmodels.ChallengeViewModel
import com.thk.sevendays.ui.viewmodels.StampViewModel
import com.thk.sevendays.navigation.SevenDaysApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val challengeViewModel by viewModels<ChallengeViewModel>()
    private val stampViewModel by viewModels<StampViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SevenDaysApp(
                challengeViewModel = challengeViewModel,
                stampViewModel = stampViewModel
            )
        }
    }
}




