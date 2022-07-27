package com.thk.data.datasource

import com.thk.data.model.Challenge
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllChallenges(): Flow<List<Challenge>>
    suspend fun addChallenge(challenge: Challenge)
    suspend fun removeChallenge(challengeId: Int)
}