package com.thk.sevendays.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChallengeList(challenges: List<String>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp), modifier = Modifier.fillMaxHeight()) {
        items(items = challenges) { title ->
            ChallengeCard(title = title)
        }
    }
}

@Composable
private fun ChallengeCard(title: String) {
    Card(
        elevation = 3.dp,
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        CardContent(title)
    }
}

@Composable
private fun CardContent(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(16.dp)
    ) {
        Column() {
            Text(text = title, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "00.00 ~ 00.00", style = MaterialTheme.typography.body2, modifier = Modifier.alpha(ContentAlpha.medium))
        }

        Box(
            modifier = Modifier
                .size(50.dp, 50.dp)
                .background(color = MaterialTheme.colors.secondary)
        ) {
            
        }


    }
}

@Preview
@Composable
private fun ChallengeListPreview() {
    MaterialTheme {
        ChallengeList(listOf("1", "2", "3", "4"))
    }
}

@Preview
@Composable
private fun ChallengeCardPreview() {
    MaterialTheme {
        ChallengeCard("preview")
    }
}