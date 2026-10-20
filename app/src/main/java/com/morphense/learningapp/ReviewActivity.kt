package com.morphense.learningapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.morphense.learningapp.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            val name = intent.getStringExtra(NAME).orEmpty()
            val score = intent.getIntExtra(SCORE, 0)
            val total = intent.getIntExtra(TOTAL, 0)

            tvThanksMessage.text = getString(R.string.tv_thanks_message, name)
            tvScoreMessage.text = getString(R.string.tv_score_message, score, total)
            btnDone.setOnClickListener {
                Log.i("REVIEW", "Player clicked on the 'done' button")
                Log.i("REVIEW", "Player is being redirected to the intro activity")
                finish()
            }
        }

        Log.d("REVIEW", "Activity created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("REVIEW", "Activity destroyed")
    }
}