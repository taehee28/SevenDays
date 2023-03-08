
package com.thk.sevendays.ui.screens

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.thk.sevendays.ui.components.Section
import com.thk.sevendays.ui.components.SwitchPref
import com.thk.sevendays.ui.components.TimePickerPref
import com.thk.sevendays.ui.viewmodels.SettingsViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val alarmState by viewModel.alarmState.collectAsState()
    val alarmTime by viewModel.alarmTime.collectAsState()

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
                    description = "스탬프 찍는 것을 잊지 않도록 정해진 시간에 알림을 보냅니다.",
                    checked = alarmState,
                    onCheckedChange = viewModel::setAlarmState
                )

                TimePickerPref(
                    text = "알림 시간 선택",
                    enabled = alarmState,
                    time = alarmTime,
                    onTimeSelected = viewModel::setAlarmTime
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    /*SettingsScreen(
        onBackClick = {}
    )*/
}