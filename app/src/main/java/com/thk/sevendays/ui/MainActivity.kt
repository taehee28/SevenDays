
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SevenDaysApp()
        }
    }
}




