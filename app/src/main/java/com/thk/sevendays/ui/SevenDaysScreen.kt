package com.thk.sevendays.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.thk.sevendays.data.Challenge
import com.thk.sevendays.ui.components.ChallengeList
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun SevenDaysScreen(
    challenges: List<Challenge>,
    onAddChallenge: (Challenge) -> Unit,
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    setShowDialog(true)
//                    onAddChallenge(Challenge("title ${Random.nextInt(50)}", LocalDate.now()))
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        ChallengeList(challenges = challenges)
        
        AddChallengeDialog(showDialog = showDialog, setShowDialog = setShowDialog)
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview
@Composable
private fun SevenDaysScreenPreview() {
    MaterialTheme {
        SevenDaysScreen(challenges = emptyList(), onAddChallenge = {})
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun AddChallengeDialog(showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    AnimatedVisibility(
        visible = showDialog,
    ) {
        Dialog_Alert(setShowDialog = setShowDialog)
    }
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
                onChallengeTitleChange = { challengeTitle = it }
            )
        }

    }
}

@Composable
private fun Dialog_Alert(setShowDialog: (Boolean) -> Unit) {
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
                    onChallengeTitleChange = { challengeTitle = it }
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
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "추가")
                }
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun AddChallengeDialogContent(challengeTitle: String, onChallengeTitleChange: (String) -> Unit) {
    OutlinedTextField(
        value = challengeTitle,
        onValueChange = onChallengeTitleChange,
        placeholder = { Text(text = "제목") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
private fun AddChallengeDialogContentPreview() {
    MaterialTheme {
    }
}