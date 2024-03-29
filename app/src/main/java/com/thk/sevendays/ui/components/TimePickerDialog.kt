package com.thk.sevendays.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.thk.sevendays.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimePickerDialog(
    time: LocalTime,
    onDismissRequest: () -> Unit,
    onConfirmClick: (LocalTime) -> Unit
) {
    var hour by remember { mutableStateOf(time.format(DateTimeFormatter.ofPattern("h")).toInt()) }
    var minute by remember { mutableStateOf(time.minute) }
    var isAfterNoon = time.hour >= 12

    val toggleItems = listOf(stringResource(id = R.string.am), stringResource(id = R.string.pm))

    // TODO: 큰 화면에서는 어떻게 나오는지?
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
                        onToggleChanged = { isAfterNoon = it == toggleItems[1] }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TimePickerTextField(
                        value = hour.toString(),
                        items = (1..12).map { it.toString() },
                        onMenuItemClick = { hour = it.toInt() }
                    )

                    Text(
                        text = ":",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center
                    )

                    TimePickerTextField(
                        value = String.format("%02d", minute),
                        items = (0..11).map { String.format("%02d", (it * 5)) },
                        onMenuItemClick = { minute = it.toInt() }
                    )
                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(onClick = {
                    val _hour = if (isAfterNoon) (hour % 12) + 12 else hour % 12
                    onConfirmClick(LocalTime.of(_hour, minute))
                    onDismissRequest()
                }) {
                    Text(text = stringResource(id = R.string.set))
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
private fun TimePickerTextField(
    value: String,
    items: List<String>,
    onMenuItemClick: (String) -> Unit,
) = Column {
    var menuExpanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        enabled = false,
        modifier = Modifier
            .height(70.dp)
            .width(70.dp)
            .clickable { menuExpanded = true },
        textStyle = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
        colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = Color.Black)
    )

    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = { menuExpanded = !menuExpanded },
        modifier = Modifier.height(200.dp)
    ) {
        items.forEach {
            DropdownMenuItem(
                onClick = {
                    menuExpanded = false
                    onMenuItemClick(it)
                }
            ) {
                Text(
                    text = it,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

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