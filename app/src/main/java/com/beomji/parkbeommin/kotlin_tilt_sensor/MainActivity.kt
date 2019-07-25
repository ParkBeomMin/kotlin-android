package com.beomji.parkbeommin.kotlin_tilt_sensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var tiltView: TiltView
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // value[0] : x축 값. 위로 기울이면 -10 ~ 0. 아래로 기울이면 0 ~ 10.
        // value[1] : y축 값. 왼쪽으로 기울이면 -10 ~ 0. 오른쪽으로 기울이면 0 ~ 10.
        // value[2] : z축 값. 미사용.
        Log.d("MainActivity", "onSensorChanged: x : ${event?.values?.get(0)}, " +
                "y : ${event?.values?.get(1)}, z : ${event?.values?.get(2)}")

        tiltView.onSensorEvent(event)
    }


    private val sensorManager by lazy {
        // 지연된 초기화 사용. sensorManager 변수를 처음 사용할 때 SensorManager 객체를 얻
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 화면 꺼지지 않게
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // 화면 가로로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        tiltView = TiltView(this)
        setContentView(tiltView)


    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
