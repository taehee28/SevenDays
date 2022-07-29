@file:OptIn(ExperimentalFoundationApi::class)

package com.thk.sevendays.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thk.data.model.sampleChallengeList
import com.thk.data.model.sampleStampList
import com.thk.sevendays.ui.components.ChallengeCard
import com.thk.sevendays.ui.components.ChallengeStampCard

@Composable
fun ChallengeDetailScreen(id: String) {
    Scaffold() {
        DetailScreenContent()
    }
    Text(text = "id = $id")
}

@Preview
@Composable
fun ChallengeDetailScreenPreview() {
    MaterialTheme {
        ChallengeDetailScreen("id~~~~~")
    }
}

@Composable
fun DetailScreenContent() {
    ChallengeStampCard(stamps = sampleStampList) {
        ChallengeCard(challenge = sampleChallengeList[0])
    }
}