package com.morphense.learningapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.morphense.learningapp.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnStart.setOnClickListener {
                Log.i("INTRO", "Player clicked on the 'start' button")
                val name = binding.etName.text.toString().trim()

                if (name.isEmpty()) {
                    Log.e("INTRO", "Player didn't enter the name")
                    etName.error = getString(R.string.error_name)
                    return@setOnClickListener
                }

                Log.i("INTRO", "Player is being redirected to the quiz activity")
                val intent = Intent(this@IntroActivity, WordActivity::class.java)
                intent.putExtra(NAME, name)
                startActivity(intent)
            }
        }

        Log.d("INTRO", "Activity created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("INTRO", "Activity destroyed")
    }
}