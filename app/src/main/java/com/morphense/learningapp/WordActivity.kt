package com.morphense.learningapp

import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.morphense.learningapp.databinding.ActivityWordBinding
import com.morphense.learningapp.databinding.ItemOptionBinding

class WordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWordBinding
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)

    private val dictionary = Dictionary()
    private lateinit var questions: List<Question>
    private var currentQuestionIndex = 0
    private var score = 0
    private var isAnswered = false

    private fun renderSkipButton() {
        with(binding) {
            btnSuccessWrapper.visibility = View.INVISIBLE
            btnFailureWrapper.visibility = View.INVISIBLE
            btnSkipWrapper.visibility = View.VISIBLE
        }
    }

    private fun renderSuccessButton() {
        with(binding) {
            btnSkipWrapper.visibility = View.INVISIBLE
            btnSuccessWrapper.visibility = View.VISIBLE
        }
    }

    private fun renderFailureButton() {
        with(binding) {
            btnSkipWrapper.visibility = View.INVISIBLE
            btnFailureWrapper.visibility = View.VISIBLE
        }
    }

    private fun updateProgressBar() {
        with(binding) {
            pB.setProgress(pB.progress + 10, true)
        }
    }

    private fun renderQuestion() {
        val currentQuestion = questions[currentQuestionIndex]
        isAnswered = false

        with(binding) {
            tvWord.text = currentQuestion.word.word

            for ((index, option) in
            listOf(option1, option2, option3, option4).withIndex()
            ) {
                makeDefault(option)

                val currentOption = currentQuestion.options[index]
                option.optionNumber.text = (index + 1).toString()
                option.optionLabel.text = currentOption.translation

                option.root.setOnClickListener {
                    if (isAnswered) return@setOnClickListener

                    if (currentQuestion.check(currentOption.translation)) {
                        score++
                        makeCorrect(option)
                    } else {
                        makeIncorrect(option)
                    }

                    isAnswered = true
                }
            }
        }

        renderSkipButton()
    }

    private fun makeCorrect(option: ItemOptionBinding) {
        toneGenerator.startTone(ToneGenerator.TONE_PROP_ACK, 150)

        with(option) {
            optionNumber.setTextColor(Color.WHITE)
            optionNumber.setBackgroundResource(
                R.drawable.shape_rounded_solid_success
            )
            optionLabel.setTextColor(getColor(R.color.success))
            root.setBackgroundResource(
                R.drawable.shape_rounded_outline_success
            )
        }

        renderSuccessButton()
    }

    private fun makeIncorrect(option: ItemOptionBinding) {
        toneGenerator.startTone(ToneGenerator.TONE_PROP_NACK, 200)

        with(option) {
            optionNumber.setTextColor(Color.WHITE)
            optionNumber.setBackgroundResource(
                R.drawable.shape_rounded_solid_failure
            )
            optionLabel.setTextColor(getColor(R.color.failure))
            root.setBackgroundResource(
                R.drawable.shape_rounded_outline_failure
            )
        }

        renderFailureButton()
    }

    private fun makeDefault(option: ItemOptionBinding) {
        with(option) {
            optionNumber.setTextColor(getColor(R.color.secondary))
            optionNumber.setBackgroundResource(
                R.drawable.shape_rounded_solid
            )
            optionLabel.setTextColor(getColor(R.color.secondary))
            root.setBackgroundResource(
                R.drawable.shape_rounded_outline
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questions = dictionary.generateQuestion(10)
        renderQuestion()

        with(binding) {
            for (button in listOf(btnSkip, btnFailure, btnSuccess)) {
                button.setOnClickListener {
                    currentQuestionIndex++

                    if (currentQuestionIndex == questions.size) {
                        Log.i("WORD", "Player has completed the quiz")
                        Log.i("WORD", "Player is being redirected to the review activity")
                        startActivity(
                            Intent(this@WordActivity, ReviewActivity::class.java).apply {
                                putExtra(NAME, intent.getStringExtra(NAME).orEmpty())
                                putExtra(SCORE, score)
                                putExtra(TOTAL, questions.size)
                            }
                        )
                        finish()
                    } else {
                        Log.i(
                            "WORD",
                            "Player has answered on the question ${currentQuestionIndex + 1}"
                        )
                        renderQuestion()
                        updateProgressBar()
                    }
                }
            }
        }

        Log.d("WORD", "Activity created")
    }

    override fun onDestroy() {
        toneGenerator.release()
        super.onDestroy()
        Log.d("WORD", "Activity destroyed")
    }
}