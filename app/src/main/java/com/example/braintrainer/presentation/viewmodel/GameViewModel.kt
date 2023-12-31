package com.example.braintrainer.presentation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.braintrainer.R
import com.example.braintrainer.data.GameRepositoryImpl
import com.example.braintrainer.domain.entities.GameLevel
import com.example.braintrainer.domain.entities.GameResult
import com.example.braintrainer.domain.entities.GameSettings
import com.example.braintrainer.domain.entities.Question
import com.example.braintrainer.domain.use_cases.GenerateQuestionUseCase
import com.example.braintrainer.domain.use_cases.GetGameSettingsUseCase


class GameViewModel(
    private val application: Application,
    private val gameLevel: GameLevel
) : ViewModel() {
    private val repository = GameRepositoryImpl
    private val generaQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private lateinit var gameSettings: GameSettings

    private val _timer = MutableLiveData<String>()
    val timer: LiveData<String>
        get() = _timer

    private val _finishGame = MutableLiveData<GameResult>()
    val finishGame: LiveData<GameResult>
        get() = _finishGame

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _progressBar = MutableLiveData(0)
    val progressBar: LiveData<Int>
        get() = _progressBar

    private val _enoughRightAnswers = MutableLiveData(false)
    val enoughRightAnswers: LiveData<Boolean>
        get() = _enoughRightAnswers

    private val _enoughPercent = MutableLiveData(false)
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent


    private var rightRepositoryAnswer = 0
    private var questionsCount = 0
    private var userRightAnswers = 0

    init {
        startGame()
    }

    private fun startGame() {
        gameSettings = getGameSettingsUseCase(gameLevel)
        startTimer()
        getNewQuestion()
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun updateProgress() {
        updateRightAnswers()
        updateProgressBar()
    }

    private fun updateRightAnswers() {
        _progressAnswers.value = application.getString(
            R.string.progress_answers,
            userRightAnswers.toString(),
            gameSettings.minCountOfRightAnswers.toString()
        )
        _enoughRightAnswers.value = userRightAnswers >= gameSettings.minCountOfRightAnswers
    }

    private fun updateProgressBar() {
        val percentOfRightAnswer = userRightAnswers * 100 / questionsCount
        _progressBar.value = percentOfRightAnswer
        _enoughPercent.value = percentOfRightAnswer >= gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        val longMillis = gameSettings.gameTimeInSeconds.toLong() * 1_000

        object : CountDownTimer(longMillis, 1_000) {
            override fun onTick(p0: Long) {
                val seconds = p0 / 1_000
                _timer.value = convertSecondsToMinutesAndSeconds(seconds)
            }

            private fun convertSecondsToMinutesAndSeconds(totalSecs: Long): String {
                val minutes = (totalSecs % 3600) / 60
                val seconds = totalSecs % 60
                return String.format("%02d:%02d", minutes, seconds);
            }

            override fun onFinish() {
                var winner = true
                if (userRightAnswers < gameSettings.minCountOfRightAnswers) {
                    winner = false
                }
                val userRightAnswersInPercent = (userRightAnswers * 100) / questionsCount
                if (userRightAnswersInPercent < gameSettings.minPercentOfRightAnswers) {
                    winner = false
                }
                _finishGame.value =
                    GameResult(
                        winner = winner,
                        countOfRightAnswers = userRightAnswers,
                        countOfQuestions = questionsCount,
                        gameSettings = gameSettings
                    )
            }
        }.start()
    }


    fun validateAnswer(answer: String) {
        questionsCount++
        val numberAnswer = answer.toInt()
        if (numberAnswer == rightRepositoryAnswer) {
            userRightAnswers++
        }
        updateProgress()
        getNewQuestion()
    }

    private fun getNewQuestion() {
        val question = generaQuestionUseCase(gameSettings.maxSumValue)
        _question.value = question
        rightRepositoryAnswer = question.sum - question.visibleNumber
    }

}