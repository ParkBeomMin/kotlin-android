package com.beomji.parkbeommin.kotlin_bmi_calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val weight = intent.getStringExtra("weight").toInt()
        val height = intent.getStringExtra("height").toInt()

        val bmi = weight / Math.pow(height / 100.0, 2.0)

//        기본적인 방법
//        Toast.makeText(this, "$bmi", Toast.LENGTH_LONG).show()

//        anko commons 라이브러리 방법
        toast("$bmi")

        when {
            bmi >= 35 -> resultTv.text = "고도 비만"
            bmi >= 30 -> resultTv.text = "2단계 비만"
            bmi >= 25 -> resultTv.text = "1단계 비만"
            bmi >= 13 -> resultTv.text = "과체중"
            bmi >= 18.5 -> resultTv.text = "정상"
            else -> resultTv.text = "저체중"
        }

        when {
            bmi >= 23 -> resultIv.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            bmi >= 18.5 -> resultIv.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp)
            else -> resultIv.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp)
        }
    }
}
