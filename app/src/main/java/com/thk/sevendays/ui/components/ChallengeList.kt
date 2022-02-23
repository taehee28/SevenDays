package com.thk.sevendays.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.thk.sevendays.utils.challengingDaysFrom
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
        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable { }
    ) {
        CardContent(challenge.title, challenge.startDate)
    }
}

@Composable
private fun CardContent(
    title: String,
    startDate: LocalDate
) {
    val challengingDays = LocalDate.now().challengingDaysFrom(startDate = startDate)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(16.dp)
    ) {
        Column() {
            Text(text = title, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))
            // TODO: 2022/02/23 글자 색 변경하기  
            Text(text = "${challengingDays}일째 도전 중!", style = MaterialTheme.typography.body2, modifier = Modifier.alpha(ContentAlpha.medium))
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
        ChallengeCard(Challenge("산책하기", LocalDate.now().minusDays(3)))
    }
}