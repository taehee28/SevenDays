
package com.thk.sevendays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thk.sevendays.data.ChallengeViewModel
import com.thk.sevendays.data.StampViewModel
import com.thk.sevendays.ui.SevenDaysApp
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




