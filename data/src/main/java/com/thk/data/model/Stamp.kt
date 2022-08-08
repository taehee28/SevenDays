package com.thk.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
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
    val challengeId: Int,

    @ColumnInfo(name = DatabaseInfo.COLUMN_NAME_IS_CHECKED)
    val isChecked: Boolean,

    @ColumnInfo(name = DatabaseInfo.COLUMN_NAME_DATE)
    val date: LocalDate
)

val sampleStampList = listOf(
    Stamp(0, true, LocalDate.now().minusDays(3)),
    Stamp(0, false, LocalDate.now().minusDays(2)),
    Stamp(0, true, LocalDate.now().minusDays(1)),
    Stamp(0, false, LocalDate.now()),
    Stamp(0, false, LocalDate.now().plusDays(1)),
    Stamp(0, false, LocalDate.now().plusDays(2)),
    Stamp(0, false, LocalDate.now().plusDays(3)),
)
