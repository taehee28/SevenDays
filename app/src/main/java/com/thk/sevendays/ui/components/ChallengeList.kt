package com.thk.sevendays.ui.components

import android.util.Log
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
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList
import com.thk.sevendays.ui.theme.*
import com.thk.sevendays.utils.challengingDaysFrom
import java.time.LocalDate

@Composable
fun ChallengeList(
    challenges: List<Challenge>,
    onChallengeClick: (Long) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.fillMaxHeight()) {
        items(items = challenges) { challenge ->
            ChallengeCard(
                challenge = challenge,
                onChallengeClick = onChallengeClick
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChallengeCard(
    challenge: Challenge,
    onChallengeClick: ((Long) -> Unit)? = null,
) {
    val challengingDays = LocalDate.now().challengingDaysFrom(startDate = challenge.startDate)
    val backgroundColor = if (challengingDays > 7) {
        SevenDaysTheme.colors.finishedBackground
    } else {
        SevenDaysTheme.colors.inChallengeBackground
    }

    Card(
        enabled = onChallengeClick != null,
        onClick = {
            if (onChallengeClick != null) {
                onChallengeClick(challenge.challengeId)
            }
        },
        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        backgroundColor = backgroundColor
    ) {
        CardContent(challenge.title, challengingDays)
    }
}

@Composable
private fun CardContent(
    title: String,
    challengingDays: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(16.dp)
    ) {
        Column {
            Text(
                text = "7일 동안",
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (challengingDays > 7) {
                Text(
                    text = "도전 종료",
                    style = MaterialTheme.typography.caption.copy(color = SevenDaysTheme.colors.finishedFontColor),
                )
            } else {
                Text(
                    text = "${challengingDays}일째 도전 중!",
                    style = MaterialTheme.typography.caption.copy(color = SevenDaysTheme.colors.inChallengeFontColor),
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
            sampleChallengeList,
            onChallengeClick = {}
        )
    }
}

@Preview
@Composable
private fun ChallengeCardPreview() {
    MaterialTheme {
        ChallengeCard(
            sampleChallengeList[1],
            onChallengeClick = {}
        )
    }
}