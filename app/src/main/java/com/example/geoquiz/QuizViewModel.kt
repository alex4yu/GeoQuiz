package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    var currentIndex = 0
    val bankSize = questionBank.size
    var currentQuestionAnswer = questionBank[currentIndex].answer
    var currentQuestionText = questionBank[currentIndex].textResId
    fun moveToNext()
    {
        currentIndex = (currentIndex + 1) % questionBank.size
        currentQuestionText = questionBank[currentIndex].textResId
        currentQuestionAnswer = questionBank[currentIndex].answer
    }

}