package com.thk.sevendays.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thk.sevendays.ui.theme.*
import com.thk.sevendays.utils.firstIndex

@Composable
fun LabeledStampList() {
    val list = (1..7).toList()

    LazyColumn() {
        itemsIndexed(
            items = list
        ) { index, item ->
            LabeledStampBox(
                nodePosition = when (index) {
                    list.firstIndex -> NodePosition.Start
                    list.lastIndex -> NodePosition.End
                    else -> NodePosition.Middle
                }
            )
        }
    }
}

@Preview
@Composable
fun StampListPreview() {
    LabeledStampList()
}

@Composable
private fun LabeledStampBox(
    nodePosition: NodePosition = NodePosition.Middle
) {
    Row(
        modifier = Modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StampBox(nodePosition = nodePosition)
        Spacer(modifier = Modifier.width(32.dp))
        Label()
    }
}

@Preview
@Composable
private fun LabeledStampBoxPreview() {
    LabeledStampBox()
}

@Composable
private fun Label(color: Color = Color.Gray) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(AbsoluteCutCornerShape(topLeftPercent = 50, bottomLeftPercent = 50))
            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
            .background(color)
            .padding(
                top = 4.dp,
                bottom = 4.dp,
                start = MaterialTheme.typography.h5.fontSize.value.dp * 1.1f,
                end = MaterialTheme.typography.h5.fontSize.value.dp / 2
            )
    ) {
        Text(text = "TEST", style = MaterialTheme.typography.h5, fontWeight = FontWeight.W300, color = Color.White)
    }
}

@Preview
@Composable
private fun LabelPreview() {
    Column {
        Label(Blue300)
        Label(RedA100)
    }
}

@Composable
private fun StampBox(
    nodePosition: NodePosition = NodePosition.Middle
) {
    Box(
        modifier = Modifier.size(150.dp),
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
                .size(110.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(10.dp, Red100, CircleShape)
        ) {
            // TODO: 스탬프 표시 기능 
        }
    }
}

@Composable
fun Edge(
    modifier: Modifier
) = Box(
    modifier = modifier
        .size(50.dp)
        .background(Red100)
)

@Preview
@Composable
fun StampBoxPreview() {
    StampBox()
}

private enum class NodePosition {
    Start, Middle, End
}