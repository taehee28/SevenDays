@file:OptIn(ExperimentalAnimationApi::class)

package com.thk.sevendays.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thk.data.model.Stamp
import com.thk.data.model.sampleStampList
import com.thk.sevendays.R
import com.thk.sevendays.ui.theme.*
import com.thk.sevendays.utils.firstIndex
import com.thk.sevendays.utils.isToday
import java.time.LocalDate
import kotlin.random.Random

private enum class NodePosition {
    Start, Middle, End
}

@Composable
fun ChallengeStampCard(
    stamps: List<Stamp>,
    setStampChecked: (Stamp) -> Unit,
    header: @Composable LazyItemScope.() -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        item(content = header)

        item { 
            StampListCard(
                stamps = stamps,
                setStampChecked = setStampChecked
            )
        }
    }
}

@Preview
@Composable
private fun ChallengeStampCardPreview() {
    ChallengeStampCard(stamps = sampleStampList, setStampChecked = {})
}

@Composable
private fun StampListCard(
    stamps: List<Stamp>,
    setStampChecked: (Stamp) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for ((index, item) in stamps.withIndex()) {
                LabeledStampBox(
                    stamp = item,
                    setStampChecked = setStampChecked,
                    nodePosition = when (index) {
                        stamps.firstIndex -> NodePosition.Start
                        stamps.lastIndex -> NodePosition.End
                        else -> NodePosition.Middle
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun StampListCardPreview() {
    StampListCard(sampleStampList, {})
}

@Composable
private fun LabeledStampBox(
    stamp: Stamp,
    setStampChecked: (Stamp) -> Unit,
    nodePosition: NodePosition = NodePosition.Middle
) {
    Row(
        modifier = Modifier.width(300.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        StampBox(
            isChecked = stamp.isChecked,
            clickable = stamp.date.isToday(),
            onCheckedChanged = { setStampChecked(stamp.copy(isChecked = it)) },
            nodePosition = nodePosition
        )

        Spacer(modifier = Modifier.width(24.dp))

        if (!stamp.date.isAfter(LocalDate.now())) {
            val (labelText, labelColor) = stamp.run {
                if (date.isToday()) {
                    "오늘!" to Blue300
                } else {
                    val color = if (isChecked) RedA100 else Color.Gray
                    date.toString() to color
                }
            }

            Label(labelText, labelColor)
        }

    }
}

@Preview
@Composable
private fun LabeledStampBoxPreview() {
    LabeledStampBox(sampleStampList[0], {})
}

@Composable
private fun Label(
    text: String,
    color: Color = Color.Gray
) {
    val labelFondSize = MaterialTheme.typography.subtitle1

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(AbsoluteCutCornerShape(topLeftPercent = 50, bottomLeftPercent = 50))
            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
            .background(color)
            .padding(
                top = 4.dp,
                bottom = 4.dp,
                start = labelFondSize.fontSize.value.dp * 1.1f,
                end = labelFondSize.fontSize.value.dp / 2
            )
    ) {
        Text(text = text, fontSize = labelFondSize.fontSize, fontWeight = FontWeight.W300, color = Color.White)
    }
}

@Preview
@Composable
private fun LabelPreview() {
    Column {
        Label(LocalDate.now().toString(), Blue300)
        Label(LocalDate.now().toString(), RedA100)
    }
}

@Composable
private fun StampBox(
    isChecked: Boolean,
    clickable: Boolean,
    onCheckedChanged: (Boolean) -> Unit = {},
    nodePosition: NodePosition = NodePosition.Middle
) {
    val checkedState = remember { mutableStateOf(isChecked) }

    val context = LocalContext.current

    val horizontalBias = rememberSaveable { Random.nextDouble(-0.2, 0.2) }
    val verticalBias = rememberSaveable { Random.nextDouble(-0.2, 0.2) }
    val degree = rememberSaveable { (-10..10).random() }
    val rotation = (360 + degree) % 360

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {

        when (nodePosition) {
            NodePosition.Start -> Edge(modifier = Modifier.align(Alignment.BottomCenter))
            NodePosition.End -> Edge(modifier = Modifier.align(Alignment.TopCenter))
            NodePosition.Middle -> {
                Edge(modifier = Modifier.align(Alignment.BottomCenter))
                Edge(modifier = Modifier.align(Alignment.TopCenter))
            }
        }



        Box(
            contentAlignment = BiasAlignment(horizontalBias.toFloat(),verticalBias.toFloat()),
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .border(10.dp, Red100, CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (clickable) {
                        checkedState.value = !checkedState.value
                        onCheckedChanged(checkedState.value)
                    } else {
                        Toast
                            .makeText(context, context.getString(R.string.toast_modify_disabled), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        ) {
            AnimatedVisibility (
                checkedState.value,
                enter = scaleIn(tween(500), 0f, TransformOrigin.Center),
                exit = fadeOut()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_sentiment_satisfied_24),
                    contentDescription = "checked",
                    tint = SevenDaysTheme.colors.stampColor,
                    modifier = Modifier
                        .size(50.dp)
                        .rotate(rotation.toFloat())
                )
            }
        }
    }
}

@Preview
@Composable
fun StampBoxPreview() {
    StampBox(isChecked = false, clickable = false)
}

@Composable
private fun Edge(
    modifier: Modifier
) = Box(
    modifier = modifier
        .width(30.dp)
        .height(20.dp)
        .background(Red100)
)
