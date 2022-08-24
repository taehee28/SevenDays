
package com.thk.sevendays.ui

import android.widget.TimePicker
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import com.google.android.material.timepicker.MaterialTimePicker

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "설정")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) {

        Column {
            Section(title = "알림") {
                SwitchPref(
                    text = "알림 받기",
                    checked = true,
                    onCheckedChange = {}
                )

                TimePickerPref(text = "알림 시간 선택")
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        onBackClick = {}
    )
}

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
    enabled: Boolean = true
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            // TODO: 시간 글씨 색이랑 title 글씨 색이랑 차이가 있어야 할듯
            Text(text = "8:00", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = "select alert time", tint = Color.DarkGray)
        }
    }

    if (showDialog) {
        TimePickerDialog(
            onDismissRequest = { showDialog = false },
        )
    }
}

@Composable
private fun TimePickerDialog(
    onDismissRequest: () -> Unit
) {
    var hour by remember { mutableStateOf("8") }
    var minute by remember { mutableStateOf("00") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {


            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                        contentPadding = PaddingValues(vertical = 6.dp, horizontal = 8.dp),
                        modifier = Modifier.defaultMinSize(minHeight = 1.dp, minWidth = 1.dp)
                    ) {
                        Text(text = "오전", fontSize = 10.sp)
                    }

                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
                        contentPadding = PaddingValues(vertical = 6.dp, horizontal = 8.dp),
                        modifier = Modifier.defaultMinSize(minHeight = 1.dp, minWidth = 1.dp)
                    ) {
                        Text(text = "오후", fontSize = 10.sp)
                    }
                }

                OutlinedTextField(
                    value = hour,
                    onValueChange = { hour = it },
                    modifier = Modifier.width(50.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Text(
                    text = ":",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = minute,
                    onValueChange = { minute = it },
                    modifier = Modifier.width(50.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "취소")
                }
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "확인")
                }
            }
        }
    )
}

@Preview
@Composable
private fun TimePickerDialogPreview() {
    TimePickerDialog(
        onDismissRequest = {}
    )
}

@Composable
private fun ToggleButtons(
    items: List<String>,
    enabled: Boolean = true
) = Column {
    var value by remember {
        mutableStateOf("111")
    }

    items.forEachIndexed { index, s ->
        val shape = when(index) {
            0 -> RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            items.lastIndex -> RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
            else -> RoundedCornerShape(0.dp)
        }

        val modifier = when (index) {
            0 -> Modifier.offset(0.dp, 0.dp)
            else -> Modifier.offset(0.dp, (-1 * index).dp)
        }
        val color = ButtonDefaults.outlinedButtonColors()
        val backgroundColor by animateColorAsState(
            targetValue = if (value == s) ButtonDefaults.buttonColors()
                .backgroundColor(enabled = enabled).value
            else ButtonDefaults.outlinedButtonColors()
                .backgroundColor(enabled = enabled).value
        )

        val contentColor by animateColorAsState(
            targetValue = if (value == s) ButtonDefaults.buttonColors()
                .contentColor(enabled = enabled).value
            else ButtonDefaults.outlinedButtonColors()
                .contentColor(enabled = enabled).value
        )

        Surface(
            shape = shape,
            border = ButtonDefaults.outlinedBorder,
            color = backgroundColor,
            contentColor = contentColor,
            modifier = modifier.toggleable(value = value == s, onValueChange = { value = s })
        ) {
            Text(text = s, modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ToggleButtonsPreview() {
    ToggleButtons(
        items = listOf("111", "222", "333")
    )
}

@Composable
fun BasePerf(
    text: String,
    description: String = "",
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    component: @Composable () -> Unit = {}
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .height(80.dp)
        .clickable(enabled = onClick != null, onClick = onClick ?: {})
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


