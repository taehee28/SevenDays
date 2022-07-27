package com.thk.data.repository

import com.thk.data.model.Challenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun getAllChallenges(): Flow<List<Challenge>>
    suspend fun addChallenge(content: String)
    suspend fun removeChallenge(challengeId: Int)
}