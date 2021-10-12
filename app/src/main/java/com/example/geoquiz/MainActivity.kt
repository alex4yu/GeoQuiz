package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0
class MainActivity : AppCompatActivity() {
    private lateinit var truebutton: Button
    private lateinit var falsebutton: Button
    private lateinit var nextbutton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var prevbutton: ImageButton
    private lateinit var cheatbutton: Button
    private var correct: Double = 0.0
    private var score = false

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?:0

        Log.d(TAG, "onCreate(Bundle?) called")

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        truebutton = findViewById(R.id.true_button)
        falsebutton = findViewById(R.id.false_button)
        questionTextView = findViewById(R.id.question_text_view)
        nextbutton = findViewById(R.id.next_button)
        prevbutton = findViewById(R.id.prev_button)
        cheatbutton = findViewById(R.id.cheat_button)
        questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        cheatbutton.setOnClickListener{view: View->
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
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
            quizViewModel.moveToNext()
            updateQuestion()
        }
        prevbutton.setOnClickListener {
            quizViewModel.moveToNext()
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
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
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
            var percentdouble: Double = correct/quizViewModel.bankSize*100
            var percentString: String = percentdouble.toString()
            if(percentString.length > 3)
            {
                percentString = percentString.substring(0, 4)
            }
            var percent: String = "$percentString correct"
            val toast = Toast.makeText(this, percent, Toast.LENGTH_LONG )
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
            score = false
        }
        if (quizViewModel.currentIndex == quizViewModel.bankSize - 1)
        {
            score = true
        }
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        truebutton.isClickable = true
        falsebutton.isClickable = true

    }
    private fun checkAnswer(userAnswer: Boolean): Int {
        val correctAnswer = quizViewModel.currentQuestionAnswer
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