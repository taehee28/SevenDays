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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
    // TODO: 2022/02/26 종료된 도전 카드 색 disable한 색으로 변경하기  
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
        Column {
            Text(
                text = "7일 동안",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TODO: 2022/02/26 나중에 다른 색으로 변경하기
            if (challengingDays > 7) {
                Text(
                    text = "도전 종료",
                    style = MaterialTheme.typography.body2.copy(color = Color.Blue),
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            } else {
                Text(
                    text = "${challengingDays}일째 도전 중!",
                    style = MaterialTheme.typography.body2.copy(color = Color.Red),
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            }
        }

    }
}

@Preview
@Composable
private fun ChallengeListPreview() {
    MaterialTheme {
        ChallengeList(
            listOf(
                Challenge("하루 한번 산책", LocalDate.now()),
                Challenge("하루에 물 2리터 마시기", LocalDate.now().minusDays(9))
            )
        )
    }
}

@Preview
@Composable
private fun ChallengeCardPreview() {
    MaterialTheme {
        ChallengeCard(Challenge("하루 한번 산책하기", LocalDate.now().minusDays(3)))
    }
}