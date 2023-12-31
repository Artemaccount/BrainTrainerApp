package com.example.braintrainer.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.braintrainer.domain.entities.GameLevel
import kotlin.RuntimeException

class GameViewModelFactory(
    private val application: Application,
    private val gameLevel: GameLevel,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, gameLevel) as T
        }
        throw RuntimeException("unknown view model $modelClass")
    }
}