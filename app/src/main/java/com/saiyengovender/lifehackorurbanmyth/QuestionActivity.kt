package com.saiyengovender.lifehackorurbanmyth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Question screen - shows each statement and handles answers
class QuestionActivity : AppCompatActivity() {

    private val TAG = "QuestionActivity"

    // list of statements - true means it's a real hack, false means myth
    private val statements = listOf(
        Pair("Putting your phone in rice fixes water damage.", false),
        Pair("Drinking water before a meal can help reduce overeating.", true),
        Pair("You only use 10% of your brain.", false),
        Pair("Chewing gum helps improve concentration and memory.", true),
        Pair("Reading in dim light permanently damages your eyesight.", false),
        Pair("Taking short breaks while studying improves retention.", true),
        Pair("Shaving makes hair grow back thicker.", false),
        Pair("Cold water can help reduce swelling from a minor burn.", true),
        Pair("We swallow 8 spiders a year in our sleep.", false),
        Pair("Writing things down by hand helps you remember them better.", true)
    )

    private var currentIndex = 0  // which question we're on
    private var score = 0          // number of correct answers
    private var answered = false   // stops user answering twice

    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvStatement: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var btnHack: Button
    private lateinit var btnMyth: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        Log.d(TAG, "Question screen loaded")

        // link views
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvStatement = findViewById(R.id.tvStatement)
        tvFeedback = findViewById(R.id.tvFeedback)
        btnHack = findViewById(R.id.btnHack)
        btnMyth = findViewById(R.id.btnMyth)
        btnNext = findViewById(R.id.btnNext)

        loadQuestion()

        // user thinks it's a hack
        btnHack.setOnClickListener {
            if (!answered) checkAnswer(true)
        }

        // user thinks it's a myth
        btnMyth.setOnClickListener {
            if (!answered) checkAnswer(false)
        }

        // move to next question or go to score screen
        btnNext.setOnClickListener {
            currentIndex++
            if (currentIndex < statements.size) {
                loadQuestion()
            } else {
                Log.d(TAG, "All done - going to ScoreActivity")
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("SCORE", score)
                intent.putExtra("TOTAL", statements.size)
                startActivity(intent)
                finish()
            }
        }
    }

    // loads the current question onto the screen
    private fun loadQuestion() {
        answered = false
        tvFeedback.text = ""
        btnNext.visibility = View.GONE
        btnHack.isEnabled = true
        btnMyth.isEnabled = true

        val questionNum = currentIndex + 1
        tvQuestionNumber.text = "Question $questionNum/${statements.size}"
        tvStatement.text = statements[currentIndex].first

        Log.d(TAG, "Loaded question $questionNum")
    }

    // checks if the answer is correct and updates score
    private fun checkAnswer(userAnswer: Boolean) {
        answered = true
        val correctAnswer = statements[currentIndex].second

        if (userAnswer == correctAnswer) {
            score++
            tvFeedback.text = if (correctAnswer) "Correct! That's a real time-saver!" else "Correct! That's just an urban myth."
            tvFeedback.setTextColor(android.graphics.Color.parseColor("#00FF99"))
            Log.d(TAG, "Correct! Score: $score")
        } else {
            tvFeedback.text = if (correctAnswer) "Wrong! That one is actually a real hack!" else "Wrong! That's just an urban myth."
            tvFeedback.setTextColor(android.graphics.Color.parseColor("#E94560"))
            Log.d(TAG, "Wrong. Score stays: $score")
        }

        btnHack.isEnabled = false
        btnMyth.isEnabled = false
        btnNext.visibility = View.VISIBLE
    }
}