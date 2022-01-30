package com.thk.sevendays.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.thk.sevendays.data.Challenge
import com.thk.sevendays.ui.components.ChallengeList
import java.time.LocalDate
import kotlin.random.Random

@Composable
fun SevenDaysScreen(
    challenges: List<Challenge>,
    onAddChallenge: (Challenge) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddChallenge(Challenge("title ${Random.nextInt(50)}", LocalDate.now()))
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        ChallengeList(challenges = challenges)
    }
}

@Preview
@Composable
private fun SevenDaysScreenPreview() {
    MaterialTheme {
        SevenDaysScreen(challenges = emptyList(), onAddChallenge = {})
    }
}