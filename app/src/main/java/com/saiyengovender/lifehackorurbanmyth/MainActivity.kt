package com.saiyengovender.lifehackorurbanmyth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// Welcome screen - first thing the user sees
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Welcome screen loaded")

        // find the start button
        val btnStart = findViewById<Button>(R.id.btnStart)

        // when clicked, go to the question screen
        btnStart.setOnClickListener {
            Log.d(TAG, "Start clicked - going to QuestionActivity")
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}