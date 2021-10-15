package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val EXTRA_ANSWER_SHOWN = "com.alex_.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.alex_.android.geoquiz.answer_is_true"
class CheatActivity : AppCompatActivity() {
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private var answerisTrue = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener{
            val answerText = when{
                answerisTrue-> R.string.true_button
                else -> R.string.false_button
            }
            setAnswerShownResult(true)
            answerTextView.setText(answerText)
        }
        answerisTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
    }
    companion object{
        fun newIntent(packageContext: Context, answerisTrue: Boolean): Intent{
            return Intent(packageContext, CheatActivity::class.java).apply{
                putExtra(EXTRA_ANSWER_IS_TRUE, answerisTrue)
            }
        }
    }
    private fun setAnswerShownResult(isAnswerShown: Boolean)
    {
        val data = Intent().apply{
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)

    }
}