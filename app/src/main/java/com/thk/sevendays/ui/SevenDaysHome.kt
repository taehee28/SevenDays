package com.thk.sevendays.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.thk.sevendays.ui.components.ChallengeList
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList
import com.thk.sevendays.ui.theme.SevenDaysAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun SevenDaysHome(
    challengesFlow: StateFlow<List<Challenge>>,
    onAddChallenge: (String) -> Unit,
    onChallengeClick: (Long) -> Unit
) {
    val challenges by challengesFlow.collectAsState()
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { setShowDialog(true) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        ChallengeList(
            challenges = challenges,
            onChallengeClick = onChallengeClick
        )

        AnimatedVisibility(visible = showDialog) {
            AddChallengeDialog(
                setShowDialog = setShowDialog,
                onAddChallenge = onAddChallenge
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview
@Composable
private fun SevenDaysScreenPreview_light() {
    SevenDaysAppTheme(darkTheme = false) {
        SevenDaysHome(challengesFlow = MutableStateFlow(emptyList()), onAddChallenge = {}, onChallengeClick = {})
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview
@Composable
private fun SevenDaysScreenPreview_dark() {
    SevenDaysAppTheme(darkTheme = true) {
        SevenDaysHome(challengesFlow = MutableStateFlow(emptyList()), onAddChallenge = {}, onChallengeClick = {})
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun AddChallengeDialog(
    setShowDialog: (Boolean) -> Unit,
    onAddChallenge: (String) -> Unit
) {
    Dialog_Alert(
        setShowDialog = setShowDialog,
        onAddChallenge = onAddChallenge
    )
}

@ExperimentalComposeUiApi
@Composable
private fun Dialog_FullScreen(setShowDialog: (Boolean) -> Unit) {
    var challengeTitle by rememberSaveable { mutableStateOf("") }

    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "새 도전 추가하기") },
                    navigationIcon = {
                        IconButton(onClick = { setShowDialog(false) }) {
                            Icon(imageVector = Icons.Default.Close,
                                contentDescription = "close")
                        }
                    }
                )
            }
        ) {
            AddChallengeDialogContent(
                challengeTitle = challengeTitle,
                onTitleChange = { challengeTitle = it }
            )
        }

    }
}

@Composable
private fun Dialog_Alert(
    setShowDialog: (Boolean) -> Unit,
    onAddChallenge: (String) -> Unit,
) {
    var challengeTitle by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        modifier = Modifier.wrapContentHeight(),
        onDismissRequest = { setShowDialog(false) },
        text = {
            Column {
                Text(
                    text = "새 도전 추가하기",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(16.dp))
                AddChallengeDialogContent(
                    challengeTitle = challengeTitle,
                    onTitleChange = { challengeTitle = it }
                )

            }
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = { setShowDialog(false) }) {
                    Text(text = "취소")
                }
                TextButton(
                    onClick = {
                        onAddChallenge(challengeTitle)
                        setShowDialog(false)
                    }
                ) {
                    Text(text = "추가")
                }
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun AddChallengeDialogContent(challengeTitle: String, onTitleChange: (String) -> Unit) {
    OutlinedTextField(
        value = challengeTitle,
        onValueChange = onTitleChange,
        placeholder = { Text(text = "제목") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}