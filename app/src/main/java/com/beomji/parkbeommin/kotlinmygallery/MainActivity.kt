package com.beomji.parkbeommin.kotlinmygallery

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import kotlin.concurrent.timer
class MainActivity : AppCompatActivity() {
    private val REQUEST_READ_EXTERNAL_STORAGE: Int = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        confirmPermission()
    }

    private fun getAllPhotos() {
        // 모든 사진 정보 가져오기
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, // 가져올 항목 배열
                null, // 조건
                null, // 조건
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC") // 찍은 날짜 내림차순

        val fragments = ArrayList<Fragment>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 사진 경로 uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MainActivity", uri)
                fragments.add(PhotoFragment.newInstance(uri))
            }
            cursor.close()
        }

        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        photoVp.adapter = adapter

        timer(period = 3000) {
            runOnUiThread {
                if (photoVp.currentItem < adapter.count - 1) {
                    photoVp.currentItem = photoVp.currentItem + 1
                }else {
                    photoVp.currentItem = 0
                }
            }
        }
    }


    private fun confirmPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { // 권한이 허용되지 않음
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) { // 이전에 이미 권한이 거부 되었을 때
                alert("사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다. 권한이 필요한 이유") {
                    yesButton { // 권한 요청
                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    noButton {  }
                }.show()
            }else { // 권한 요청
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_READ_EXTERNAL_STORAGE)
            }
        }else { // 권한이 이미 허용됨
            getAllPhotos()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAllPhotos()
                }else {
                    toast("권한 거부 됨")
                }
            }
        }
    }
}
