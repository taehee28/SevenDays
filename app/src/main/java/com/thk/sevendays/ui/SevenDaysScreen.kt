package com.thk.sevendays.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.thk.sevendays.ui.components.ChallengeList

val titleList = List(10) { index -> "title $index" }

@Composable
fun SevenDaysScreen() {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }) {
        ChallengeList(challenges = titleList)
    }
}

@Preview
@Composable
private fun SevenDaysScreenPreview() {
    MaterialTheme {
        SevenDaysScreen()
    }
}