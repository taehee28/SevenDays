
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.thk.sevendays.R
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
                    Text(text = stringResource(id = R.string.settings))
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
            Section(title = stringResource(id = R.string.settings_section_alarm)) {
                SwitchPref(
                    text = stringResource(id = R.string.settings_get_alarm),
                    description = stringResource(id = R.string.settings_get_alarm_description),
                    checked = alarmState,
                    onCheckedChange = viewModel::setAlarmState
                )

                TimePickerPref(
                    text = stringResource(id = R.string.settings_section_alarm),
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