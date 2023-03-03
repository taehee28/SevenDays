package com.thk.sevendays.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun Section(
    title: String = "",
    content: @Composable ColumnScope.() -> Unit
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
) {
    if (title.isNotBlank()) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.secondaryVariant,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(bottom = 10.dp, start = 16.dp)
        )
    }

    content()
    Divider(modifier = Modifier.padding(top = 8.dp))
}

@Composable
fun SwitchPref(
    text: String,
    description: String = "",
    enabled: Boolean = true,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) = BasePerf(
    text = text,
    description = description,
    enabled = enabled
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
    )
}

@Composable
fun TimePickerPref(
    text: String,
    description: String = "",
    time: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    enabled: Boolean = true,
) {
    var showDialog by remember { mutableStateOf(false) }

    BasePerf(
        text = text,
        description = description,
        enabled = enabled,
        onClick = {
            showDialog = true
        }
    ) {
        val textAlpha = if (enabled) 1f else 0.5f

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = time.format(DateTimeFormatter.ofPattern("a h:mm")),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.alpha(textAlpha)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "select alert time",
                tint = Color.DarkGray,
                modifier = Modifier.alpha(textAlpha)
            )
        }
    }

    if (showDialog) {
        TimePickerDialog(
            time = time,
            onDismissRequest = { showDialog = false },
            onConfirmClick = onTimeSelected
        )
    }
}



@Composable
private fun BasePerf(
    text: String,
    description: String = "",
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    component: @Composable () -> Unit = {}
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .clickable(enabled = (onClick != null) && enabled, onClick = onClick ?: {})
        .padding(horizontal = 16.dp)
        .height(80.dp)
) {
    val textAlpha = if (enabled) 1f else 0.5f

    Column(
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.alpha(textAlpha)
        )
        if (description.isNotBlank()) {
            Text(
                text = description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.alpha(textAlpha)
            )
        }
    }

    Box(modifier = Modifier.align(Alignment.CenterEnd)) {
        component()
    }
}