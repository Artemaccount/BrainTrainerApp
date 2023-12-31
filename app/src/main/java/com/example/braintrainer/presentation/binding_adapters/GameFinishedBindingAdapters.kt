package com.example.braintrainer.presentation.binding_adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.example.braintrainer.R
import com.example.braintrainer.domain.entities.GameResult

@BindingAdapter("required_answers")
fun requiredAnswers(textView: TextView, count: Int) {
    textView.text = textView.context.getString(
        R.string.required_score,
        count.toString()
    )
}

@BindingAdapter("your_score")
fun yourScore(textView: TextView, count: Int) {
    textView.text = textView.context.getString(
        R.string.score_answers,
        count.toString()
    )
}

@BindingAdapter("required_percent")
fun requiredPercent(textView: TextView, count: Int) {
    textView.text = textView.context.getString(
        R.string.required_percentage,
        count.toString()
    )
}

@BindingAdapter("your_percent")
fun yourPercent(textView: TextView, gameResult: GameResult) {
    val percentage = (gameResult.countOfRightAnswers * 100) / gameResult.countOfQuestions
    textView.text = textView.context.getString(
        R.string.score_percentage,
        percentage.toString()
    )
}

@BindingAdapter("winner_image")
fun yourPercent(image: ImageView, winner: Boolean) {
    val intDrawable = if (winner) R.drawable.smile_512dp else R.drawable.sad_512dp
    val imageDrawable = AppCompatResources.getDrawable(image.context, intDrawable)
    image.setImageDrawable(imageDrawable)
}