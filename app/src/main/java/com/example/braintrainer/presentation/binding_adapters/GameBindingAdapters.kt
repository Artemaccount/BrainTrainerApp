package com.example.braintrainer.presentation.binding_adapters

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.braintrainer.R


@BindingAdapter("number_as_text")
fun sumValue(textView: TextView, count: Int) {
    textView.text = count.toString()
}

@BindingAdapter("progress_value")
fun progress(progressBar: ProgressBar, count: Int) {
    progressBar.setProgress(count, true)
}

@BindingAdapter("enough_right_answers")
fun enoughRightAnswers(textView: TextView, enough: Boolean) {
    val color = getColorByState(textView.context, enough)
    textView.setTextColor(color)
}

@BindingAdapter("on_option_click")
fun onOptionClick(textView: TextView, listener: OnOptionClickListener) {
    textView.setOnClickListener {
        listener.invoke(textView.text.toString())
    }
}

@FunctionalInterface
interface OnOptionClickListener {
    fun invoke(option: String)
}

@BindingAdapter("enough_right_percent")
fun enoughRightPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) R.color.bright_green else R.color.bright_red
    return ContextCompat.getColor(context, colorResId)
}

