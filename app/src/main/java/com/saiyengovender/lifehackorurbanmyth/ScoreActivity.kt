package com.saiyengovender.lifehackorurbanmyth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Score screen - shows final score and lets user review answers
class ScoreActivity : AppCompatActivity() {

    private val TAG = "ScoreActivity"

    // same list needed so we can show the review
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        Log.d(TAG, "Score screen loaded")

        // get the score passed from QuestionActivity
        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 10)

        val tvScore = findViewById<TextView>(R.id.tvScore)
        val tvFeedback = findViewById<TextView>(R.id.tvFeedback)
        val btnReview = findViewById<Button>(R.id.btnReview)
        val scrollReview = findViewById<ScrollView>(R.id.scrollReview)
        val tvReviewContent = findViewById<TextView>(R.id.tvReviewContent)

        // show the score
        tvScore.text = "You scored $score/$total"

        // personalised feedback based on how well they did
        val feedback = when {
            score == total -> "Master Hacker! Perfect score!"
            score >= total * 0.8 -> "Great Job! You really know your hacks!"
            score >= total * 0.6 -> "Not bad! Keep practising!"
            score >= total * 0.4 -> "You got some right! Room to improve."
            else -> "Stay Safe Online! Brush up on your life hacks."
        }

        tvFeedback.text = feedback
        Log.d(TAG, "Score: $score/$total - Feedback: $feedback")

        // review button shows all questions and correct answers
        btnReview.setOnClickListener {
            if (scrollReview.visibility == View.GONE) {
                val sb = StringBuilder()
                for (i in statements.indices) {
                    val answer = if (statements[i].second) "HACK (True)" else "MYTH (False)"
                    sb.append("${i + 1}. ${statements[i].first}\nAnswer: $answer\n\n")
                }
                tvReviewContent.text = sb.toString()
                scrollReview.visibility = View.VISIBLE
                btnReview.text = "Hide Review"
                Log.d(TAG, "Review shown")
            } else {
                scrollReview.visibility = View.GONE
                btnReview.text = "Review Answers"
            }
        }
    }
}