package com.thk.sevendays.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.thk.sevendays.ui.components.ChallengeList
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList
import com.thk.sevendays.R
import com.thk.sevendays.state.UiState
import com.thk.sevendays.ui.theme.SevenDaysAppTheme
import com.thk.sevendays.ui.viewmodels.ChallengeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun SevenDaysHome(
    onChallengeClick: (Long) -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: ChallengeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(imageVector = Icons.Rounded.Settings, contentDescription = "settings")
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { setShowDialog(true) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        when (val state = uiState) {
            is UiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                AnimatedVisibility(
                    visible = state.data.isEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.empty_challenge_list),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body2,
                            color = Color.Gray,
                        )
                    }
                }

                ChallengeList(
                    challenges = state.data,
                    onChallengeClick = onChallengeClick,
                    onRemoveChallenge = viewModel::removeChallenge
                )
            }
            is UiState.Error -> {

            }
        }

        AnimatedVisibility(visible = showDialog) {
            AddChallengeDialog(
                setShowDialog = setShowDialog,
                onAddChallenge = viewModel::addChallenge
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
        SevenDaysHome(
            onChallengeClick = {},
            onSettingsClick = {}
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview
@Composable
private fun SevenDaysScreenPreview_dark() {
    SevenDaysAppTheme(darkTheme = true) {
        SevenDaysHome(
            onChallengeClick = {},
            onSettingsClick = {}
        )
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
                    title = { Text(text = stringResource(id = R.string.add_challenge)) },
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
                    text = stringResource(id = R.string.add_challenge),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1
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
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(
                    enabled = challengeTitle.isNotBlank(),
                    onClick = {
                        onAddChallenge(challengeTitle)
                        setShowDialog(false)
                    }
                ) {
                    Text(text = stringResource(id = R.string.add))
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
        placeholder = { Text(text = stringResource(id = R.string.title_placeholder)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}