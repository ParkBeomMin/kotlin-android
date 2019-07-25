package com.beomji.parkbeommin.kotlin_bmi_calculator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        button.setOnClickListener {

            saveData(weightEdt.text.toString().toInt(), heightEdt.text.toString().toInt())

//            기본적인 방법
//            val intent = Intent(this, ResultActivity::class.java)
//            intent.putExtra("weight", weightEdt.text.toString())
//            intent.putExtra("height", heightEdt.text.toString())
//            startActivity(intent)


//            anko commons 라이브러리 방법
            startActivity<ResultActivity>(
                    "weight" to weightEdt.text.toString(),
                    "height" to heightEdt.text.toString()
            )
        }
    }

    private fun saveData(weight: Int, height: Int) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("KEY_WEIGHT", weight)
                .putInt("KEY_HEIGHT", height)
                .apply()
    }

    private fun loadData() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val weight = pref.getInt("KEY_WEIGHT", 0)
        val height = pref.getInt("KEY_HEIGHT", 0)

        if( weight != 0 && height != 0) {
            weightEdt.setText(weight.toString())
            heightEdt.setText(height.toString())
        }
    }
}
