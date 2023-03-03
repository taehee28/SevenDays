
package com.thk.sevendays.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.thk.sevendays.ui.viewmodels.ChallengeViewModel
import com.thk.sevendays.ui.viewmodels.StampViewModel
import com.thk.sevendays.navigation.SevenDaysApp
import com.thk.sevendays.ui.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // TODO: viewModel 인스턴스 여기서 넘기지 않도록 변경하기
    private val challengeViewModel by viewModels<ChallengeViewModel>()
    private val stampViewModel by viewModels<StampViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SevenDaysApp(
                challengeViewModel = challengeViewModel,
                stampViewModel = stampViewModel,
                settingsViewModel = settingsViewModel
            )
        }
    }
}




