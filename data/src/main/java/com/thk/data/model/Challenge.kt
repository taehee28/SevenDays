package com.thk.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thk.data.database.DatabaseInfo
import java.time.LocalDate
import java.util.*

@Entity(tableName = DatabaseInfo.TABLE_NAME_CHALLENGE)
data class Challenge(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseInfo.COLUMN_NAME_CHALLENGE_ID)
    val challengeId : Int = 0,

    @ColumnInfo(name = DatabaseInfo.COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = DatabaseInfo.COLUMN_NAME_START_DATE)
    val startDate: LocalDate = LocalDate.now()
)

val sampleChallengeList = listOf(
    Challenge(challengeId = 1, title = "하루 한번 산책", startDate = LocalDate.now()),
    Challenge(challengeId = 2, title = "책 읽기", startDate = LocalDate.now().minusDays(3)),
    Challenge(challengeId = 3,title = "하루에 물 2리터 마시기", startDate = LocalDate.now().minusDays(9))
)