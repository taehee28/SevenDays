@file:OptIn(ExperimentalFoundationApi::class)

package com.thk.sevendays.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thk.data.logd
import com.thk.data.model.Challenge
import com.thk.data.model.Stamp
import com.thk.data.model.sampleChallengeList
import com.thk.data.model.sampleStampList
import com.thk.sevendays.state.UiState
import com.thk.sevendays.ui.components.ChallengeCard
import com.thk.sevendays.ui.components.ChallengeStampCard
import com.thk.sevendays.ui.viewmodels.StampViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ChallengeDetailScreen(
    id: Long,
    onBackClick: () -> Unit,
    viewModel: StampViewModel = hiltViewModel()
) {
    val challenge: Challenge? by viewModel.challenge
    val stamps: List<Stamp> by viewModel.stamps.collectAsState()

    LaunchedEffect(Unit) {
        // TODO: 화면이 돌아가도 한번만 호출 되도록
        viewModel.loadChallengeData(id)
    }

    // 해당 화면을 빠져나갈 때(= disposed) viewModel의 데이터 제거
    DisposableEffect(Unit) { onDispose { viewModel.clearUiState() } }

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
        DetailScreenContent(
            challenge = challenge ?: Challenge(),
            stamps = stamps,
            setStampChecked = viewModel::setStampChecked
        )
    }
}

@Preview
@Composable
fun ChallengeDetailScreenPreview() {
    MaterialTheme {
        ChallengeDetailScreen(
            id = 0,
            onBackClick = {}
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