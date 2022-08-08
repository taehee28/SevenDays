package com.thk.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.thk.data.database.DatabaseInfo
import java.time.LocalDate

@Entity(
    tableName = DatabaseInfo.TABLE_NAME_STAMP,
    foreignKeys = [
        ForeignKey(
            entity = Challenge::class,
            parentColumns = arrayOf(DatabaseInfo.COLUMN_NAME_CHALLENGE_ID),
            childColumns = arrayOf(DatabaseInfo.COLUMN_NAME_CHALLENGE_ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Stamp(
    @ColumnInfo(name = DatabaseInfo.COLUMN_NAME_CHALLENGE_ID, index = true)
    val challengeId: Long,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseInfo.COLUMN_NAME_STAMP_ID)
    val stampId: Long = 0,

    @ColumnInfo(name = DatabaseInfo.COLUMN_NAME_IS_CHECKED)
    val isChecked: Boolean,

    @ColumnInfo(name = DatabaseInfo.COLUMN_NAME_DATE)
    val date: LocalDate
)

val sampleStampList = listOf(
    Stamp(challengeId = 0, isChecked = true, date = LocalDate.now().minusDays(3)),
    Stamp(challengeId = 0, isChecked = false, date = LocalDate.now().minusDays(2)),
    Stamp(challengeId = 0, isChecked = true, date = LocalDate.now().minusDays(1)),
    Stamp(challengeId = 0, isChecked = false, date = LocalDate.now()),
    Stamp(challengeId = 0, isChecked = false, date = LocalDate.now().plusDays(1)),
    Stamp(challengeId = 0, isChecked = false, date = LocalDate.now().plusDays(2)),
    Stamp(challengeId = 0, isChecked = false, date = LocalDate.now().plusDays(3)),
)
