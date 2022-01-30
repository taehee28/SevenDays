package com.thk.sevendays.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.thk.sevendays.data.Challenge
import java.time.LocalDate

@Composable
fun ChallengeList(challenges: List<Challenge>) {
    LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.fillMaxHeight()) {
        items(items = challenges) { challenge ->
            ChallengeCard(challenge = challenge)
        }
    }
}

@Composable
private fun ChallengeCard(challenge: Challenge) {
    Card(
        elevation = 3.dp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable {  }
    ) {
        CardContent(challenge)
    }
}

@Composable
private fun CardContent(challenge: Challenge) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(16.dp)
    ) {
        Column() {
            Text(text = challenge.title, style = MaterialTheme.typography.h5)
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
        ChallengeList(emptyList())
    }
}

@Preview
@Composable
private fun ChallengeCardPreview() {
    MaterialTheme {
        ChallengeCard(Challenge("preivew", LocalDate.now()))
    }
}