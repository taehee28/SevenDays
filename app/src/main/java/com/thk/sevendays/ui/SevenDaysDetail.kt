@file:OptIn(ExperimentalFoundationApi::class)

package com.thk.sevendays.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChallengeDetailScreen(id: String) {
    Scaffold() {
        DetailScreenContent()
    }
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
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "prefix")
        Text(text = "title")
        Text(text = "period")

        // TODO: 새로운 StampList 컴포저블 이용해서 화면 구현하기
    }
}