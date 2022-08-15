@file:OptIn(ExperimentalFoundationApi::class)

package com.thk.sevendays.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
    onChallengeClick: (Long) -> Unit,
    onRemoveChallenge: ((Long) -> Unit)? = null
) {
    LazyColumn(contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 72.dp), modifier = Modifier.fillMaxHeight()) {
        items(
            items = challenges,
            key = { it.challengeId }
        ) { challenge ->
            ChallengeCard(
                challenge = challenge,
                modifier = Modifier.animateItemPlacement(animationSpec = tween(300)),
                onChallengeClick = onChallengeClick,
                onRemoveChallenge = onRemoveChallenge
            )
        }
    }
}

// TODO: stateless한 버전의 컴포저블 만들기
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChallengeCard(
    challenge: Challenge,
    modifier: Modifier = Modifier,
    onChallengeClick: ((Long) -> Unit)? = null,
    onRemoveChallenge: ((Long) -> Unit)? = null
) {
    val challengingDays = LocalDate.now().challengingDaysFrom(startDate = challenge.startDate)
    val backgroundColor = if (challengingDays > 7) {
        SevenDaysTheme.colors.finishedBackground
    } else {
        SevenDaysTheme.colors.inChallengeBackground
    }

    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        enabled = onChallengeClick != null,
        onClick = {
            if (onChallengeClick != null) {
                onChallengeClick(challenge.challengeId)
            }
        },
        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp,
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        backgroundColor = backgroundColor
    ) {
        CardContent(
            title = challenge.title,
            challengingDays = challengingDays,
            menu = onRemoveChallenge?.let { {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "menu",
                    modifier = Modifier
                        .alpha(0.5f)
                        .clickable { menuExpanded = true }
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            menuExpanded = false
                            onRemoveChallenge(challenge.challengeId)
                        }
                    ) {
                        Text(text = "삭제하기")
                    }
                }
            } }
        )
    }
}

@Composable
private fun CardContent(
    title: String,
    challengingDays: Int,
    menu: @Composable (BoxScope.() -> Unit)? = null
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

        if (menu != null) {
            Box(modifier = Modifier.align(Alignment.Top), content = menu)
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