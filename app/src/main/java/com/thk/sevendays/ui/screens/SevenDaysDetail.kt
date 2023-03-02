@file:OptIn(ExperimentalFoundationApi::class)

package com.thk.sevendays.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thk.data.model.Challenge
import com.thk.data.model.Stamp
import com.thk.data.model.sampleChallengeList
import com.thk.data.model.sampleStampList
import com.thk.sevendays.state.UiState
import com.thk.sevendays.ui.components.ChallengeCard
import com.thk.sevendays.ui.components.ChallengeStampCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ChallengeDetailScreen(
    challenge: Challenge?,
    uiStateFlow: StateFlow<UiState<List<Stamp>>>,
    setStampChecked: (Stamp) -> Unit,
    onDisposed: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by uiStateFlow.collectAsState()

    // 해당 화면을 빠져나갈 때(= disposed) viewModel의 데이터 제거
    DisposableEffect(Unit) { onDispose { onDisposed() } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) {
        if (challenge == null) {
            Text(text = "error!")
        } else {

            when (val state = uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }
                is UiState.Success -> {
                    DetailScreenContent(
                        challenge = challenge,
                        stamps = state.data,
                        setStampChecked = setStampChecked
                    )
                }
                is UiState.Error -> {

                }
            }


        }
    }
}

@Preview
@Composable
fun ChallengeDetailScreenPreview() {
    MaterialTheme {
        ChallengeDetailScreen(
            sampleChallengeList[0],
            MutableStateFlow(UiState.Success(sampleStampList)),
            {},
            {},
            {}
        )
    }
}

@Composable
fun DetailScreenContent(
    challenge: Challenge,
    stamps: List<Stamp>,
    setStampChecked: (Stamp) -> Unit,
) {
    ChallengeStampCard(
        stamps = stamps,
        setStampChecked = setStampChecked
    ) {
        ChallengeCard(challenge = challenge)
    }
}