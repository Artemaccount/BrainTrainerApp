package com.example.braintrainer.domain.use_cases

import com.example.braintrainer.domain.entities.GameLevel
import com.example.braintrainer.domain.entities.GameSettings
import com.example.braintrainer.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(gameLevel: GameLevel): GameSettings {
        return repository.getGameSettings(gameLevel)
    }
}