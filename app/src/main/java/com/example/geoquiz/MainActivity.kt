package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var truebutton: Button
    private lateinit var falsebutton: Button
    private lateinit var nextbutton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var prevbutton: ImageButton
    private var correct: Int = 0
    private var score = false
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate(Bundle?) called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        truebutton = findViewById(R.id.true_button)
        falsebutton = findViewById(R.id.false_button)
        questionTextView = findViewById(R.id.question_text_view)
        nextbutton = findViewById(R.id.next_button)
        prevbutton = findViewById(R.id.prev_button)
        questionTextView.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        truebutton.setOnClickListener{view: View ->

            val toast = Toast.makeText(this, checkAnswer(true), Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()


            truebutton.isClickable = false
            falsebutton.isClickable = false



        }
        falsebutton.setOnClickListener { view: View ->

            val toast = Toast.makeText(this, checkAnswer(false), Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()


            truebutton.isClickable = false
            falsebutton.isClickable = false
        }



        nextbutton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        prevbutton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion(){
        if (score)
        {
            val percent: String = correct.toString()+"/"+questionBank.size.toString()+" correct"
            val toast = Toast.makeText(this, percent, Toast.LENGTH_LONG )
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
            score = false
        }
        if (currentIndex == questionBank.size-1)
        {
            score = true
        }
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        truebutton.isClickable = true
        falsebutton.isClickable = true

    }
    private fun checkAnswer(userAnswer: Boolean): Int {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId: Int
        if(userAnswer == correctAnswer)
        {
             messageResId = R.string.correct_toast
            correct++
        }
        else
        {
            messageResId = R.string.incorrect_toast
        }

        return messageResId
    }
}