package com.thk.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.thk.data.model.Challenge
import com.thk.data.model.Stamp
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM ${DatabaseInfo.TABLE_NAME_CHALLENGE}")
    fun getChallenges(): Flow<List<Challenge>>

    @Insert
    suspend fun addChallenge(challenge: Challenge): Long

    @Query("DELETE FROM ${DatabaseInfo.TABLE_NAME_CHALLENGE} WHERE ${DatabaseInfo.COLUMN_NAME_CHALLENGE_ID} = :challengeId")
    suspend fun removeChallenge(challengeId: Long)
}

@Dao
interface StampDao {
    @Query("SELECT * FROM ${DatabaseInfo.TABLE_NAME_STAMP} WHERE ${DatabaseInfo.COLUMN_NAME_CHALLENGE_ID} = :challengeId")
    fun getStamps(challengeId: Long): Flow<List<Stamp>>

    @Insert(onConflict = REPLACE)
    suspend fun addStamps(vararg stamp: Stamp): List<Long>

    @Update
    suspend fun updateStampChecked(stamp: Stamp)
}