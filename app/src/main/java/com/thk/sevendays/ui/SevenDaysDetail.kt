@file:OptIn(ExperimentalFoundationApi::class)

package com.thk.sevendays.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList
import com.thk.data.model.sampleStampList
import com.thk.sevendays.ui.components.ChallengeCard
import com.thk.sevendays.ui.components.ChallengeStampCard

@Composable
fun ChallengeDetailScreen(challenge: Challenge?) {
    Scaffold() {
        if (challenge == null) {
            Text(text = "error!")
        } else {
            DetailScreenContent(challenge)
        }
    }
}

@Preview
@Composable
fun ChallengeDetailScreenPreview() {
    MaterialTheme {
        ChallengeDetailScreen(sampleChallengeList[0])
    }
}

@Composable
fun DetailScreenContent(challenge: Challenge) {
    ChallengeStampCard(stamps = sampleStampList) {
        ChallengeCard(challenge = challenge)
    }
}