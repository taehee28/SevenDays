package com.thk.sevendays.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thk.sevendays.ui.components.ChallengeList

val titleList = listOf<String>("title 1", "title 2", "title 3", "title 4")

@Composable
fun SevenDaysScreen() {
    Scaffold() {
        ChallengeList(challenges = titleList)
    }
}

@Preview
@Composable
private fun SevenDaysScreenPreview() {
    MaterialTheme {
        SevenDaysScreen()
    }
}