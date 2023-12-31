package com.example.braintrainer.domain.repository

import com.example.braintrainer.domain.entities.GameLevel
import com.example.braintrainer.domain.entities.GameSettings
import com.example.braintrainer.domain.entities.Question

interface GameRepository {
    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(gameLevel: GameLevel): GameSettings
}