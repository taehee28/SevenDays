package com.thk.sevendays.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.thk.sevendays.data.Challenge
import com.thk.sevendays.ui.components.ChallengeList
import java.time.LocalDate
import kotlin.random.Random

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
        
        TestDialog(showDialog = showDialog, setShowDialog = setShowDialog)
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
private fun TestDialog(showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    AnimatedVisibility(
        visible = showDialog,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight * 2 }
        )
    ) {
        Dialog(onDismissRequest = { setShowDialog(false) }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "title") },
                        navigationIcon = {
                            IconButton(onClick = { setShowDialog(false) }) {
                                Icon(imageVector = Icons.Default.Close,
                                    contentDescription = "close")
                            }
                        }
                    )
                }
            ) {

            }
        }
    }



//    AlertDialog(
//        onDismissRequest = {
//            setShowDialog(false)
//        },
//        title = {
//            Text(text = "title")
//        },
//        text = {
//            Text(text = "body text")
//        },
//        confirmButton = {
//            TextButton(onClick = { setShowDialog(false) }) {
//                Text(text = "OK")
//            }
//        }
//    )

}


@Preview
@Composable
private fun TestDialogPreview() {
    MaterialTheme {
    }
}