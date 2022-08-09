
package com.thk.sevendays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.thk.sevendays.data.ChallengeViewModel
import com.thk.sevendays.ui.SevenDaysApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val challengeViewModel by viewModels<ChallengeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SevenDaysApp(challengeViewModel = challengeViewModel)
        }
    }
}




