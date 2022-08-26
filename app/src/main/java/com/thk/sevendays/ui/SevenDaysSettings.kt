
package com.thk.sevendays.ui

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

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
    var time by remember { mutableStateOf(LocalTime.now()) }

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
            Text(text = time.format(DateTimeFormatter.ofPattern("a h:mm")), style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = "select alert time", tint = Color.DarkGray)
        }
    }

    if (showDialog) {
        TimePickerDialog(
            time = LocalTime.now(),
            onDismissRequest = { showDialog = false },
            onConfirmClick = { time = it }
        )
    }
}

@Composable
private fun TimePickerDialog(
    time: LocalTime,
    onDismissRequest: () -> Unit,
    onConfirmClick: (LocalTime) -> Unit
) {
    var hour by remember { mutableStateOf(time.hour) }
    var minute by remember { mutableStateOf(time.minute) }
    var hourMenuExpanded by remember { mutableStateOf(false) }
    var minuteMenuExpanded by remember { mutableStateOf(false) }
    var isAfterNoon = hour >= 12
    Log.d("TAG", "TimePickerDialog: isAfterNoon = $isAfterNoon")

    val toggleItems = listOf("오전", "오후")

    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VerticalToggleButtons(
                        items = toggleItems,
                        initialValue = toggleItems[if (isAfterNoon) 1 else 0],
                        onToggleChanged = {
                            isAfterNoon = it == toggleItems[1]
                            hour = if (isAfterNoon) (hour % 12) + 12 else hour % 12
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {

                        OutlinedTextField(
                            value = (hour % 12).let { if (it == 0) 12 else it }.toString(),
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .height(70.dp)
                                .width(70.dp)
                                .clickable { hourMenuExpanded = true },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            textStyle = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
                            enabled = false
                        )

                        DropdownMenu(
                            expanded = hourMenuExpanded,
                            onDismissRequest = { hourMenuExpanded = !hourMenuExpanded },
                            modifier = Modifier.height(200.dp)
                        ) {
                            for (i in 1..12) {
                                DropdownMenuItem(
                                    onClick = {
                                        hourMenuExpanded = false
                                        hour = if (isAfterNoon) (i % 12) + 12 else i % 12
                                    }
                                ) {
                                    Text(
                                        text = i.toString(),
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }


                    Text(
                        text = ":",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center
                    )

                    Column {
                        OutlinedTextField(
                            value = minute.toString(),
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .height(70.dp)
                                .width(70.dp)
                                .clickable { minuteMenuExpanded = true },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            textStyle = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center)
                        )

                        DropdownMenu(
                            expanded = minuteMenuExpanded,
                            onDismissRequest = { minuteMenuExpanded = !minuteMenuExpanded },
                            modifier = Modifier.height(200.dp)
                        ) {
                            for (i in 0..55 step 5) {
                                DropdownMenuItem(
                                    onClick = {
                                        minuteMenuExpanded = false
                                        minute = i
                                    }
                                ) {
                                    Text(
                                        text = String.format("%02d", i),
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }

                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "취소")
                }
                TextButton(onClick = {
                    onConfirmClick(LocalTime.of(hour, minute))
                    onDismissRequest()
                }) {
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
        time = LocalTime.of(20, 0),
        onDismissRequest = {},
        onConfirmClick = { time -> }
    )
}

@Composable
private fun VerticalToggleButtons(
    items: List<String>,
    initialValue: String,
    enabled: Boolean = true,
    onToggleChanged: (String) -> Unit
) = Column {
    val interactionSource = remember { MutableInteractionSource() }
    var value by remember { mutableStateOf(initialValue) }

    items.forEachIndexed { index, s ->
        val shape = when(index) {
            0 -> RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            items.lastIndex -> RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
            else -> RoundedCornerShape(0.dp)
        }

        // note : offset을 앞으로 조금씩 땡기느라 끝에 남는 공간이 생겨버림
        val modifier = when (index) {
            0 -> Modifier.offset(0.dp, 0.dp)
            else -> Modifier.offset(0.dp, (-1 * index).dp)
        }

        val buttonColors = if (value == s) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors()

        val backgroundColor by animateColorAsState(
            targetValue = buttonColors.backgroundColor(enabled = enabled).value
        )

        val contentColor by animateColorAsState(
            targetValue = buttonColors.contentColor(enabled = enabled).value
        )

        Surface(
            shape = shape,
            border = ButtonDefaults.outlinedBorder,
            color = backgroundColor,
            contentColor = contentColor,
            modifier = modifier
                .zIndex(if (value == s) 1f else 0f)
                .toggleable(
                    value = value == s,
                    onValueChange = {
                        onToggleChanged(s)
                        value = s
                    },
                    enabled = enabled,
                    interactionSource = interactionSource,
                    indication = null
                )
        ) {
            Text(text = s, modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerticalToggleButtonsPreview() {
    VerticalToggleButtons(
        items = listOf("111", "222", "333"),
        onToggleChanged = {},
        initialValue = "111"
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


