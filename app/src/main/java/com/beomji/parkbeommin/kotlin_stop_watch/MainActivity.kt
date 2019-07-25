package com.beomji.parkbeommin.kotlin_stop_watch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false

    private var lap = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playFab.setOnClickListener {
            if (isRunning) {
                pause()
            }else {
                start()
            }
            isRunning = !isRunning
        }

        lapBtn.setOnClickListener {
            recordLapTime()
        }

        refreshFab.setOnClickListener {
            reset()
        }
    }

    private fun start() {
        playFab.setImageResource(R.drawable.ic_pause_black_24dp)
        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secondTv.text = "$sec"
                milliSecondTv.text = "$milli"
            }
        }
    }

    private fun pause() {
        playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel() // 실행중인 타이머가 있다면, 종료
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val lapTv = TextView(this)
        lapTv.text = "$lap LAB : ${lapTime / 100}.${lapTime % 100}"

        lapLayout.addView(lapTv, 0) // 맨 위에 뷰를 추가한다.
        lap++
    }

    private fun reset() {
        timerTask?.cancel()

        time = 0
        isRunning = false
        playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secondTv.text = "0"
        milliSecondTv.text = "00"

        lapLayout.removeAllViews()
        lap = 1
    }
}
