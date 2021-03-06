package com.thk.sevendays.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thk.data.model.Stamp
import com.thk.data.model.sampleStampList
import com.thk.sevendays.ui.theme.*
import com.thk.sevendays.utils.firstIndex
import com.thk.sevendays.utils.isToday
import java.time.LocalDate

private enum class NodePosition {
    Start, Middle, End
}

@Composable
fun ChallengeStampCard(
    stamps: List<Stamp>,
    header: @Composable LazyItemScope.() -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item(content = header)

        item { 
            StampListCard(stamps = stamps)
        }
    }
}

@Preview
@Composable
private fun ChallengeStampCardPreview() {
    ChallengeStampCard(stamps = sampleStampList)
}

@Composable
fun LabeledStampList(
    stamps: List<Stamp>,
    header: @Composable LazyItemScope.() -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item(content = header)

        itemsIndexed(
            items = stamps
        ) { index, item ->
            LabeledStampBox(
                stamp = item,
                nodePosition = when (index) {
                    stamps.firstIndex -> NodePosition.Start
                    stamps.lastIndex -> NodePosition.End
                    else -> NodePosition.Middle
                }
            )
        }
    }
}

@Preview
@Composable
private fun StampListPreview() {
//    LabeledStampList(sampleStampList)
}

@Composable
private fun StampListCard(
    stamps: List<Stamp>
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
    StampListCard(sampleStampList)
}

@Composable
private fun LabeledStampBox(
    stamp: Stamp,
    nodePosition: NodePosition = NodePosition.Middle
) {
    Row(
        modifier = Modifier.width(300.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        StampBox(isChecked = stamp.isChecked, nodePosition = nodePosition)

        Spacer(modifier = Modifier.width(24.dp))

        if (stamp.isChecked || stamp.date.isToday()) {
            val (labelText, labelColor) = if (stamp.date.isToday()) {
                "??????!" to Blue300
            } else {
                stamp.date.toString() to RedA100
            }

            Label(labelText, labelColor)
        }
    }
}

@Preview
@Composable
private fun LabeledStampBoxPreview() {
    LabeledStampBox(sampleStampList[0])
}

@Composable
private fun Label(
    text: String,
    color: Color = Color.Gray
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(AbsoluteCutCornerShape(topLeftPercent = 50, bottomLeftPercent = 50))
            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
            .background(color)
            .padding(
                top = 4.dp,
                bottom = 4.dp,
                start = 18.sp.value.dp * 1.1f,
                end = 18.sp.value.dp / 2
            )
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.W300, color = Color.White)
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
    nodePosition: NodePosition = NodePosition.Middle
) {
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
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(10.dp, Red100, CircleShape)
        ) {
            // TODO: ????????? ?????? ?????? 
        }
    }
}

@Preview
@Composable
fun StampBoxPreview() {
    StampBox(false)
}

@Composable
private fun Edge(
    modifier: Modifier
) = Box(
    modifier = modifier
        .size(30.dp)
        .background(Red100)
)
